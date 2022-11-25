package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoBasico;

import java.util.stream.IntStream;

public class TipoKernelGaussiano implements TipoKernelInterface{

    double[][] media;
    double[] sigma;
    int tamanho;

    double[][][] dadosKernel;

    public TipoKernelGaussiano() {
        this(0.0, 0.0, 1.0, 5);
    }

    public TipoKernelGaussiano(double mediaX, double mediaY, double sigma, int tamanho) {
        this.media = new double[][]{{mediaX, mediaY}, {mediaX, mediaY}, {mediaX, mediaY}};
        this.sigma = new double[]{sigma, sigma, sigma};
        this.tamanho = tamanho;
    }

    public TipoKernelGaussiano(double[][] media, double[] sigma, int tamanho) {
        this.media = media;
        this.sigma = sigma;
        this.tamanho = tamanho;
    }

    public void normalizaSomaUnitaria(double multiplica) {
        final double[] acc = new double[dadosKernel.length];
        // Soma as 3 matrizes de cada cor e as coloca na respqectica posicao
        IntStream.range(0, dadosKernel.length).parallel().forEach(
                cor -> {
                    IntStream.range(0, dadosKernel[cor].length).forEach(
                            xPos -> {
                                IntStream.range(0, dadosKernel[cor][xPos].length).forEach(
                                        yPos -> {
                                            acc[cor] += dadosKernel[cor][xPos][yPos];
                                        });
                            });
                });

        // Ajusta os valores das matrizes os dividindo pela soma e multiplicando pela constante.
        IntStream.range(0, dadosKernel.length).forEach(
                cor -> {
                    IntStream.range(0, dadosKernel[cor].length).forEach(
                            xPos -> {
                                IntStream.range(0, dadosKernel[cor][xPos].length).forEach(
                                        yPos -> {
                                            dadosKernel[cor][xPos][yPos] /= acc[cor];
                                            dadosKernel[cor][xPos][yPos] *= multiplica;
                                        });
                            });
                });
    }

    public double getSoma(int cor) {
        final double[] acc = {0.};
        IntStream.range(0, dadosKernel[cor].length).forEach(
                xPos -> {
                    IntStream.range(0, dadosKernel[cor][xPos].length).forEach(
                            yPos -> {
                                acc[0] += dadosKernel[cor][xPos][yPos];
                            });
                });
        return acc[0];
    }

    private double calculaGalsiana(double x, double y, int rgb) {
        double sigmaQuadradoVezes2 = sigma[rgb] * sigma[rgb] * 2;
        double fator = 1.0 / Math.sqrt(Math.PI * sigmaQuadradoVezes2);

        double xLinha = x - media[rgb][0];
        double yLinha = y - media[rgb][0];

        double expoencial = -((xLinha * xLinha) + (yLinha * yLinha)) / sigmaQuadradoVezes2;
        return Math.exp(expoencial) * fator;
    }

    public void criaDadosKernel(double multiplica, double soma) {

        int base = -tamanho / 2;
        dadosKernel = new double[3][tamanho][tamanho];

        IntStream.range(0, tamanho).forEach(
                posX -> {
                    IntStream.range(0, tamanho).forEach(posY -> {
                        dadosKernel[Imagem.Vermelho][posX][posY] =
                                (calculaGalsiana(posX + base, posY + base, Imagem.Vermelho) * multiplica) + soma;
                        dadosKernel[Imagem.Verde][posX][posY] =
                                (calculaGalsiana(posX + base, posY + base, Imagem.Verde) * multiplica) + soma;
                        dadosKernel[Imagem.Azul][posX][posY] =
                                (calculaGalsiana(posX + base, posY + base, Imagem.Azul) * multiplica) + soma;
                    });
                });
    }

    @Override
    public double[][][] getData1() {
        return dadosKernel;
    }
}

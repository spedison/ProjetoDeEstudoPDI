package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.enuns.CanalProcessado;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels.TipoKernelInterface;

import java.util.Arrays;
import java.util.stream.IntStream;

public class TransformacaoConvolucaoQuadratica implements TransformacaoConvolucaoInterface<TransformacaoConvolucaoQuadratica> {
    private double[][][] data1;
    private double[][][] data2;

    private CanalProcessado[] canaisProcessados = {CanalProcessado.Vermelho, CanalProcessado.Verde, CanalProcessado.Azul};

    double multiplicador;
    double soma;

    public TransformacaoConvolucaoQuadratica() {
        setMultiplicador(1.0);
        setSoma(0.0);
    }

    public TransformacaoConvolucaoQuadratica(TipoKernelInterface tipoKernelInterface, double multiplicador, double soma) {
        setData1(tipoKernelInterface.getData1());
        setData2(tipoKernelInterface.getData2());
        this.multiplicador = multiplicador;
        this.soma = soma;
    }

    public void setCanaisProcessados(CanalProcessado[] canaisProcessados) {
        this.canaisProcessados = canaisProcessados;
    }

    @Override
    public double[][][] getData1() {
        return data1;
    }

    @Override
    public double[][][] getData2() {
        return data2;
    }

    @Override
    public TransformacaoConvolucaoQuadratica setData1(double[][][] data) {
        this.data1 = data;
        return this;
    }

    @Override
    public TransformacaoConvolucaoQuadratica setData2(double[][][] data) {
        this.data2 = data;
        return this;
    }

    public double getMultiplicador() {
        return multiplicador;
    }

    public TransformacaoConvolucaoQuadratica setMultiplicador(double multiplicador) {
        this.multiplicador = multiplicador;
        return this;
    }

    public double getSoma() {
        return soma;
    }

    public TransformacaoConvolucaoQuadratica setSoma(double soma) {
        this.soma = soma;
        return this;
    }

    public boolean isProcessaCor(CanalProcessado canalProcessado) {
        final boolean[] ret = {false};
        Arrays.stream(canaisProcessados).
                filter(i -> !ret[0]).
                forEach(canal -> ret[0] |= canalProcessado == canal);
        return ret[0];
    }

    @Override
    public int[] transformaRGB(int[] pixel, int x, int y, Imagem imagem) {

        final int xOffset = -(getData1()[0].length / 2); // Pega da 1o Cor o tamanho a qualidade de linhas.
        final int yOffset = -(getData1()[0][0].length / 2); // Peda da 1o Cor e depois da linhas a quantidade de colunas.

        final double[] acc1 = {.0, .0, .0}; // Acumuladores R,G,B.
        final double[] acc2 = {.0, .0, .0}; // Acumuladores R,G,B.
        final double[] acc = {.0, .0, .0}; // Acumuladores R,G,B.

        IntStream.range(0, getData1()[0].length).forEach(xPos -> {
            IntStream.range(0, getData1()[0][0].length).forEach(yPos -> {
                Arrays.stream(canaisProcessados).forEach(canalProcessado -> {
                    int[] px = {0,0,0};
                    int posXLinha = x + xPos + xOffset;
                    int posYLinha = y + yPos + yOffset;
                    px = imagem.getPixel(posXLinha, posYLinha);
                    switch (canalProcessado) {
                        case Vermelho -> {
                            acc1[Imagem.Vermelho] += px[Imagem.Vermelho] * getData1()[Imagem.Vermelho][xPos][yPos];
                            acc2[Imagem.Vermelho] += px[Imagem.Vermelho] * getData2()[Imagem.Vermelho][xPos][yPos];
                        }
                        case Verde -> {
                            acc1[Imagem.Verde] += px[Imagem.Verde] * getData1()[Imagem.Verde][xPos][yPos];
                            acc2[Imagem.Verde] += px[Imagem.Verde] * getData2()[Imagem.Verde][xPos][yPos];
                        }
                        case Azul -> {
                            acc1[Imagem.Azul] += px[Imagem.Azul] * getData1()[Imagem.Azul][xPos][yPos];
                            acc2[Imagem.Azul] += px[Imagem.Azul] * getData2()[Imagem.Azul][xPos][yPos];
                        }
                    }
                });
            });
        });
        IntStream.range(0, acc.length).forEach(
                i ->
                        acc[i] = Math.sqrt(acc1[i] * acc1[i] + acc2[i] * acc2[i])
        );

        int[] ret = {0, 0, 0};
        int[] img = imagem.getPixel(x, y);
        ret[Imagem.Vermelho] = isProcessaCor(CanalProcessado.Vermelho) ? (int) ((acc[Imagem.Vermelho] * multiplicador + soma) + 0.5) : img[Imagem.Vermelho];
        ret[Imagem.Verde] = isProcessaCor(CanalProcessado.Verde) ? (int) ((acc[Imagem.Verde] * multiplicador + soma) + 0.5) : img[Imagem.Verde];
        ret[Imagem.Azul] = isProcessaCor(CanalProcessado.Azul) ? (int) ((acc[Imagem.Azul] * multiplicador + soma) + 0.5) : img[Imagem.Azul];
        return ret;
    }

}
package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao;

import br.com.spedison.biblioteca_pdi.auxiliar.Arredondador;
import br.com.spedison.biblioteca_pdi.base.enuns.CanalProcessado;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels.TipoKernelInterface;

import java.util.Arrays;
import java.util.stream.IntStream;

public class TransformacaoConvolucaoBasico implements TransformacaoConvolucaoInterface<TransformacaoConvolucaoBasico> {
    private double[][][] data;

    private CanalProcessado[] canaisProcessados = {CanalProcessado.Vermelho, CanalProcessado.Verde, CanalProcessado.Azul};

    double multiplicador;
    double soma;

    public TransformacaoConvolucaoBasico() {
        setData1(null);
        setData2(null);
        setMultiplicador(1.);
        setSoma(0.);
    }

    public TransformacaoConvolucaoBasico(TipoKernelInterface tipoKernel) {
        setMultiplicador(1.0);
        setSoma(0.0);
        setData1(tipoKernel.getData1());
    }

    public void setCanaisProcessados(CanalProcessado[] canaisProcessados) {
        this.canaisProcessados = canaisProcessados;
    }

    @Override
    public double[][][] getData1() {
        return data;
    }

    @Override
    public TransformacaoConvolucaoBasico setData1(double[][][] data) {
        this.data = data;
        return this;
    }

    public double getMultiplicador() {
        return multiplicador;
    }

    public TransformacaoConvolucaoBasico setMultiplicador(double multiplicador) {
        this.multiplicador = multiplicador;
        return this;
    }

    public double getSoma() {
        return soma;
    }

    public TransformacaoConvolucaoBasico setSoma(double soma) {
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

        final int xOffset = -(data[0].length / 2);
        final int yOffset = -(data[0][0].length / 2);

        final double[] acc = {.0, .0, .0}; // Acumuladores R,G,B.


        IntStream.range(0, data.length).forEach(xPos -> {
            IntStream.range(0, data[0].length).forEach(yPos -> {
                Arrays.stream(canaisProcessados).forEach(canalProcessado -> {
                    int[] px = imagem.getPixel(x + xPos + xOffset, y + yPos + yOffset);
                    switch (canalProcessado) {
                        case Vermelho -> {
                            acc[Imagem.Vermelho] += px[Imagem.Vermelho] * getData1()[Imagem.Vermelho][xPos][yPos];
                        }
                        case Verde -> {
                            acc[Imagem.Verde] += px[Imagem.Verde] * getData1()[Imagem.Verde][xPos][yPos];
                        }
                        case Azul -> {
                            acc[Imagem.Azul] += px[Imagem.Azul] * getData1()[Imagem.Azul][xPos][yPos];
                        }
                    }
                });
            });
        });

        int[] ret = {0, 0, 0};
        int[] img = imagem.getPixel(x, y);
        ret[Imagem.Vermelho] = isProcessaCor(CanalProcessado.Vermelho) ?
                Arredondador.arredonda(acc[Imagem.Vermelho] * multiplicador + soma) : img[Imagem.Vermelho];
        ret[Imagem.Verde] = isProcessaCor(CanalProcessado.Verde) ?
                Arredondador.arredonda(acc[Imagem.Verde] * multiplicador + soma) : img[Imagem.Verde];
        ret[Imagem.Azul] = isProcessaCor(CanalProcessado.Azul) ?
                Arredondador.arredonda(acc[Imagem.Azul] * multiplicador + soma) : img[Imagem.Azul];
        return ret;
    }
}
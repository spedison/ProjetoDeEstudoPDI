package br.com.spedison.biblioteca_pdi.base;

import java.util.Arrays;
import java.util.stream.IntStream;

public class HistogramaImagem {

    private int[] histogramR;
    private int[] histogramG;
    private int[] histogramB;

    private int[] histogramRAcc;
    private int[] histogramGAcc;
    private int[] histogramBAcc;

    int maximo;
    int minimo;

    public int getMaximo() {
        return maximo;
    }

    public int getMinimo() {
        return minimo;
    }

    public long getContagem() {
        return contagem;
    }

    private long contagem;

    public int[] getHistograma(int rgb, boolean acc) {
        return switch (rgb) {
            case Imagem.Vermelho -> acc ? histogramRAcc : histogramR;
            case Imagem.Verde -> acc ? histogramGAcc : histogramG;
            case Imagem.Azul -> acc ? histogramBAcc : histogramB;
            default -> throw new IllegalStateException("Valor RGB Inválido: " + rgb);
        };
    }

    public double[][] getHistogramProb(boolean cinza, boolean multiplicado100, boolean acc) {

        int[][] histograma = new int[3][];

        if (cinza) {
            histograma[Imagem.Vermelho] = acc ? histogramRAcc : histogramR;
            histograma[Imagem.Verde] = acc ? histogramRAcc : histogramR;
            histograma[Imagem.Azul] = acc ? histogramRAcc : histogramR;
        } else {
            histograma[Imagem.Vermelho] = acc ? histogramRAcc : histogramR;
            histograma[Imagem.Verde] = acc ? histogramGAcc : histogramG;
            histograma[Imagem.Azul] = acc ? histogramBAcc : histogramB;
        }

        final double multiplicador = multiplicado100 ? 100.0 : 1.0;
        double[][] ret = new double[3][];
        IntStream.range(0, 3).forEach(cor ->
                ret[cor] = Arrays
                        .stream(histograma[cor])
                        .asDoubleStream()
                        .map(x -> multiplicador * x / contagem)
                        .toArray());

        return ret;
    }

    private int[] getValorMinimoEMaximoDaBanda(double desconsidearMenorQue, double[] hist) {
        int[] ret = new int[]{0, maximo};
        for (int i = 0; i < hist.length; i++) {
            if (hist[i] >= desconsidearMenorQue) {
                ret[0] = i;
                break;
            }
        }
        for (int i = hist.length - 1; i >= 0; i--) {
            if (hist[i] >= desconsidearMenorQue) {
                ret[1] = i;
                break;
            }
        }
        return ret;
    }

    public int[][] getValorMinimoEMaximoDaBanda(double desconsidearMenorQue, boolean cinza) {

        int[][] retBanda = new int[3][];
        double[][] hist = getHistogramProb(cinza, false, false);

        IntStream
                .range(0, 3)
                .forEach(cor ->
                        retBanda[cor] = getValorMinimoEMaximoDaBanda(desconsidearMenorQue, hist[cor]));
        return retBanda;
    }

    public int[] getValorMinimoDaBanda(double desconsidearMenorQue, boolean cinza) {
        double[][] hist = getHistogramProb(cinza, false, false);
        int[] ret = new int[3];
        IntStream.range(0, 3).forEach(cor -> {
            int i = 0;
            for (; i < maximo; i++) {
                if (hist[cor][i] >= desconsidearMenorQue) {
                    break;
                }
            }
            ret[cor] = i;
        });
        return ret;
    }

    public int[] getValorMaximoDaBanda(double desconsidearMenorQue, boolean cinza) {
        double[][] hist = getHistogramProb(cinza, false, false);
        int[] ret = new int[3];
        IntStream.range(0, 3).forEach(cor -> {
            int i = maximo;
            for (; i >= 0; i--) {
                if (hist[cor][i] >= desconsidearMenorQue) {
                    break;
                }
            }
            ret[cor] = i;
        });
        return ret;
    }

    public HistogramaImagem limpaHistogram(int limitSuperior) {
        histogramR = new int[limitSuperior + 1];
        histogramG = new int[limitSuperior + 1];
        histogramB = new int[limitSuperior + 1];
        return this;
    }

    private void registraHistograma(int[] c) {
        histogramR[c[Imagem.Vermelho]]++;
        histogramG[c[Imagem.Verde]]++;
        histogramB[c[Imagem.Azul]]++;
        contagem++;
    }

    private void registraHistogram(Imagem img, int x, int y, int[] px) {
        registraHistograma(img.getPixel(x, y, px));
    }

    public HistogramaImagem processaHistograma(Imagem img) {
        return processaHistograma(img, img.getOrigemX(), img.getLimiteX(), img.getOrigemY(), img.getLimiteY());
    }

    public HistogramaImagem processaHistograma(Imagem img, int xMin, int xMax, int yMin, int yMax) {
        int[] arrPx = new int[3]; // Buffer compartilhado, pq é sequencial

        maximo = img.getValorMaximo();
        minimo = img.getValorMinimo();

        // Aloca e Zera contadores.
        limpaHistogram(img.getValorMaximo());

        // Faz a contagem pixel a pizel da imagem
        contagem = 0;
        img.getStreamLargura(xMin, xMax)
                .forEach(indexX -> {
                    img.getStreamAltura(yMin, yMax)
                            .forEach(indexY -> {
                                registraHistogram(img, indexX, indexY, arrPx);
                            });
                });

        // Monta um Histograma de contagem acumulado.
        histogramRAcc = Arrays.copyOf(histogramR, histogramR.length);
        histogramGAcc = Arrays.copyOf(histogramG, histogramG.length);
        histogramBAcc = Arrays.copyOf(histogramB, histogramB.length);

        for (int i = 1; i < histogramBAcc.length; i++) {
            histogramRAcc[i] += histogramRAcc[i - 1];
            histogramGAcc[i] += histogramGAcc[i - 1];
            histogramBAcc[i] += histogramBAcc[i - 1];
        }

        return this;
    }

    public HistogramaImagem imprimeHistogramaTabular() {
        System.out.printf("%s\t%s\t%s\t%s\n", "Intensidade", "R", "G", "B");
        IntStream.range(0, histogramB.length).forEach(
                pos -> {
                    int r = histogramR[pos];
                    int g = histogramG[pos];
                    int b = histogramB[pos];
                    System.out.printf("%d\t%d\t%d\t%d\n", pos, r, g, b);
                }
        );
        return this;
    }
}
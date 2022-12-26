package br.com.spedison;

import br.com.spedison.usogeral.auxiliar.MatrizAuxiliar;

import java.util.Objects;
import java.util.stream.IntStream;

public class Convolucao1D {

    private double [] kernel;

    public void setKernel(double[] kernel) {
        this.kernel = kernel;
    }

    private double getData(double[] data, int pos) {
        if (pos < 0) {
            pos = 0;
        }
        if (pos > data.length - 1) {
            pos = data.length - 1;
        }
        return data[pos];
    }

    private double getData(double[][] data, int pos, int canal) {
        if (pos < 0) {
            pos = 0;
        }
        if (pos > data.length - 1) {
            pos = data.length - 1;
        }
        return data[pos][canal];
    }

    public double[] convolucao(double[] entrada) {
        double[] ret = new double[entrada.length];

        Objects.requireNonNull(kernel, "O Kernel não foi definido é nullo");
        Objects.requireNonNull(entrada, "Os valores de entrada é nullo");

        if (kernel.length % 2 == 0) {
            throw new RuntimeException("O tamanho do kernel deve ser impar. O tamanho atual é par : " + kernel.length);
        }

        IntStream
                .range(0, entrada.length)
                .parallel()
                .forEach(posProc -> {
                    IntStream
                            .rangeClosed(-kernel.length / 2, kernel.length / 2)
                            .forEach(k -> ret[posProc] += getData(entrada, posProc + k) * kernel[k + (kernel.length / 2)]);
                });

        return ret;
    }

    public double[][] convolucao(double[][] entrada, boolean comTransposicao) {

        var entradaParaProcessar = comTransposicao ? MatrizAuxiliar.transpor(entrada) : entrada;

        double[][] ret = new double[entradaParaProcessar.length][entradaParaProcessar[0].length];

        int canais = entradaParaProcessar[0].length;

        Objects.requireNonNull(kernel, "O Kernel não foi definido é nullo");
        Objects.requireNonNull(entrada, "Os valores de entrada é nullo");

        if (kernel.length % 2 == 0) {
            throw new RuntimeException("O tamanho do kernel deve ser impar. O tamanho atual é par : " + kernel.length);
        }

        IntStream
                .range(0, entradaParaProcessar.length)
                .parallel()
                .forEach(posProc -> {
                    IntStream
                            .rangeClosed(-kernel.length / 2, kernel.length / 2)
                            .forEach(k ->
                                    IntStream
                                            .range(0, canais)
                                            .forEach(canal ->
                                                    ret[posProc][canal] += (getData(entradaParaProcessar, posProc + k, canal) * kernel[k + (kernel.length / 2)]))
                            );
                });

        return ret;
    }
}

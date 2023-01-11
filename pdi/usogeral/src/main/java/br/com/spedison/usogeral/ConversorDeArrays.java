package br.com.spedison.usogeral;

import java.util.stream.IntStream;

public class ConversorDeArrays {

    static public double[] converteFloatToDouble(float[] dado) {
        double[] ret = new double[dado.length];
        IntStream
                .range(0, dado.length)
                .forEach(pos ->
                        ret[pos] = dado[pos]);
        return ret;
    }

    static public float[] converteDoubleToFloat(double[] dado) {
        float[] ret = new float[dado.length];
        IntStream
                .range(0, dado.length)
                .forEach(pos ->
                        ret[pos] = (float) dado[pos]);
        return ret;
    }

    static public double[][] converteFloatToDouble(float[][] dado) {
        double[][] ret = new double[dado.length][dado[0].length];
        IntStream
                .range(0, dado.length)
                .forEach(posLinha -> {
                    IntStream
                            .range(0, dado[0].length)
                            .forEach(posColuna ->
                                    ret[posLinha][posColuna] = dado[posLinha][posColuna]);
                });
        return ret;
    }

    static public float[][] converteDoubleToFloat(double[][] dado) {
        float[][] ret = new float[dado.length][dado[0].length];
        IntStream
                .range(0, dado.length)
                .forEach(posLinha -> {
                    IntStream
                            .range(0, dado[0].length)
                            .forEach(posColuna ->
                                    ret[posLinha][posColuna] = (float) dado[posLinha][posColuna]);
                });
        return ret;
    }

}

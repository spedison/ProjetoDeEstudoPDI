package br.com.spedison.pds;

import br.com.spedison.usogeral.sinais.SinalSomaSenos;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MainTestaSeno {
    public static void main(String[] args) {
        SinalSomaSenos sss = new SinalSomaSenos(new double[]{14.}, new double[]{900});
        int pedacos = 100;
        double[] x = IntStream
                .range(0, pedacos)
                .mapToDouble(k -> k * (1. / 900.) * (1. / pedacos))
                .toArray();
        double[] y = Arrays
                .stream(x)
                .map(sss::calculaAmplitude)
                .toArray();
        IntStream
                .range(0, x.length)
                .mapToObj(pos -> "%f;%f".formatted(x[pos], y[pos]))
                .forEach(System.out::println);
    }
}

package br.com.spedison.usogeral.auxiliar.sinais;

import java.util.stream.IntStream;

public class SinalSomaSenos implements Sinal {

    double[] amplitudes = {1.D};
    double[] frequencias = {1.D};

    public SinalSomaSenos() {
    }

    public SinalSomaSenos(double[] amplitudes, double[] frequencias) {
        this.amplitudes = amplitudes;
        this.frequencias = frequencias;
    }

    @Override
    public double calculaAmplitude(double t) {
        final double[] soma = {.0D};
        IntStream.range(0, amplitudes.length).forEach(pos -> {
            soma[0] += amplitudes[pos] * Math.sin(frequencias[pos] * t);
        });
        return soma[0];
    }
}

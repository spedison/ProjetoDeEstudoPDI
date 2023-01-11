package br.com.spedison.usogeral.sinais;

public class SinalSomaSenos implements Sinal {

    private double[] amplitudes = {1.D};
    private double[] frequencias = {1.D};

    public SinalSomaSenos() {
    }

    public SinalSomaSenos(double[] amplitudes, double[] frequencias) {
        this.amplitudes = amplitudes;
        this.frequencias = frequencias;
    }

    @Override
    public double calculaAmplitude(double t) {
        double soma = .0D;
        for (int pos = 0; pos < amplitudes.length; pos++) {
            final var sin = Math.sin(2. * Math.PI * frequencias[pos] * t);
            soma = soma + (amplitudes[pos] * sin);
        }
        return soma;
    }

    @Override
    public String getNome() {
        return "Sinal soma de senos";
    }
}
package br.com.spedison.usogeral.sinais;

public class SinalSomaSenos implements Sinal {

    private double[] amplitudes = {1.D};
    private double[] frequencias = {1.D};

    private double[] fases = {0.D};

    public SinalSomaSenos() {
    }

    public SinalSomaSenos(double[] amplitudes, double[] frequencias) {
        this.amplitudes = amplitudes;
        this.frequencias = frequencias;
        this.fases = new double[frequencias.length];
    }

    public SinalSomaSenos(double[] amplitudes, double[] frequencias, double[] fases) {
        this.amplitudes = amplitudes;
        this.frequencias = frequencias;
        this.fases = fases;
    }

    @Override
    public double calculaAmplitude(double t) {
        double soma = .0D;
        for (int pos = 0; pos < amplitudes.length; pos++) {
            final var sin = Math.sin(2. * Math.PI * frequencias[pos] * t);
            soma = soma + (amplitudes[pos] * sin + fases[pos]);
        }
        return soma;
    }

    @Override
    public String getNome() {
        return "Sinal soma de senos";
    }
}
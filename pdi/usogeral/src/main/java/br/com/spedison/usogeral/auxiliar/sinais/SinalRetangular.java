package br.com.spedison.usogeral.auxiliar.sinais;

public class SinalRetangular implements Sinal {

    double tempoDaOnda = 10;
    double fracaoNoMaximo = .50;
    double amplitudeTempo1 = 0.D;
    double amplitudeTempo2 = 1.D;

    public SinalRetangular() {
    }

    public SinalRetangular(double tempoDaOnda, double fracaoNoMaximo, double amplitudeTempo1, double amplitudeTempo2) {
        this.tempoDaOnda = tempoDaOnda;
        this.fracaoNoMaximo = fracaoNoMaximo;
        this.amplitudeTempo1 = amplitudeTempo1;
        this.amplitudeTempo2 = amplitudeTempo2;
    }

    @Override
    public double calculaAmplitude(double t) {
        double tempoOnda = Math.abs(t % tempoDaOnda);

        return (Math.signum(t) >= 0.) ?
                (tempoOnda <= fracaoNoMaximo * tempoDaOnda ? amplitudeTempo1 : amplitudeTempo2) :
                (tempoOnda <= fracaoNoMaximo * tempoDaOnda ? amplitudeTempo2 : amplitudeTempo1);
    }
}

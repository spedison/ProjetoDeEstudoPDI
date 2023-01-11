package br.com.spedison.usogeral.sinais;

public class SinalDenteDeSerra implements Sinal {

    double tempoDaOnda = 3.D;
    double coeficienteAngular = 20.0;
    double coeficienteLinear = 0.D;

    public SinalDenteDeSerra() {
    }

    public SinalDenteDeSerra(double tempoDaOnda, double coeficienteAngular, double coeficienteLinear) {
        this.tempoDaOnda = tempoDaOnda;
        this.coeficienteAngular = coeficienteAngular;
        this.coeficienteLinear = coeficienteLinear;
    }

    @Override
    public double calculaAmplitude(double t) {
        double tempoDaOndaAtual = Math.abs(t % tempoDaOnda);

        return (Math.signum(t) >= 0.) ?
                (coeficienteAngular * tempoDaOndaAtual + coeficienteLinear) :
                ((Math.abs(tempoDaOnda) - tempoDaOndaAtual) * coeficienteAngular + coeficienteLinear);

    }

    @Override
    public String getNome() {
        return "Sinal Dente de Serra com tempo %f e Coeficiente Angular %f e Coeficiente Linear %f".formatted(tempoDaOnda, coeficienteAngular, coeficienteLinear);
    }
}

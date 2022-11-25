package br.com.spedison.main.imagem_padrao;

public class Circulo {

    private double raio;
    private double centroX;
    private double centroY;

    public Circulo(double raio) {
        this(raio, 0., 0.);
    }

    public Circulo(double raio, double centroX, double centroY) {
        this.raio = raio;
        this.centroX = centroX;
        this.centroY = centroY;
    }

    public boolean estaDentro(double x, double y) {
        double xVal = x - centroX;
        double yVal = y - centroY;
        return (xVal * xVal + yVal * yVal <= raio * raio);
    }

    public double calculaRaio(double x, double y) {
        double xVal = x - centroX;
        double yVal = y - centroY;
        return Math.sqrt(xVal * xVal + yVal * yVal);
    }

    public double calculaAngulo(double x, double y) {
        double xVal = x - centroX;
        double yVal = y - centroY;
        return Math.toDegrees(Math.atan(yVal / xVal));
    }
}

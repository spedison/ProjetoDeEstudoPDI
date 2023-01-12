package br.com.spedison.pds.auxiliar;

public record Complexo (double real, double imaginario){

    final static public Complexo Zero = new Complexo(0., 0.);
    final static public Complexo Um = new Complexo(1., 1.);

    public double getModulo() {
        return Math.sqrt(Math.pow(imaginario, 2.) + Math.pow(real, 2.));
    }

    public double getTheta() {
        return Math.atan2(imaginario, real);
    }

    public Complexo mutiplica(double escalar) {
        return new Complexo(this.real * escalar, this.imaginario * escalar);
    }

    public Complexo mutiplica(Complexo valor) {
        var v1R = valor.real() * real();
        var v2I = valor.imaginario() * real();
        var v3I = valor.real() * imaginario();
        var v4R = -1. * valor.imaginario() * imaginario();
        return new Complexo(v1R + v4R, v2I + v3I);
    }

    public Complexo soma(Complexo valor) {
        return new Complexo(this.real + valor.real,
                this.imaginario + valor.imaginario);
    }

    public Complexo soma(double real) {
        return new Complexo(this.real + real, this.imaginario);
    }

    public static Complexo expPositivo(double imaginario) {
        return new Complexo(Math.cos(imaginario), Math.sin(imaginario));
    }

    public static Complexo expNegativo(double imaginario) {
        return new Complexo(Math.cos(imaginario), -Math.sin(imaginario));
    }

    public static Complexo getZero() {
        return Zero;
    }
    public static Complexo getUm() {
        return Um;
    }

}

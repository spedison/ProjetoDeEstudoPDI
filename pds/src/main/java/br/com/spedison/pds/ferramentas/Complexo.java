package br.com.spedison.pds.ferramentas;

public class Complexo {

    final static public Complexo Zero = new Complexo(0., 0.);
    final static public Complexo Um = new Complexo(1., 1.);

    public double getReal() {
        return real;
    }


    public double getImaginario() {
        return imaginario;
    }

    private double real;
    private double imaginario;

    public Complexo(double real, double imaginario) {
        this.real = real;
        this.imaginario = imaginario;
    }

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
        var v1R = valor.getReal() * getReal();
        var v2I = valor.getImaginario() * getReal();
        var v3I = valor.getReal() * getImaginario();
        var v4R = -1. * valor.getImaginario() * getImaginario();
        return new Complexo(v1R + v4R, v2I + v3I);
    }

    public Complexo soma(Complexo valor) {
        return new Complexo(this.real + valor.real,
                this.imaginario + valor.imaginario);
    }

    public Complexo soma(double real) {
        return new Complexo(this.real + real, this.imaginario);
    }

    public static Complexo exp(double imaginario) {
        var ret = new Complexo(Math.cos(imaginario), Math.sin(imaginario));
        return ret;
    }

    public static Complexo getIdentidade() {
        return Zero;
    }

    @Override
    public String toString() {
        return "%.12f\t%.12f\n".formatted(real, imaginario);
    }
}

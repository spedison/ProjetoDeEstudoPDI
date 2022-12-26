package br.com.spedison.fourier;

public class Complexo {
    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getImaginario() {
        return imaginario;
    }

    public void setImaginario(double imaginario) {
        this.imaginario = imaginario;
    }

    double real;
    double imaginario;

    public Complexo(double real, double imaginario) {
        this.real = real;
        this.imaginario = imaginario;
    }

    public double getModulo() {
        return Math.sqrt(Math.pow(imaginario, 2.) + Math.pow(real, 2.));
    }

    public double getTeta() {
        return Math.atan2(imaginario, real);
    }

    public Complexo mutiplica(double escalar) {
        real *= escalar;
        imaginario *= escalar;
        return this;
    }

    public Complexo mutiplica(Complexo valor) {
        var v1R = valor.getReal() * getReal();
        var v2I = valor.getImaginario() * getReal();
        var v3I = valor.getReal() * getImaginario();
        var v4R = -1. * valor.getImaginario() * getImaginario();
        this.setImaginario(v2I + v3I);
        this.setReal(v1R + v4R);
        return this;
    }

    public Complexo soma(Complexo valor) {
        this.real += valor.real;
        this.imaginario += valor.imaginario;
        return this;
    }

    public Complexo soma(double real) {
        this.real += real;
        return this;
    }

    public static Complexo exp(double imaginario) {
        var ret = new Complexo(Math.cos(2. * Math.PI * Math.abs(imaginario)), Math.sin(2. * Math.PI * Math.abs(imaginario)));
        if (Math.signum(imaginario) > 0.)
            ret.setImaginario(-1 * ret.getImaginario());
        return ret;
    }

    public static Complexo getIdentidade() {
        return new Complexo(0., 0.);
    }

    @Override
    public String toString() {
        return "%.12f\t%.12f\n".formatted(real, imaginario);
    }
}

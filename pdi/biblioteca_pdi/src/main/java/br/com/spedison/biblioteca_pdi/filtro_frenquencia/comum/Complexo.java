package br.com.spedison.biblioteca_pdi.filtro_frenquencia.comum;

public class Complexo {
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

    @Override
    public String toString() {
        return "%.12f\t%.12f\n".formatted(real, imaginario) ;
    }
}

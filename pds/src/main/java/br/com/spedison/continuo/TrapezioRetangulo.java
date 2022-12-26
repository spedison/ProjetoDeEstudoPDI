package br.com.spedison.continuo;

import br.com.spedison.fourier.Complexo;

public class TrapezioRetangulo {
    private final double h;
    private final Complexo p1;
    private final Complexo p2;

    public TrapezioRetangulo(double h, Complexo p1, Complexo p2) {
        this.h = h;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Complexo getArea() {
        return p1
                .soma(p2)
                .mutiplica(.5)
                .mutiplica(h);
    }

    @Override
    public String toString() {
        return "TrapezioRetangulo{" +
                "  altura = " + h +
                ", p1 = " + p1 +
                ", p2 = " + p2 +
                ", area = " + getArea() +
                "  }";
    }
}
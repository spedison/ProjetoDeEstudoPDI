package br.com.spedison.pds.ferramentas;

public class TrapezioRetangulo {
    private final double h;
    private final Complexo p1;
    private final Complexo p2;

    private final Complexo area;
    public TrapezioRetangulo(double h, Complexo p1, Complexo p2) {
        this.h = h;
        this.p1 = p1;
        this.p2 = p2;
        this.area = p1
                .soma(p2)
                .mutiplica(.5 * h);
    }

    public Complexo getArea() {
        return area;
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
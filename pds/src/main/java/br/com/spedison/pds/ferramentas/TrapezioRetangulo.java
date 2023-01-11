package br.com.spedison.pds.ferramentas;

public record TrapezioRetangulo(double h, Complexo p1, Complexo p2) {
    public Complexo getArea() {
        return p1
                .soma(p2)
                .mutiplica(.5 * h);
    }
}
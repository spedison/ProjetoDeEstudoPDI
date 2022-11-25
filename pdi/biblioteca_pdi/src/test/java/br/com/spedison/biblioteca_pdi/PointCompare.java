package br.com.spedison.biblioteca_pdi;

import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.InferePontoCubica2Test;

import java.awt.*;

public class PointCompare extends Point {

    public PointCompare(Point p) {
        super(p);
    }

    @Override
    public boolean equals(Object obj) {
        Point o = (PointCompare) obj;
        return o.x == this.x && o.y == this.y;
    }

    public double dist(double x, double y) {
        double dx = Math.abs(x - this.x);
        double dy = Math.abs(y - this.y);
        return Math.sqrt(dx * dx + dy * dy);
    }
}

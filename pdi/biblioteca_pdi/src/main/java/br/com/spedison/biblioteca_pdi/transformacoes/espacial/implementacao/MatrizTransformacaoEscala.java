package br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class MatrizTransformacaoEscala extends MatrizTransformacaoEspacial {

    double escalaX;
    double escalaY;

    public MatrizTransformacaoEscala() {
        super();
    }

    public MatrizTransformacaoEscala(double escalaX, double escalaY) {
        setEscala(escalaX,escalaY);
    }

    private void setEscala(double escalaX, double escalaY) {
        this.escalaX = escalaX;
        this.escalaY = escalaY;
        direta.setEntry(0, 0, escalaX);
        direta.setEntry(1, 1, escalaY);
        calculaInverso();
    }

    public double getEscalaX() {
        return escalaX;
    }

    public MatrizTransformacaoEscala setEscalaX(double escalaX) {
        setEscala(escalaX, escalaY);
        return this;
    }

    public double getEscalaY() {
        return escalaY;
    }

    public MatrizTransformacaoEscala setEscalaY(double escalaY) {
        setEscala(escalaX, escalaY);
        return this;
    }
}

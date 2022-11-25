package br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class MatrizTransformacaoTranslacao extends MatrizTransformacaoEspacial {

    double passoX;
    double passoY;


    public MatrizTransformacaoTranslacao() {
        super();
    }

    public MatrizTransformacaoTranslacao(double passoX, double passoY) {
        setPasso(passoX, passoY);
    }

    private void setPasso(double passoX, double passoY) {
        this.passoX = passoX;
        this.passoY = passoY;
        direta.setEntry(2, 0, passoX);
        direta.setEntry(2, 1, passoY);
        calculaInverso();
    }

    public double getPassoX() {
        return passoX;
    }

    public MatrizTransformacaoTranslacao setPassoX(double passoX) {
        setPasso(passoX, passoY);
        return this;
    }

    public double getPassoY() {
        return passoY;
    }

    public MatrizTransformacaoTranslacao setPassoY(double passoY) {
        setPasso(passoX, passoY);
        return this;
    }

}

package br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class MatrizTransformacaoCisalhamentoVertical extends MatrizTransformacaoEspacial {

    double escalaX;

    public MatrizTransformacaoCisalhamentoVertical() {
        super();
    }

    public MatrizTransformacaoCisalhamentoVertical(double escalaX) {
        setEscalaX(escalaX);
    }

    public double getEscalaX() {
        return escalaX;
    }

    public MatrizTransformacaoCisalhamentoVertical setEscalaX(double escalaX) {
        this.escalaX = escalaX;
        direta.setEntry(1, 0, escalaX);
        calculaInverso();
        return this;
    }

}

package br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/***
 *
 * Referência: https://www.pucsp.br/~jarakaki/cgpi/Apos_Tranf_2D.pdf
 */
public class MatrizTransformacaoRotacao extends MatrizTransformacaoEspacial {

    double anguloGraus = 10;

    public MatrizTransformacaoRotacao() {
        super();
        setAnguloGraus(anguloGraus);
    }

    public MatrizTransformacaoRotacao(double anguloGraus) {
        setAnguloGraus(anguloGraus);
    }

    public double getAnguloGraus() {
        return anguloGraus;
    }

    public MatrizTransformacaoRotacao setAnguloGraus(double anguloGraus) {
        this.anguloGraus = anguloGraus;
        direta.setEntry(0, 0, Math.cos(converteGrausEmRadianos(anguloGraus)));
        direta.setEntry(0, 1, Math.sin(converteGrausEmRadianos(anguloGraus)));

        direta.setEntry(1, 0, -Math.sin(converteGrausEmRadianos(anguloGraus)));
        direta.setEntry(1, 1, Math.cos(converteGrausEmRadianos(anguloGraus)));

        calculaInverso();
        return this;
    }

    private double converteGrausEmRadianos(double graus) {
        return (graus * Math.PI) / 180.0;
    }
}

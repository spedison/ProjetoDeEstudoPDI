package br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public abstract class MatrizTransformacaoEspacial {

    private RealMatrix divisor = null;
    protected RealMatrix direta = MatrixUtils.createRealMatrix(new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});

    int somaX = 0;
    int somaY = 0;

    public MatrizTransformacaoEspacial() {

    }

    public int getSomaX() {
        return somaX;
    }

    public void setSomaX(int somaX) {
        this.somaX = somaX;
    }

    public int getSomaY() {
        return somaY;
    }

    public void setSomaY(int somaY) {
        this.somaY = somaY;
    }

    public RealMatrix getMatrixDireta() {
        return direta;
    }

    public void calculaInverso() {
        divisor = MatrixUtils.inverse(getMatrixDireta());
    }

    public double[] calculaPontoInverso(double x, double y) {
        var entradaData = new double[][]{{x, y, 1.0}}; //  1 x 3
        var entrada = MatrixUtils.createRealMatrix(entradaData);
        var ret1 = entrada.multiply(divisor);
        double[] ret = {ret1.getRow(0)[0], ret1.getRow(0)[1]};
        return ret;
    }

    public double[] calculaPontoInverso(int x, int y) {
        return calculaPontoInverso((double) x, (double) y);
    }

    public int[] calculaPontoDireto(double x, double y) {
        var entradaData = new double[][]{{x, y, 1.0}}; //  1 x 3
        var entrada = MatrixUtils.createRealMatrix(entradaData);
        var ret1 = entrada.multiply(getMatrixDireta());
        int[] ret = new int[2];
        ret[0] = (int) (ret1.getRow(0)[0] + .5) - somaX;
        ret[1] = (int) (ret1.getRow(0)[1] + .5) - somaY;
        return ret;
    }

    public int[] calculaPontoDireto(int x, int y) {
        return calculaPontoDireto((double) x, (double) y);
    }

}

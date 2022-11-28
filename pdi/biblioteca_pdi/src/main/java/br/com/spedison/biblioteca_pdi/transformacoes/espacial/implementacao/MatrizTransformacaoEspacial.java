package br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import br.com.spedison.usogeral.auxiliar.Arredondador;

public abstract class MatrizTransformacaoEspacial {

    private RealMatrix divisor = null;
    protected RealMatrix direta = MatrixUtils.createRealMatrix(new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});

    public MatrizTransformacaoEspacial() {

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

    public double[] calculaPontoDireto(double x, double y) {
        var entradaData = new double[][]{{x, y, 1.0}}; //  1 x 3
        var entrada = MatrixUtils.createRealMatrix(entradaData);
        var ret1 = entrada.multiply(getMatrixDireta());
        double[] ret = {ret1.getRow(0)[0], ret1.getRow(0)[1]};
        return ret;
    }

    public int[] calculaPontoDireto(int x, int y) {
        double[] ret = calculaPontoDireto((double) x, (double) y);
        return new int[]{Arredondador.arredonda(ret[0]), Arredondador.arredonda(ret[0])};
    }

}

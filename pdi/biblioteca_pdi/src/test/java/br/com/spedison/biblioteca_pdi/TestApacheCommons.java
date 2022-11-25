package br.com.spedison.biblioteca_pdi;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Test;

public class TestApacheCommons {

    @Test
    public void testaInversaoMatrix(){
        double[][] matrixData = new double[][]{
                {1, 0, 0, 0},
                {1, 0, 1, 0},
                {1, 1, 0, 0},
                {1, 1, 1, 1}
        };

        double[][] dobleUnitData = new double[][]{
                {2, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 2}
        };

        RealMatrix matrix = MatrixUtils.createRealMatrix(matrixData);
        RealMatrix doubleUnit = MatrixUtils.createRealMatrix(dobleUnitData);
        RealMatrix inverse = MatrixUtils.inverse(matrix);
        RealMatrix doubleUnitMult = inverse.multiply(doubleUnit);
        System.out.println(doubleUnitMult);
        System.out.println(doubleUnitMult.getEntry(0,0));
    }

}

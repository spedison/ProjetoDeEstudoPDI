package br.com.spedison.usogeral.auxiliar;

import org.junit.Assert;
import org.junit.Test;

public class ImprimeArrayTest {

    @Test
    public void testImprimeInt2D() {
        ImprimeArray.imprimeInt2D(new int[][]{{10, 10, 20}, {20, 20, 30, 30}, {40, 50, 60}});
    }

    @Test
    public void testImprimeDouble2D() {
        ImprimeArray.imprimeDouble2D(new double[][]{{12.111, 10.333, 20.555}, {2330.666, 20.888, 35.77722}, {40.555, 50.77777, 60.8888}});
    }

    @Test
    public void teststrInt2D() {
        int[][] dados = new int[][]{{1, 2, 3}, {6, 9, 12}, {15, 18, 55}};
        String str = "1\t2\t3\t\n6\t9\t12\t\n15\t18\t55\t\n";
        String resultado = ImprimeArray.strInt2D(dados);
        Assert.assertEquals(str, resultado);
    }

    @Test
    public void testTestImprimeDouble2D() {
        double[][] dados = new double[][]{{1.1, 2.2, 3.3}, {6.4, 9.1, 12.4}, {15.01, 18.5, 55.09}};
        String str = "        1,10\t        2,20\t        3,30\t\n        6,40\t        9,10\t       12,40\t\n       15,01\t       18,50\t       55,09\t\n";
        String resultado = ImprimeArray.strDouble2D(dados, 12, 2);
        Assert.assertEquals(str, resultado);
    }

}
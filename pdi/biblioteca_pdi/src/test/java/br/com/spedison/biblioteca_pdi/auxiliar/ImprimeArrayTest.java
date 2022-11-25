package br.com.spedison.biblioteca_pdi.auxiliar;

import junit.framework.TestCase;

public class ImprimeArrayTest extends TestCase {

    public void testImprimeInt2D() {
        ImprimeArray.imprimeInt2D(new int[][]{{10, 10, 20}, {20, 20, 30, 30}, {40, 50, 60}});
    }

    public void testImprimeDouble2D() {
        ImprimeArray.imprimeDouble2D(new double[][]{{12.111, 10.333, 20.555}, {2330.666, 20.888, 30.101010, 35.77722}, {40.555, 50.77777, 60.8888}});
    }
}
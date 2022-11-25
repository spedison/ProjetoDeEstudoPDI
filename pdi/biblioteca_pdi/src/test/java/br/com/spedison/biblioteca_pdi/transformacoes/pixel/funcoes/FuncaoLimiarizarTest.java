package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class FuncaoLimiarizarTest {

    @Test
    public void testLimpaIntervalos() {
        FuncaoLimiarizar fl = new FuncaoLimiarizar();
        fl.addLimite(0, 10, 128);
        fl.limpaIntervalos();
        int ret = fl.transforma(0, 0, 50);
        Assert.assertEquals(0, ret);

    }

    @Test
    public void testAddLimiteSuperior() {
        FuncaoLimiarizar fl = new FuncaoLimiarizar(0, 255);

        fl.addLimite(0, 10, 1);
        fl.addLimite(11, 20, 2);
        fl.addLimite(21, 30, 3);

        int ret = fl.transforma(0, 0, 0);
        Assert.assertEquals(1, ret);

        ret = fl.transforma(0, 0, 10);
        Assert.assertEquals(1, ret);

        ret = fl.transforma(0, 0, 11);
        Assert.assertEquals(2, ret);

        ret = fl.transforma(0, 0, 21);
        Assert.assertEquals(3, ret);

        ret = fl.transforma(0, 0, 50);
        Assert.assertEquals(fl.getMaximo(), ret);

        ret = fl.transforma(0, 0, 30);
        Assert.assertEquals(3, ret);

        ret = fl.transforma(0, 0, 31);
        Assert.assertEquals(fl.getMaximo(), ret);

    }

    @Test
    public void testAddLimiteInferior() {
        FuncaoLimiarizar fl = new FuncaoLimiarizar(0, 255);
        fl.addLimite(5, 10, 1);
        fl.addLimite(11, 20, 2);
        fl.addLimite(21, 30, 3);
        fl.addLimite(31, fl.maximo, 4);

        int ret = fl.transforma(0, 0, 0);
        Assert.assertEquals(0, ret);

        ret = fl.transforma(0, 0, 1);
        Assert.assertEquals(0, ret);

        ret = fl.transforma(0, 0, 4);
        Assert.assertEquals(0, ret);

        ret = fl.transforma(0, 0, 5);
        Assert.assertEquals(1, ret);

        ret = fl.transforma(0, 0, 6);
        Assert.assertEquals(1, ret);

        ret = fl.transforma(0, 0, 9);
        Assert.assertEquals(1, ret);

        ret = fl.transforma(0, 0, 10);
        Assert.assertEquals(1, ret);

        ret = fl.transforma(0, 0, 11);
        Assert.assertEquals(2, ret);

        ret = fl.transforma(0, 0, 20);
        Assert.assertEquals(2, ret);

        ret = fl.transforma(0, 0, 30);
        Assert.assertEquals(3, ret);

        ret = fl.transforma(0, 0, 31);
        Assert.assertEquals(4, ret);

        ret = fl.transforma(0, 0, 50);
        Assert.assertEquals(4, ret);

        ret = fl.transforma(0, 0, fl.maximo);
        Assert.assertEquals(4, ret);

    }

    @Test
    public void testIntervaloSemDefinicao() {
        FuncaoLimiarizar fl = new FuncaoLimiarizar(0, 255);
        fl.addLimite(5, 10, 1);//deixei um intervalo sem definiçáo entre 11 e 15.
        fl.addLimite(15, 20, 2);
        fl.addLimite(21, 30, 3);
        fl.addLimite(31, fl.maximo, 4);

        int ret = fl.transforma(0, 0, 0);
        Assert.assertEquals(0, ret);

        ret = fl.transforma(0, 0, 1);
        Assert.assertEquals(0, ret);

        ret = fl.transforma(0, 0, 4);
        Assert.assertEquals(0, ret);

        ret = fl.transforma(0, 0, 15);
        Assert.assertEquals(2, ret);


        ret = fl.transforma(0, 0, 12);
        Assert.assertEquals(fl.getMinimo(), ret, 0.1);

        ret = fl.transforma(0, 0, 10);
        Assert.assertEquals(1, ret);

        ret = fl.transforma(0, 0, 11);
        Assert.assertEquals(fl.getMinimo(), ret, 0.1);

        ret = fl.transforma(0, 0, 30);
        Assert.assertEquals(3, ret);

        ret = fl.transforma(0, 0, 31);
        Assert.assertEquals(4, ret);

        ret = fl.transforma(0, 0, 50);
        Assert.assertEquals(4, ret);

        ret = fl.transforma(0, 0, fl.maximo);
        Assert.assertEquals(4, ret);

    }

}
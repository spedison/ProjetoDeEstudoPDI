package br.com.spedison.biblioteca_pdi.auxiliar;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArredondadorTest {

    @Test
    public void testArredonda() {
        double v1 = 11.45;
        int r1 = 11;
        int proximo = Arredondador.arredonda(v1);
        assertEquals(proximo, r1);

        v1 = -11.45;
        r1 = -11;
        proximo = Arredondador.arredonda(v1);
        assertEquals(proximo, r1);

        v1 = -13.51;
        r1 = -14;
        proximo = Arredondador.arredonda(v1);
        assertEquals(proximo, r1);

        v1 = 13.51;
        r1 = 14;
        proximo = Arredondador.arredonda(v1);
        assertEquals(proximo, r1);
    }

    @Test
    public void testProximoMaiorNumeroEmModulo() {
        double v1 = 11.45;
        double r1 = 12.;
        int proximo = Arredondador.proximoMaiorNumeroEmModulo(v1);
        Assert.assertEquals(proximo, r1, 0.001);

        v1 = -15.46;
        r1 = -16;
        proximo = Arredondador.proximoMaiorNumeroEmModulo(v1);
        Assert.assertEquals(proximo, r1, 0.001);
    }

    @Test
    public void testMenorNuneroEmModulo() {
        double v1 = 11.45;
        double r1 = 11.;
        int proximo = Arredondador.menorNumeroEmModulo(v1);
        assertEquals(proximo, r1, 0.001);

        v1 = -15.46;
        r1 = -15;
        proximo = Arredondador.menorNumeroEmModulo(v1);
        assertEquals(proximo, r1, 0.001);

    }

    @Test
    public void testParteFracionaria() {
        double v1 = 11.045;
        double r1 = .045;
        assertEquals(Arredondador.parteFracionaria(v1), r1, 0.0001);
        v1 = -11.045;
        r1 = -.045;
        assertEquals(Arredondador.parteFracionaria(v1), r1, 0.0001);
    }

    @Test
    public void testProximoMenorNumeroEmModulo() {
        double v1 = 11.45;
        double r1 = 10.;
        int proximo = Arredondador.proximoMenorNumeroEmModulo(v1);
        Assert.assertEquals(proximo, r1, 0.001);

        v1 = -15.46;
        r1 = -14.;
        proximo = Arredondador.proximoMenorNumeroEmModulo(v1);
        Assert.assertEquals(proximo, r1, 0.001);
    }

    @Test
    public void testMenorNumeroEmModulo() {
        double v1 = 11.45;
        double r1 = 11.;
        int proximo = Arredondador.menorNumeroEmModulo(v1);
        Assert.assertEquals(proximo, r1, 0.001);

        v1 = -15.9999;
        r1 = -15.;
        proximo = Arredondador.menorNumeroEmModulo(v1);
        Assert.assertEquals(proximo, r1, 0.001);
    }

    @Test
    public void testTestProximoNumeroInteiroEmModulo() {
        double v1 = 11.45;
        double r1 = 12.;
        int proximo = Arredondador.proximoNumeroInteiroEmModulo(v1);
        Assert.assertEquals(proximo, r1, 0.001);

        v1 = -15.9999;
        r1 = -16.;
        proximo = Arredondador.proximoNumeroInteiroEmModulo(v1);
        Assert.assertEquals(proximo, r1, 0.001);
    }

    @Test
    public void testComplemento() {
        double complemento = 10.;
        double valor = 4.;
        double resultado = 6.;
        assertEquals(Arredondador.complemento(valor, complemento), resultado, .0001);
        valor = 4.2;
        resultado = -3.2;
        assertEquals(Arredondador.complemento(valor), resultado, .0001);
    }

    @Test
    public void testMenorNumeroProximoInt() {
        double v1 = 11.45;
        int r1 = 11;
        int proximo = Arredondador.menorNumeroProximoInt(v1);
        Assert.assertEquals(proximo, r1);

        v1 = -15.9999;
        r1 = -16;
        proximo = Arredondador.menorNumeroProximoInt(v1);
        Assert.assertEquals(proximo, r1);
    }

}
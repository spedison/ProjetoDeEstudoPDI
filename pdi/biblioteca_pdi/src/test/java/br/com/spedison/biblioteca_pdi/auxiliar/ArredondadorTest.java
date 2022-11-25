package br.com.spedison.biblioteca_pdi.auxiliar;

import org.junit.Assert;
import org.junit.Test;

public class ArredondadorTest {

    @Test
    public void testArredonda() {
        double v1 = 11.45;
        int r1 = 11;
        int proximo = Arredondador.arredonda(v1);
        Assert.assertEquals(proximo, r1);

        v1 = -11.45;
        r1 = -11;
        proximo = Arredondador.arredonda(v1);
        Assert.assertEquals(proximo, r1);

        v1 = -13.51;
        r1 = -14;
        proximo = Arredondador.arredonda(v1);
        Assert.assertEquals(proximo, r1);

        v1 = 13.51;
        r1 = 14;
        proximo = Arredondador.arredonda(v1);
        Assert.assertEquals(proximo, r1);

    }

    @Test
    public void testMaiorNumeroEmModulo() {
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
        Assert.assertEquals(proximo, r1, 0.001);

        v1 = -15.46;
        r1 = -15;
        proximo = Arredondador.menorNumeroEmModulo(v1);
        Assert.assertEquals(proximo, r1, 0.001);

    }

    public void testParteFracionaria() {
    }
}
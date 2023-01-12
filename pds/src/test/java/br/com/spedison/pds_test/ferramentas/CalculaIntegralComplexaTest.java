package br.com.spedison.pds_test.ferramentas;

import br.com.spedison.pds.auxiliar.CalculaIntegralComplexa;
import br.com.spedison.pds.auxiliar.Complexo;
import org.junit.Assert;
import org.junit.Test;

public class CalculaIntegralComplexaTest {

    @Test
    public void getIntegral() {

        CalculaIntegralComplexa.Funcao1Complexa funcao1 = (double x) ->
                new Complexo(Math.pow(x, 2.), Math.pow(x, 3.));

        CalculaIntegralComplexa.Funcao1Complexa funcao2 = (double x) ->
                new Complexo(Math.pow(x, 4.), Math.pow(x, 4.));

        CalculaIntegralComplexa calculaIntegralComplexa = new CalculaIntegralComplexa();
        calculaIntegralComplexa.setFuncaoComplexa(funcao1);
        calculaIntegralComplexa.setPasso(.0000001);
        var inicio = System.currentTimeMillis();
        var r = calculaIntegralComplexa.getIntegral(-10., 10.);
        var fim = System.currentTimeMillis();

        Assert.assertEquals(0., 0., 0.00001);
        Assert.assertEquals(0., r.imaginario(), 0.001);
        Assert.assertEquals(666.66666666666, r.real(), 0.00000001);
        System.out.printf("Tempo gasto T1 foi %d ms\n",fim - inicio);

        calculaIntegralComplexa.setFuncaoComplexa(funcao2);
        inicio = System.currentTimeMillis();
        r = calculaIntegralComplexa.getIntegral(-10., 10.);
        fim = System.currentTimeMillis();


        Assert.assertEquals(r.imaginario(), r.real(), 0.0000001);
        Assert.assertEquals(4E4, r.imaginario(), 0.001);
        System.out.printf("Tempo gasto T2 foi %d ms\n",fim - inicio);
    }
}
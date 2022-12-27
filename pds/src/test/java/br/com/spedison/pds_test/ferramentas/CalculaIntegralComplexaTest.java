package br.com.spedison.pds_test.ferramentas;

import br.com.spedison.pds.ferramentas.CalculaIntegralComplexa;
import br.com.spedison.pds.ferramentas.Complexo;
import org.junit.Assert;
import org.junit.Test;

public class CalculaIntegralComplexaTest {

    @Test
    public void getIntegral() {

        CalculaIntegralComplexa.Funcao1Complexa funcao = (double x) ->
                new Complexo(Math.pow(x, 2.), Math.pow(x, 3.));


        CalculaIntegralComplexa calculaIntegralComplexa = new CalculaIntegralComplexa();
        calculaIntegralComplexa.setFuncaoComplexa(funcao);
        calculaIntegralComplexa.setPasso(.0000001);
        var inicio = System.currentTimeMillis();
        var r = calculaIntegralComplexa.getIntegral(-10., 10.);
        var fim = System.currentTimeMillis();

        Assert.assertEquals(0., 0., 0.00001);
        Assert.assertEquals(0., r.getImaginario(), 0.001);
        Assert.assertEquals(666.66666666666, r.getReal(), 0.00000001);
        System.out.println("Tempo gasto foi %d ms".formatted(fim - inicio));

    }
}
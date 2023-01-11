package br.com.spedison.usogeral_testes.sinais;

import br.com.spedison.usogeral.sinais.SinalSomaSenos;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

public class SinalSomaSenosTest {

    @Test
    public void testCalculaAmplitude() {

        final var t = 1. / 900.;
        final var f = 1. / t;
        final var max = 1000;
        final var amp = 14.;

        SinalSomaSenos sss = new SinalSomaSenos(new double[]{amp}, new double[]{f});
        IntStream
                .rangeClosed(0, max)
                .mapToDouble(x -> t * ((double) x / max))
                .map(s -> {
                    System.out.println("Testando : %f".formatted(s));
                    return s;
                })
                .forEach(x ->
                        Assert.assertEquals(amp * Math.sin(2. * Math.PI * f * x),
                                sss.calculaAmplitude(x), 0.01));
    }

    @Test
    public void testCalculaAmplitude1Ponto() {

        final var t = 1. / 900.;
        final var f = 1. / t;
        final var amp = 14.;

        SinalSomaSenos sss = new SinalSomaSenos(new double[]{amp}, new double[]{f});
        final double s = 0.000029;
        System.out.println("Testando : %f".formatted(s));
        Assert.assertEquals(amp * Math.sin(2. * Math.PI * f * s),
                sss.calculaAmplitude(s), 0.01);
    }


}
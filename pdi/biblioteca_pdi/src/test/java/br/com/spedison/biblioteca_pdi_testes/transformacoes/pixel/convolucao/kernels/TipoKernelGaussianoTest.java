package br.com.spedison.biblioteca_pdi_testes.transformacoes.pixel.convolucao.kernels;

import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels.TipoKernelGaussiano;
import br.com.spedison.usogeral.auxiliar.ImprimeArray;
import org.junit.Assert;
import org.junit.Test;

public class TipoKernelGaussianoTest {

    @Test
    public void testCriaDadosKernel() {
        final double multiplicador = 1000.;
        final double erro = 0.0001;

        TipoKernelGaussiano tkg = new TipoKernelGaussiano(0., 0., 1., 5);


        double [] [] compare =
        {{2.9690,    13.3062,    21.9382,    13.3062,     2.9690},
        {13.3062,    59.6343,    98.3203,    59.6343,    13.3062},
        {21.9382,    98.3203,   162.1028,    98.3203,    21.9382},
        {13.3062,    59.6343,    98.3203,    59.6343,    13.3062},
        {2.9690 ,   13.3062 ,   21.9382 ,   13.3062 ,    2.9690}};


        tkg.criaDadosKernel(1., 0.);
        tkg.normalizaSomaUnitaria(multiplicador);
        double[][][] dados = tkg.getData1();

        ImprimeArray.imprimeDouble2D(dados[0], 7, 3);

        final var soma = tkg.getSoma(0);
        Assert.assertEquals(multiplicador, soma, 0.001);
        int i = 0;

        Assert.assertArrayEquals(dados[0][i],compare[i],erro); i++;
        Assert.assertArrayEquals(dados[0][i],compare[i],erro); i++;
        Assert.assertArrayEquals(dados[0][i],compare[i],erro); i++;
        Assert.assertArrayEquals(dados[0][i],compare[i],erro); i++;
        Assert.assertArrayEquals(dados[0][i],compare[i],erro);

    }
}
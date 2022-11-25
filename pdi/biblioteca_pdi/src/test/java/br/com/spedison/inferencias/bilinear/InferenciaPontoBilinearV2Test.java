package br.com.spedison.inferencias.bilinear;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.ResultadoInferencia;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.InferenciaPontoBilinearV1;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.InferenciaPontoBilinearV2;
import org.junit.Assert;
import org.junit.Test;

public class InferenciaPontoBilinearV2Test {

    @Test
    public void testaInteiros(){
        System.out.println(Math.floor(+10.45));
        System.out.println(Math.floor(-10.45));
    }

    @Test
    public void inferePonto() {


        double pos = 0.8;
        var imagem = new Imagem(3, 3);
        imagem.setPixel(0, 0, new int[]{70, 70, 70});
        imagem.setPixel(0, 1, new int[]{70, 70, 70});
        imagem.setPixel(0, 2, new int[]{70, 70, 70});

        imagem.setPixel(1, 0, new int[]{90, 90, 90});
        imagem.setPixel(1, 1, new int[]{90, 90, 90});
        imagem.setPixel(1, 2, new int[]{90, 90, 90});

        imagem.setPixel(2, 0, new int[]{110, 110, 110});
        imagem.setPixel(2, 1, new int[]{110, 110, 110});
        imagem.setPixel(2, 2, new int[]{110, 110, 110});
        imagem.setOrigemX(-1);
        imagem.setOrigemY(-1);

        var inferePonto = new InferenciaPontoBilinearV2();
        var resultado = new ResultadoInferencia();
        inferePonto.setImagem(imagem);
        inferePonto.inferePonto(pos, pos, resultado);

        Assert.assertEquals(resultado.isProcessado(), true);
        Assert.assertArrayEquals(resultado.getPonto(), new double[]{pos, pos}, 0.01);
        System.out.printf("Valor do ponto = %d\n", resultado.getValorAzul());
        Assert.assertTrue((resultado.getValorAzul() == resultado.getValorVerde()) &&
                (resultado.getValorAzul() == resultado.getValorVermelho()));
        Assert.assertTrue((resultado.getValorAzul() > 90) && (resultado.getValorAzul() <= 110));


        imagem.setPixel(-1, -1, new int[]{172, 172, 172});
        imagem.setPixel(-1, 0, new int[]{172, 172, 172});
        imagem.setPixel(-1, 1, new int[]{172, 172, 172});
        imagem.setPixel(0, -1, new int[]{169, 169, 169});
        imagem.setPixel(0, 0, new int[]{169, 169, 169});
        imagem.setPixel(0, 1, new int[]{169, 169, 169});
        imagem.setPixel(1, -1, new int[]{166, 166, 166});
        imagem.setPixel(1, 0, new int[]{166, 166, 166});
        imagem.setPixel(1, 1, new int[]{166, 166, 166});

        pos = .39999;
        inferePonto.inferePonto(pos, pos, resultado);
        Assert.assertEquals(resultado.isProcessado(), true);
        Assert.assertArrayEquals(resultado.getPonto(), new double[]{pos, pos}, 0.01);
        System.out.printf("Valor do ponto = %d\n", resultado.getValorAzul());
        Assert.assertTrue((resultado.getValorAzul() == resultado.getValorVerde()) &&
                (resultado.getValorAzul() == resultado.getValorVermelho()));
        Assert.assertTrue((resultado.getValorAzul() >= 168) && (resultado.getValorAzul() <= 170));

        pos = .999;
        inferePonto.inferePonto(pos, pos, resultado);
        Assert.assertEquals(resultado.isProcessado(), true);
        Assert.assertArrayEquals(resultado.getPonto(), new double[]{pos, pos}, 0.01);
        System.out.printf("Valor do ponto = %d\n", resultado.getValorAzul());
        Assert.assertTrue((resultado.getValorAzul() == resultado.getValorVerde()) &&
                (resultado.getValorAzul() == resultado.getValorVermelho()));
        Assert.assertTrue((resultado.getValorAzul() <= 167) && (resultado.getValorAzul() >= 166));

        pos = .0;
        inferePonto.inferePonto(pos, pos, resultado);
        Assert.assertEquals(resultado.isProcessado(), true);
        Assert.assertArrayEquals(resultado.getPonto(), new double[]{pos, pos}, 0.01);
        System.out.printf("Valor do ponto = %d\n", resultado.getValorAzul());
        Assert.assertTrue((resultado.getValorAzul() == resultado.getValorVerde()) &&
                (resultado.getValorAzul() == resultado.getValorVermelho()));
        Assert.assertTrue((resultado.getValorAzul() >=168) && (resultado.getValorAzul() >= 169));

    }

    @Test
    public void infereImagem() {
        Assert.assertTrue(true);

    }

}
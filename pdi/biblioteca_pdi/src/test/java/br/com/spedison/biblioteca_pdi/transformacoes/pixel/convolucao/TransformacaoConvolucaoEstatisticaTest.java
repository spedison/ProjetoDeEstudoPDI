package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao;

import br.com.spedison.biblioteca_pdi.comum.CriaImagensReferencia;
import org.junit.Assert;
import org.junit.Test;

public class TransformacaoConvolucaoEstatisticaTest {

    @Test
    public void testTransformaRGBMediana() {
        var img = CriaImagensReferencia.criaImagemLinear(10, 10);
        img.setPixel(2, 1, new int[]{3, 3, 3}); // Valor Original : 40
        img.setPixel(1, 3, new int[]{0, 0, 0}); // Valor Original : 50
        img.setPixel(1, 5, new int[]{2, 2, 2}); // Valor Original : 70

        TransformacaoConvolucaoEstatistica tm = new TransformacaoConvolucaoEstatistica(3, TipoOperacao.TipoOperacaoMediana); // Kernel de 3x3

        var ret40 = tm.transformaRGB(img.getPixel(2, 1), 2, 1, img);
        Assert.assertArrayEquals(new int[]{40, 40, 40}, ret40);

        var ret50 = tm.transformaRGB(img.getPixel(1, 3), 1, 3, img);
        Assert.assertArrayEquals(new int[]{50, 50, 50}, ret50);

        var ret70 = tm.transformaRGB(img.getPixel(1, 5), 1, 5, img);
        Assert.assertArrayEquals(new int[]{70, 70, 70}, ret70);

    }
    @Test
    public void testTransformaRGBMedia() {
        var img = CriaImagensReferencia.criaImagemLinear(10, 10);
        img.setPixel(2, 1, new int[]{3, 3, 3}); // Valor Original : 40 -> 36
        img.setPixel(1, 3, new int[]{0, 0, 0}); // Valor Original : 50 -> 44
        img.setPixel(1, 5, new int[]{2, 2, 2}); // Valor Original : 70 -> 62

        TransformacaoConvolucaoEstatistica tm = new TransformacaoConvolucaoEstatistica(3, TipoOperacao.TipoOperacaoMedia); // Kernel de 3x3

        var ret40 = tm.transformaRGB(img.getPixel(2, 1), 2, 1, img);
        Assert.assertArrayEquals(new int[]{36, 36, 36}, ret40);

        var ret50 = tm.transformaRGB(img.getPixel(1, 3), 1, 3, img);
        Assert.assertArrayEquals(new int[]{44, 44, 44}, ret50);

        var ret70 = tm.transformaRGB(img.getPixel(1, 5), 1, 5, img);
        Assert.assertArrayEquals(new int[]{62, 62, 62}, ret70);

    }
    @Test
    public void testTransformaRGBMinimo() {
        var img = CriaImagensReferencia.criaImagemLinear(10, 10);
        img.setPixel(2, 1, new int[]{255, 255, 255}); // Valor Original : 40
        img.setPixel(1, 3, new int[]{255, 255, 255}); // Valor Original : 50
        img.setPixel(1, 5, new int[]{255, 255, 255}); // Valor Original : 70

        TransformacaoConvolucaoEstatistica tm = new TransformacaoConvolucaoEstatistica(3, TipoOperacao.TipoOperacaoMin); // Kernel de 3x3

        var ret40 = tm.transformaRGB(img.getPixel(2, 1), 2, 1, img);
        Assert.assertArrayEquals(new int[]{20, 20, 20}, ret40);

        var ret50 = tm.transformaRGB(img.getPixel(1, 3), 1, 3, img);
        Assert.assertArrayEquals(new int[]{30, 30, 30}, ret50);

        var ret70 = tm.transformaRGB(img.getPixel(1, 5), 1, 5, img);
        Assert.assertArrayEquals(new int[]{50, 50, 50}, ret70);

    }
    @Test
    public void testTransformaRGBMaximo() {
        var img = CriaImagensReferencia.criaImagemLinear(10, 10);
        img.setPixel(2, 1, new int[]{0, 0, 0}); // Valor Original : 40
        img.setPixel(1, 3, new int[]{0, 0, 0}); // Valor Original : 50
        img.setPixel(1, 5, new int[]{0, 0, 0}); // Valor Original : 70

        TransformacaoConvolucaoEstatistica tm = new TransformacaoConvolucaoEstatistica(3, TipoOperacao.TipoOperacaoMax); // Kernel de 3x3

        var ret40 = tm.transformaRGB(img.getPixel(2, 1), 2, 1, img);
        Assert.assertArrayEquals(new int[]{60, 60, 60}, ret40);

        var ret50 = tm.transformaRGB(img.getPixel(1, 3), 1, 3, img);
        Assert.assertArrayEquals(new int[]{70, 70, 70}, ret50);

        var ret70 = tm.transformaRGB(img.getPixel(1, 5), 1, 5, img);
        Assert.assertArrayEquals(new int[]{90, 90, 90}, ret70);

    }
    @Test
    public void testTransformaRGBModa() {
        var img = CriaImagensReferencia.criaImagemLinear(10, 10);
        img.setPixel(2, 1, new int[]{30, 30, 30}); // Valor Original : 40
        img.setPixel(1, 3, new int[]{40, 40, 40}); // Valor Original : 50
        img.setPixel(1, 5, new int[]{80, 80, 80}); // Valor Original : 70

        TransformacaoConvolucaoEstatistica tm = new TransformacaoConvolucaoEstatistica(3, TipoOperacao.TipoOperacaoModa); // Kernel de 3x3

        var ret30 = tm.transformaRGB(img.getPixel(2, 1), 2, 1, img);
        Assert.assertArrayEquals(new int[]{30, 30, 30}, ret30);

        var ret50 = tm.transformaRGB(img.getPixel(1, 3), 1, 3, img);
        Assert.assertArrayEquals(new int[]{40, 40, 40}, ret50);

        var ret70 = tm.transformaRGB(img.getPixel(1, 5), 1, 5, img);
        Assert.assertArrayEquals(new int[]{80, 80, 80}, ret70);

    }
}
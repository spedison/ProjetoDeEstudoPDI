package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;

import br.com.spedison.biblioteca_pdi.auxiliar.Arredondador;
import br.com.spedison.biblioteca_pdi.base.HistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import org.junit.Assert;
import org.junit.Test;

public class FuncaoHistogramaProporcionalLimitesTest {

    @Test
    public void testTransforma() {

        Imagem img = new Imagem(4, 4);

        img.setOrigemX(1);
        img.setOrigemY(1);

        img.getStreamAltura().forEach(posY -> {
            img.getStreamLargura().forEach(posX -> {
                img.setPixel(posX, posY, new int[]{posX * posY, posX * posY, posX * posY});
            });
        });
        HistogramaImagem hi = new HistogramaImagem();
        hi.processaHistograma(img);
        FuncaoHistogramaProporcionalLimites ft = new FuncaoHistogramaProporcionalLimites(true, 1. / 256.);
        ft.setHistogramaImagem(hi);

        ft.inicializa(img);

        // Teste das Bordas
        var x = ft.transforma(0, 0, 1);
        var comp = 0;
        Assert.assertEquals(comp, x);

        x = ft.transforma(0, 0, 16);
        comp = 255;
        Assert.assertEquals(comp, x);

        // Fora do intervalo
        x = ft.transforma(0, 0, 18);
        comp = 255;
        Assert.assertEquals(comp, x);

        x = ft.transforma(0, 0, 20);
        comp = 255;
        Assert.assertEquals(comp, x);

        x = ft.transforma(0, 0, 255);
        comp = 255;
        Assert.assertEquals(comp, x);

        x = ft.transforma(0, 0, 0);
        comp = 0;
        Assert.assertEquals(comp, x);

        // Pontos dentro do intervalo
        x = ft.transforma(0, 0, 2);
        comp = Arredondador.arredonda(255. / 15.) ;
        Assert.assertEquals(comp, x);

        x = ft.transforma(0, 0, 3);
        comp = Arredondador.arredonda (2. * (255. / 15.) );
        Assert.assertEquals(comp, x);

        x = ft.transforma(0, 0, 5);
        comp = 4 * 17;
        Assert.assertEquals(comp, x);
    }
}
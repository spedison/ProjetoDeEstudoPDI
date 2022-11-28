package br.com.spedison.biblioteca_pdi_testes.padroes;


import br.com.spedison.usogeral.auxiliar.MD5;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemGeradorPadrao;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ImagemGeradorPadraoTest {

    private static String arquivoTeste = SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao__.bmp");

    @After
    public void tearDown() throws Exception {
        boolean apagado = (new File(arquivoTeste)).delete();
        System.out.println("apagado = " + apagado);
    }

    @Test
    public void testProcessa() {
        ImagemGeradorPadrao img = new ImagemGeradorPadrao(150, 150);
        img.setOrigemX(-50);
        img.setOrigemY(-50);
        img.processa((p, rgb) -> {
            rgb[Imagem.Vermelho] = img.limitaValores((int) Math.pow(p.x * p.y, 2) + p.x * p.x * 4 + (int) Math.sqrt((p.y + 10) * 10.0) - 60);
            rgb[Imagem.Verde] = img.limitaValores((int) Math.pow(p.x * p.y - 10, 3) + p.x * p.x * 4 + (int) Math.sqrt((p.y + 80) * 20.0) - 80);
            rgb[Imagem.Azul] = img.limitaValores(p.x * 50 + (int) Math.log((p.y * p.y + 6) * 30.0) - 200);
            return rgb;
        });
        img.salva(arquivoTeste);
        Assert.assertEquals("6f63fe58a3ae71d23827bf4eced6a75f".toUpperCase(), MD5.calculaMD5Arquivo(arquivoTeste));
    }
}
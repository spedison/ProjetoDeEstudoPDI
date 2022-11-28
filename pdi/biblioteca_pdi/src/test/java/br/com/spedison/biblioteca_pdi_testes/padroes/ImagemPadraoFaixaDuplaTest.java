package br.com.spedison.biblioteca_pdi_testes.padroes;

import br.com.spedison.biblioteca_pdi.padroes.ImagemPadraoFaixaDupla;
import br.com.spedison.usogeral.auxiliar.MD5;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ImagemPadraoFaixaDuplaTest {

    final static String arquivoFaixaVertical = SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_faixa_vertical.bmp");
    final static String arquivoFaixaHorizontal = SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_faixa_horizontal.bmp");
    final static String arquivoFaixaXadrez = SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_faixa_xadrez.bmp");


    @After
    public void limparDireorio(){
        (new File(arquivoFaixaHorizontal)).delete();
        (new File(arquivoFaixaVertical)).delete();
        (new File(arquivoFaixaXadrez)).delete();
    }

    @Test
    public void testGeraPadrao() {

        ImagemPadraoFaixaDupla padrao = new ImagemPadraoFaixaDupla(250, 250);
        padrao.setLarguraFaixa1(20);
        padrao.setLarguraFaixa2(20);
        padrao.setCorFaixa1(new int[]{255, 0, 0});
        padrao.setCorFaixa2(new int[]{255, 255, 255});

        padrao.setTipoFaixa(ImagemPadraoFaixaDupla.TipoFaixa.Vertical);
        padrao.geraPadrao();
        padrao.salva(arquivoFaixaVertical);
        Assert.assertEquals("Arquivo Faixa Vertical com Problemas.",
                MD5.calculaMD5Arquivo(arquivoFaixaVertical),
                "3ac2620e5fc6c12e560f882c8f2454ed".toUpperCase());

        padrao.setTipoFaixa(ImagemPadraoFaixaDupla.TipoFaixa.Horizontal);
        padrao.geraPadrao();
        padrao.salva(arquivoFaixaHorizontal);
        Assert.assertEquals("Arquivo Faixa Horizontal com Problemas.",
                MD5.calculaMD5Arquivo(arquivoFaixaHorizontal),
                "8db04d25717bd0d28b3562e45bc0f8df".toUpperCase());

        padrao.setTipoFaixa(ImagemPadraoFaixaDupla.TipoFaixa.Xadrez);
        padrao.geraPadrao();
        padrao.salva(arquivoFaixaXadrez);
        Assert.assertEquals("Arquivo Faixa Xadrez com Problemas.",
                MD5.calculaMD5Arquivo(arquivoFaixaXadrez),
                "f8dc9d884d98eed0b5a475187888da90".toUpperCase());
    }
}
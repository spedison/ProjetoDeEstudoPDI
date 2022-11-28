package br.com.spedison.biblioteca_pdi_testes.base;

import br.com.spedison.biblioteca_pdi.base.ImagemTamanhoAjustado;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.usogeral.auxiliar.Arredondador;
import br.com.spedison.usogeral.auxiliar.MD5;
import br.com.spedison.usogeral.auxiliar.Downloader;
import br.com.spedison.biblioteca_pdi.base.enuns.Interpolacao;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ImagemTamanhoAjustadoTest {

    static final String arquivoParaTrabalhar = SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("cafe_teste_1.jpg");
    static final String arquivoReduzido = SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("cafe_teste_1_reduzido.jpg");
    static final String urlToDownload = "https://github.com/spedison/ImagensDeTeste/raw/main/cafe1.jpg";

    @Test
    public void testSetPercentualX() {
        ImagemTamanhoAjustado ita = new ImagemTamanhoAjustado();
        ita.setPercentualX(0.666);
        assertEquals(0.666, ita.getPercentualX(), 0.0001);
        ita.setPercentualX(-1.686);
        assertEquals(1.686, ita.getPercentualX(), 0.0001);
    }

    @Test
    public void testSetPercentualY() {
        ImagemTamanhoAjustado ita = new ImagemTamanhoAjustado();
        ita.setPercentualY(0.666);
        assertEquals(0.666, ita.getPercentualY(), 0.0001);
        ita.setPercentualY(-3.667);
        assertEquals(3.667, ita.getPercentualY(), 0.0001);
    }

    @Test
    public void testSetInterpolacao() {
        ImagemTamanhoAjustado ita = new ImagemTamanhoAjustado();
        ita.setInterpolacao(Interpolacao.Cubica);
        assertEquals(Interpolacao.Cubica, ita.getInterpolacao());
        ita.setInterpolacao(Interpolacao.Linear);
        assertEquals(Interpolacao.Linear, ita.getInterpolacao());
    }


    @Test
    public void testAjustaTamanho() {
        realizarDownloadArquivos();
        ImagemTamanhoAjustado ita = new ImagemTamanhoAjustado(arquivoParaTrabalhar, 0.2, 0.2);
        ita.salva(arquivoReduzido);

        var md5ArquivoParaTrabalhar = MD5.calculaMD5Arquivo(arquivoParaTrabalhar);
        var md5ArquivoReduzido = MD5.calculaMD5Arquivo(arquivoReduzido);

        assertEquals("F5329C1A82066234854C95EE0B039C79", md5ArquivoParaTrabalhar);
        assertEquals("5A89D140E6747F3F36FCF4C3DEF56F8B", md5ArquivoReduzido);
        apagarArquivos();
    }

    private void realizarDownloadArquivos() {
        System.out.println("Realizando download arquivo de teste.");
        boolean baixou = Downloader.downloadFile(urlToDownload, arquivoParaTrabalhar);
        assertTrue("Não conseguiu baixar o arquivo do repositorio : " + urlToDownload, baixou);
    }

    private void apagarArquivos() {
        System.out.println("Limpando diretório");
        (new File(arquivoParaTrabalhar)).delete();
        (new File(arquivoReduzido)).delete();
    }
}
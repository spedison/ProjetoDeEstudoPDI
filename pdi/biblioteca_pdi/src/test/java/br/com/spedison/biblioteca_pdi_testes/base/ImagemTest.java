package br.com.spedison.biblioteca_pdi_testes.base;


import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.usogeral.auxiliar.Downloader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

// TODO: Completar o teste.
public class ImagemTest {

    String arquivoTrabalho = "cafeteira_vermelha.png";


    static final String arquivoParaTrabalhar = SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("cafeteira.jpg");
    static final String arquivoReduzido = SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("cafefeira_processada.jpg");
    static final String urlToDownload = "https://github.com/spedison/ImagensDeTeste/raw/main/cafeteira_vermelha.png";

    @Before
    public void setUp() {
        Downloader.downloadFile(urlToDownload, arquivoParaTrabalhar);
    }

    @After
    public void tearDown() {
        (new File(arquivoParaTrabalhar)).delete();
    }

    @Test
    public void testLimitaValores() {
    }

    @Test
    public void testTestLimitaValores() {
    }

    @Test
    public void testSetValorMaximo() {
    }

    @Test
    public void testSetNomeArquivo() {
    }

    @Test
    public void testSetOrigemX() {
    }

    @Test
    public void testSetOrigemY() {
    }

    @Test
    public void testCopiaImagem() {
    }

    @Test
    public void testPintaFundo() {
    }

    @Test
    public void testNovaImagem() {
    }

    @Test
    public void testTestNovaImagem() {
    }

    @Test
    public void testGetTamanho() {
    }

    @Test
    public void testGetLargura() {
    }

    @Test
    public void testGetAltura() {
    }

    @Test
    public void testGetTipo() {
    }

    @Test
    public void testLeArquivo() {
    }

    @Test
    public void testSetColor() {
    }

    @Test
    public void testSetFonte() {
    }

    @Test
    public void testEscreveTexto() {
    }

    @Test
    public void testGetPixel() {
    }

    @Test
    public void testSetRGB() {
    }

    @Test
    public void testSalva() {
    }

    @Test
    public void testTestCopiaImagem() {
    }

    @Test
    public void testTestCopiaImagem1() {
    }

    @Test
    public void testCopiaImagemDe() {
    }

    @Test
    public void testCopiaImagemPara() {
    }

    @Test
    public void testIsBorda() {
    }

    @Test
    public void testRestauraOrigem() {
    }

    @Test
    public void testIsPontoInvalido() {
    }

    @Test
    public void testIsPontoValidoInferencia() {
    }

    @Test
    public void testIsPontoInvalidoInferencia() {
    }
}
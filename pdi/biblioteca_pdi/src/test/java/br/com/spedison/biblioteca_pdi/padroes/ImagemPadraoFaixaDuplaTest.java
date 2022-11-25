package br.com.spedison.biblioteca_pdi.padroes;

import br.com.spedison.biblioteca_pdi.auxiliar.ByteData;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ImagemPadraoFaixaDuplaTest {

    @Test
    public void testGeraPadrao() {

        ImagemPadraoFaixaDupla padrao = new ImagemPadraoFaixaDupla(250, 250);
        padrao.setLarguraFaixa1(20);
        padrao.setLarguraFaixa2(20);
        padrao.setCorFaixa1(new int[]{255, 0, 0});
        padrao.setCorFaixa2(new int[]{255, 255, 255});

        padrao.setTipoFaixa(ImagemPadraoFaixaDupla.TipoFaixa.Vertical);
        padrao.geraPadrao();
        padrao.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_faixa_vertical.bmp"));
        testaImagem(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_faixa_vertical.bmp"), "3ac2620e5fc6c12e560f882c8f2454ed");

        padrao.setTipoFaixa(ImagemPadraoFaixaDupla.TipoFaixa.Horizontal);
        padrao.geraPadrao();
        padrao.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_faixa_horizontal.bmp"));
        testaImagem(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_faixa_horizontal.bmp"), "8db04d25717bd0d28b3562e45bc0f8df");

        padrao.setTipoFaixa(ImagemPadraoFaixaDupla.TipoFaixa.Xadrez);
        padrao.geraPadrao();
        padrao.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_faixa_xadrez.bmp"));
        testaImagem(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_faixa_xadrez.bmp"), "02f1a2c2580398e5c3a163dd03551bf7");
    }

    private void testaImagem(String caminhoArquivoCompleto, String hash) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println("Não posso continuar, pois não encontrei o algorítimo para utilizar.");
            Assert.assertTrue("Problema na carga do Algoritmo de Hash.", false);
            return;
        }
        try {
            Path path = Paths.get(caminhoArquivoCompleto);
            byte[] data = Files.readAllBytes(path);
            md.update(data);
            byte[] digest = md.digest();
            String digestStr = ByteData.convertToHexString(digest);

            Assert
                    .assertTrue(
                            "Hash do arquivo %s não bateu. Esperava %s e foi calculado como %s"
                                    .formatted(caminhoArquivoCompleto, hash.toUpperCase(), digestStr),
                            digestStr.equals(hash.toUpperCase()));

            path.toFile().delete();
        } catch (IOException ioe) {
            Assert.assertTrue(false);
        }
    }
}
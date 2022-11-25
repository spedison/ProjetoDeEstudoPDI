package br.com.spedison.dip;

import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ConstantesProjetoTest {

    public void setup(SistemaArquivoImagens c) {
        c.criaDirEntrada();
        c.criaDirSaida();
        System.out.println("Diretórios Criados.");
    }

    @Test
    public void getDirImagensEntrada() {
        var c= SistemaArquivoImagens.getInstance();
        setup(c);
        Path p = Paths.get(c.getDirBaseImagensEntrada());
        System.out.println("HOME : " + p.toString());

        Assert.assertTrue(p.toFile().exists());
        Assert.assertTrue(p.toFile().isDirectory());

        p = Paths.get(c.getDirBaseImagensSaida());
        Assert.assertTrue(p.toFile().exists());
        Assert.assertTrue(p.toFile().isDirectory());
    }

    @Test
    public void contaImagensEntrada(){
        var c = SistemaArquivoImagens.getInstance();
        File[] arrArquivos = c.getListaArquivosImagensEntrada(SistemaArquivoImagens.extensaoImagens);

        Assert.assertTrue(arrArquivos.length > 0);
        System.out.println(Arrays.toString(arrArquivos));
    }

}
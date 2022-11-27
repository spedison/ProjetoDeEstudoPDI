package br.com.spedison.biblioteca_pdi.transformacoes.espacial;

import br.com.spedison.biblioteca_pdi.auxiliar.MD5;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.comum.CriaImagensReferencia;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoEscala;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoIdentidade;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoRotacao;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoTranslacao;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoEspacial;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.io.File;

public class TransformadorEspacialImagemTest {

    static SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
    static String nomeArquivoImagemRerencia = sai.criaDirSaida("testeUnitario").getDirBaseImagensSaida("referencia.jpg");

    private void init() {
        Imagem img = new Imagem(500, 500);
        img.pintaFundo(Color.RED.getRGB());
        Font f = new Font("Arial", Font.BOLD, 150);
        img.
                setFonte(f).
                setColor(Color.cyan).
                escreveTexto("Teste de Imagem com Texto", 0, 200).
                salva(nomeArquivoImagemRerencia);
    }

    @Test
    public void testaTransformacaoEspacialMatrizIdentidade() {
        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        var imagemRerencia = CriaImagensReferencia.cria10por10LinhasPB();
        Imagem r = new Imagem(imagemRerencia);
        TransformadorEspacialImagem c = new TransformadorEspacialImagem(r);
        MatrizTransformacaoIdentidade i = new MatrizTransformacaoIdentidade();
        c.aplicaTransformacao(i, 1.0, 1.0, 0, 0, 0, 0);
        var ceq = new Imagem(c);
        Assert.assertTrue(ceq.equals(r));
    }

    //@Test
    public void testaEscala() {
        init();
        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        var imagemCalculado = sai.getDirBaseImagensSaida("calculado_escala.jpg");

        Imagem r = new Imagem(nomeArquivoImagemRerencia);
        TransformadorEspacialImagem c = new TransformadorEspacialImagem(r);
        MatrizTransformacaoEspacial i = new MatrizTransformacaoEscala(2.0, 1.0);
        c.aplicaTransformacao(i, 3.0, 1.0, 0, 0, 0, 0);
        c.salva(imagemCalculado);
        Assert.assertEquals(MD5.calculaMD5Arquivo(imagemCalculado),"415F45F61D49CB98D5978D15101394F5");
        new File(imagemCalculado).delete();
        // var ceq = new Imagem(c);
        // Assert.assertTrue(ceq.equals(r));
    }

    @Test
    public void testaRotacaoUmPonto() {
        MatrizTransformacaoEspacial rotacao = new MatrizTransformacaoRotacao(45);
        rotacao.calculaInverso();
        var ret = rotacao.calculaPontoInverso(Math.sqrt(2.0) / 2.0, Math.sqrt(2.0) / 2.0);
        Assert.assertEquals(ret[0], 1 , 1E-4);
        Assert.assertEquals(ret[1], 0 , 1E-4);
    }

    //@Test
    public void testaRotacaoUmaImagem() {
        init();
        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        var imagemRerencia = sai.getDirBaseImagensSaida("referencia.jpg");
        var imagemCalculado = sai.getDirBaseImagensSaida("calculado_rotacao_1imagem%d.jpg");
        Imagem r = new Imagem(imagemRerencia);
        for ( int angulo = -70 ; angulo < 70 ; angulo += 1) {
            // var angulo = 30;
            TransformadorEspacialImagem c = new TransformadorEspacialImagem(r);
            MatrizTransformacaoEspacial i = new MatrizTransformacaoRotacao(angulo);
            //i.setSomaX(c.getLargura());//r.getLargura());//r.getLargura());
            // i.setSomaY(-c.getAltura());//-r.getAltura());
            int multiplicador = 1;
            c.aplicaTransformacao(i, multiplicador * 2, multiplicador * 1.5,
                    -r.getLargura() / 2 + 180, -r.getAltura() / 2,
                    -r.getLargura() / 2 - 180, -r.getAltura() / 2);
            c.salva(imagemCalculado.formatted(angulo));
        }

    }


    //@Test
    public void testaRotacao() {
        init();
        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        var imagemRerencia = sai.getDirBaseImagensSaida("referencia.jpg");
        var imagemCalculado = sai.getDirBaseImagensSaida("calculado_rotacao_%d.jpg");

        Imagem r = new Imagem(imagemRerencia);
        for (int angulo = -50; angulo < 50; angulo += 5) {
            TransformadorEspacialImagem c = new TransformadorEspacialImagem(r);
            MatrizTransformacaoEspacial i = new MatrizTransformacaoRotacao(angulo);
            c.aplicaTransformacao(i, 2.0, 2.0, 0, 0, 0, 0);
            c.salva(imagemCalculado.formatted(angulo));
        }
        // var ceq = new Imagem(c);
        // Assert.assertTrue(ceq.equals(r));
    }

    //@Test
    public void testaTranslacao() {
        init();
        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        var imagemRerencia = CriaImagensReferencia.cria10por10LinhasPB(); //sai.getDirBaseImagensSaida("referencia.jpg");
        var imagemCalculado = sai.getDirBaseImagensSaida("calculado_translacao_%d.jpg");

        Imagem r = new Imagem(imagemRerencia);
        for (int passo = -150; passo < 150; passo += 15) {
            TransformadorEspacialImagem c = new TransformadorEspacialImagem(r);
            MatrizTransformacaoEspacial i = new MatrizTransformacaoTranslacao(0, passo);
            c.aplicaTransformacao(i, 1.0, 1.0, 0, 0, 0, 0);
            c.salva(imagemCalculado.formatted(passo));
        }
        // var ceq = new Imagem(c);
        // Assert.assertTrue(ceq.equals(r));
    }
}

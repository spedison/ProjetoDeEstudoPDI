package br.com.spedison.main.BJ_Ruido;

import br.com.spedison.biblioteca_pdi.base.*;
import br.com.spedison.biblioteca_pdi.base.operacoes_entre_imagens.OperacaoEntreImagens;
import br.com.spedison.biblioteca_pdi.base.ruido.GeradorRuidoGaussinano;
import br.com.spedison.biblioteca_pdi.base.ruido.ImagemRuido;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TipoOperacao;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoEstatistica;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformadorConvolucao;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class MainMostraImagemRuido {

    public static void main(String[] args) {

        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        Imagem imgOriginal = new Imagem(sai.getDirBaseImagensEntrada("cafe1.jpg"));

        GeradorRuidoGaussinano grg = new GeradorRuidoGaussinano();
        grg.setMaximo(70);
        grg.setMinimo(0);

        ImagemRuido imgRuido = new ImagemRuido(imgOriginal.getLargura(), imgOriginal.getAltura(), grg);
        imgRuido.geraImagem();

        OperadorEntreImagens op = new OperadorEntreImagens(imgOriginal);
        op.operacaoNaImagem(imgRuido, OperacaoEntreImagens.Soma.getOperacao());

        new ExibidorDeImagem("Original", new ImagemAjustadaTela(imgOriginal, 0.9), true);
        new ExibidorDeImagem("Original+Ruido", new ImagemAjustadaTela(op, 0.9), true);


        TransformacaoConvolucaoEstatistica tcm = new TransformacaoConvolucaoEstatistica(3, TipoOperacao.TipoOperacaoMediana);
        TransformadorConvolucao tc = new TransformadorConvolucao(op);
        tc.transforma(tcm);
        new ExibidorDeImagem("Transformada(Original+Ruido)", new ImagemAjustadaTela(tc, 0.9), true);

        tc.transforma(tcm);
        new ExibidorDeImagem("Transformada(Transformada(Original+Ruido))", new ImagemAjustadaTela(tc, 0.9), true);

        List<Imagem> imagens = new LinkedList<Imagem>();
        Font f = new Font("Arial", Font.BOLD, 100);
        imagens.add(imgOriginal.setColor(Color.RED).setFonte(f).escreveTexto("Original", 10, 150));
        imagens.add(op.setColor(Color.RED).setFonte(f).escreveTexto("Original + Ruido", 10, 150));
        imagens.add(tc.setColor(Color.RED).setFonte(f).escreveTexto("Tratada com Mediana", 10, 150));

        ArranjoEntreImagensMatriz aeiMatriz = new ArranjoEntreImagensMatriz(imagens);
        aeiMatriz
                .setColunas(3)
                .setLinhas(1)
                .setEspacoX(15)
                .setEspacoY(0)
                .processaImagens();
        aeiMatriz.salva(sai.getDirBaseImagensSaida("Resultado-Processamento-Mediana.jpg"));
        new ExibidorDeImagem("Unidos", new ImagemAjustadaTela(aeiMatriz, 0.9), true);

    }

}

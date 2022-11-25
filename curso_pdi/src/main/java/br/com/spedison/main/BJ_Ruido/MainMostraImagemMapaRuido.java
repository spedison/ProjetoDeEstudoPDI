package br.com.spedison.main.BJ_Ruido;

import br.com.spedison.biblioteca_pdi.base.*;
import br.com.spedison.biblioteca_pdi.base.ruido.GeradorRuidoGaussinano;
import br.com.spedison.biblioteca_pdi.base.ruido.ImagemRuido;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TipoOperacao;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoEstatistica;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformadorConvolucao;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class MainMostraImagemMapaRuido {

    public static void main(String[] args) {

        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        Imagem imgOriginal = new Imagem(sai.getDirBaseImagensEntrada("mapa_ruido.jpg"));

        new ExibidorDeImagem("Imagem Ruidosa", new ImagemAjustadaTela(imgOriginal,0.9), true);

        TransformacaoConvolucaoEstatistica tcmediana = new TransformacaoConvolucaoEstatistica(3, TipoOperacao.TipoOperacaoMediana);
        TransformacaoConvolucaoEstatistica tcmedia = new TransformacaoConvolucaoEstatistica(3, TipoOperacao.TipoOperacaoMedia);
        TransformadorConvolucao transformadorMediana = new TransformadorConvolucao(imgOriginal);
        transformadorMediana.transforma(tcmediana);

        TransformadorConvolucao transformadorMedia = new TransformadorConvolucao(imgOriginal);
        transformadorMediana.transforma(tcmedia);
        new ExibidorDeImagem("Mediana(Imagem Ruidosa)", new ImagemAjustadaTela(transformadorMediana, 0.9), true);
        new ExibidorDeImagem("Media(Imagem Ruidosa)", new ImagemAjustadaTela(transformadorMedia, 0.9), true);

        List<Imagem> imagens = new LinkedList<Imagem>();
        Font f = new Font("Arial",Font.BOLD, 50);
        imagens.add(imgOriginal.setColor(Color.RED).setFonte(f).escreveTexto("Original",10,100));
        imagens.add(transformadorMedia.setColor(Color.RED).setFonte(f).escreveTexto("Tratada com Média",10,100));
        imagens.add(transformadorMediana.setColor(Color.RED).setFonte(f).escreveTexto("Tratada com Mediana",10,100));

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

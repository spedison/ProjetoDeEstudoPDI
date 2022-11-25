package br.com.spedison.main.BI_Transformacao_Convolucao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.*;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;

public class Main_Aula10_Borrar {

    public static void main(String[] args) {

        // Carga de constantes do Projeto.
        var c = SistemaArquivoImagens.getInstance();

        // Kernels usados.
        TransformacaoConvolucaoInterface kernel5 = new TransformacaoConvolucaoEstatistica(TipoOperacao.TipoOperacaoMedia, 5, 0, 1.6);
        TransformacaoConvolucaoInterface kernel7 = new TransformacaoConvolucaoEstatistica(TipoOperacao.TipoOperacaoMedia, 7, 0, 2.7);
        TransformacaoConvolucaoInterface kernel11 = new TransformacaoConvolucaoEstatistica(TipoOperacao.TipoOperacaoMedia, 11, 0, 3.5);

        // Carga das Imagens.
        Imagem imgOriginal = new Imagem(c.getDirBaseImagensEntrada("cafeteira_vermelha.png"));
        TransformadorConvolucao imgC5 = new TransformadorConvolucao(imgOriginal);
        TransformadorConvolucao imgC7 = new TransformadorConvolucao(imgC5);
        TransformadorConvolucao imgC11 = new TransformadorConvolucao(imgC5);

        // Definição e execução da Covolução
        imgC5.transforma(kernel5);
        imgC7.transforma(kernel7);
        imgC11.transforma(kernel11);

        //Mostra a Imagenm
        new ExibidorDeImagem("Café Original", new ImagemAjustadaTela(imgOriginal, 0.6), true);
        new ExibidorDeImagem("Café Borrado 5", new ImagemAjustadaTela(imgC5, 0.6), true);
        new ExibidorDeImagem("Café Borrado 7", new ImagemAjustadaTela(imgC7, 0.6), true);
        new ExibidorDeImagem("Café Borrado 11", new ImagemAjustadaTela(imgC11, 0.6), true);
    }

}

package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;

public class Main_Aula20_Crop_CopiaPara {

    public static void main(String[] args) {

        SistemaArquivoImagens cp = SistemaArquivoImagens.getInstance();

        // Fonte dos dados
        Imagem imgOrigem = new Imagem(cp.getDirBaseImagensEntrada("cafe2.jpg"));
        // Copia para aqui !!
        Imagem imgDestino = new Imagem(1000, 1000);

        imgOrigem.copiaImagemPara(imgDestino, 1000, 1000);
        new ExibidorDeImagem("Imagem Original", new ImagemAjustadaTela(imgOrigem,0.6), true);
        new ExibidorDeImagem("Imagem Croped", new ImagemAjustadaTela(imgDestino,0.6), true);

        Imagem teste = new Imagem(imgOrigem.getTamanho());
        teste.copiaImagemDe(imgOrigem,0,0);
        teste.copiaImagemDe(imgDestino,500,100);
        teste.copiaImagemDe(imgDestino,900,100);
        teste.copiaImagemDe(imgDestino,1500,100);
        teste.copiaImagemDe(imgDestino,500,600);
        teste.copiaImagemDe(imgDestino,900,600);
        teste.copiaImagemDe(imgDestino,1500,600);
        new ExibidorDeImagem("Imagem Limpa", new ImagemAjustadaTela(teste,0.7), true);

    }

}

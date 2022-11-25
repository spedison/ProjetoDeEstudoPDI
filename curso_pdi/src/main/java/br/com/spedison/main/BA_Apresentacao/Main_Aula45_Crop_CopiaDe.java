package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;

public class Main_Aula45_Crop_CopiaDe {

    public static void main(String[] args) {

        SistemaArquivoImagens cp = SistemaArquivoImagens.getInstance();

        // Fonte dos dados
        Imagem imgOrigem = new Imagem(cp.getDirBaseImagensEntrada("cafe2.jpg"));
        // Copia para aqui !!
        Imagem imgDestino = new Imagem(1000, 1000);

        imgOrigem.copiaImagemPara(imgDestino, 1000, 1000);
        new ExibidorDeImagem("Imagem Original", new ImagemAjustadaTela(imgOrigem, 0.7), true);
        new ExibidorDeImagem("Imagem Croped", imgDestino, true);

    }

}

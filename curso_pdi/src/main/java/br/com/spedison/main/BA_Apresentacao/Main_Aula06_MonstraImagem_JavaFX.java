package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi_javafx.gui.ExibidorDeImagens;

public class Main_Aula06_MonstraImagem_JavaFX extends ExibidorDeImagens {

    public static void main(String[] args) {
        var img = SistemaArquivoImagens.getInstance().getDirBaseImagensEntrada("cafe1.jpg");
        Imagem imagem = new Imagem(img);
        /*ExibidorDeImagens.adicionaImagem("Café usando Java FX", imagem);
        mostraJanelas();*/
    }

}

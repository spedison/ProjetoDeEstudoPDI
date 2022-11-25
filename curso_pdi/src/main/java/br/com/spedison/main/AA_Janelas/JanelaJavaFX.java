package br.com.spedison.main.AA_Janelas;
// Tem que exportar esse pacote para que Módulo JavaFX Gráfcs Acesse a classe.

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi_javafx.gui.ExibidorDeImagens;


public class JanelaJavaFX extends ExibidorDeImagens {

    public static void main(String[] args) {
        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        var img = new Imagem(sai.getDirBaseImagensEntrada("cafe1.jpg"));
        ExibidorDeImagens.adicionaImagem("Café 1", img);
        ExibidorDeImagens.adicionaImagem("Café 2", img);
        ExibidorDeImagens.adicionaImagem("Café 3", img);
        mostraJanelas();
    }
}

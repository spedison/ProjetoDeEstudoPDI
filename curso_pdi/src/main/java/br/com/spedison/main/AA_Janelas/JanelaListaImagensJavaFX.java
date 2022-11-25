package br.com.spedison.main.AA_Janelas;
// Tem que exportar esse pacote para que Módulo JavaFX Gráfcs Acesse a classe.

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi_javafx.gui.ExibidorListaDeImagens;

public class JanelaListaImagensJavaFX extends ExibidorListaDeImagens {

    public static void main(String[] args) {
        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        var img1 = new Imagem(sai.getDirBaseImagensEntrada("cafe1.jpg"));
        var img2 = new Imagem(sai.getDirBaseImagensEntrada("cafe2.jpg"));
        var img3 = new Imagem(sai.getDirBaseImagensEntrada("disjuntores1.jpg"));
        var img4 = new Imagem(sai.getDirBaseImagensEntrada("disjuntores2.jpg"));
        ExibidorListaDeImagens.adicionaImagem("Café 1", img1);
        ExibidorListaDeImagens.adicionaImagem("Café 2", img2);
        ExibidorListaDeImagens.adicionaImagem("Disjuntores 1", img3);
        ExibidorListaDeImagens.adicionaImagem("Disjuntores 2", img4);
        ExibidorListaDeImagens.mostraJanela();
    }
}

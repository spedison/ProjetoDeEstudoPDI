package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.ImagemLeUmCanal;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;

public class Main_Aula07_MostraImagens1Canal {
    public static void main(String[] args) {

        var arqImagem = "disjuntores2.jpg";
        var nome = "Disjuntores";
        SistemaArquivoImagens cp = SistemaArquivoImagens.getInstance();
        Imagem img = new ImagemAjustadaTela(cp.getDirBaseImagensEntrada(arqImagem), 0.75);

        ImagemLeUmCanal imgVermelho = new ImagemLeUmCanal(img, Imagem.Vermelho);
        ImagemLeUmCanal imgVerde = new ImagemLeUmCanal(img, Imagem.Verde);
        ImagemLeUmCanal imgAzul = new ImagemLeUmCanal(img, Imagem.Azul);

        new ExibidorDeImagem("Imagem %s Canal vermelho".formatted(nome), imgVermelho, true);
        new ExibidorDeImagem("Imagem %s Canal verde".formatted(nome), imgVerde, true);
        new ExibidorDeImagem("Imagem %s Canal Azul".formatted(nome), imgAzul, true);
        new ExibidorDeImagem("Imagem %s 3 Canais".formatted(nome), img, true);

    }
}

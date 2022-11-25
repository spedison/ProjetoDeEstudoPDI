package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;

public class Main_Aula04_Representacao_Cont {
    public static void main(String[] args) {
        SistemaArquivoImagens cp = SistemaArquivoImagens.getInstance();

        var a = new Imagem(120, 50);
        a.escreveTexto("Texto de Teste", 15, 15);
        a.salva(cp.getDirBaseImagensSaida("Aula1-Sem-Cafe.jpg"));

        var b = new ImagemAjustadaTela(cp.getDirBaseImagensEntrada("cafe1.jpg"),.7);
        new ExibidorDeImagem("Imagem Com Café", b, true);
    }
}

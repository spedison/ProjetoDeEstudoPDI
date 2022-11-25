package br.com.spedison.main.AA_Janelas;

import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;

public class JanelaJavaSwing {
    public static void main(String[] args) {
        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        var img = new ImagemAjustadaTela( sai.getDirBaseImagensEntrada("cafe1.jpg"),0.8);
        new ExibidorDeImagem("Imagem Café 1", img, true);
        new ExibidorDeImagem("Imagem Café 2", img, true);
        new ExibidorDeImagem("Imagem Café 3", img, true);
    }
}

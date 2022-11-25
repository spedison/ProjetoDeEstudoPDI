package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.base.enuns.Interpolacao;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;

import java.awt.*;
import java.io.File;

public class Main_Aula06_MostraImagemAjustada {
    public static void main(String[] args) {  //main
        Imagem imgRx = new Imagem();
        String base = SistemaArquivoImagens.getInstance().getDirBaseImagensEntrada()+ File.separatorChar;
        imgRx.leArquivo(base + "rx-torax-1.jpg");
        Imagem imgCafe1 = new ImagemAjustadaTela(base + "cafe1.jpg", Interpolacao.Linear); // Linear
        Imagem imgCafe2 = new ImagemAjustadaTela(base + "cafe1.jpg",Interpolacao.Cubica); // Bicubica
        Imagem imgCafe3 = new ImagemAjustadaTela(base + "cafe1.jpg",Interpolacao.IterpolacaoVizinhosProximos); // Sem Interpolacao
        new ExibidorDeImagem("Torax 1", imgRx, true);
        new ExibidorDeImagem("Torax 2", imgRx, true);
        new ExibidorDeImagem("Café Linear", imgCafe1, true);
        new ExibidorDeImagem("Café Bicubica", imgCafe2, true);
        new ExibidorDeImagem("Café Padrão", imgCafe3, true);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        System.out.println("A resolução da sua máquina é : " + d.width + " x " + d.height);
    } //Fecha o main
}

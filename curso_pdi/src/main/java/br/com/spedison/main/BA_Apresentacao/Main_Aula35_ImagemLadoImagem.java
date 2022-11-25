package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.*;
import br.com.spedison.biblioteca_pdi.base.enuns.Interpolacao;
import br.com.spedison.biblioteca_pdi.base.enuns.TipoArranjo;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;

import java.awt.*;

public class Main_Aula35_ImagemLadoImagem {
    public static void main(String[] args) {
        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        ArranjoEntreImagens arranjoLinha1 = new ArranjoEntreImagens(sai.getDirBaseImagensEntrada("cafe1.jpg"));
        ArranjoEntreImagens arranjoLinha2 = new ArranjoEntreImagens(sai.getDirBaseImagensEntrada("cafe1.jpg"));

        ArranjoEntreImagens arranjoResultado = new ArranjoEntreImagens();

        arranjoLinha1.adicionarImagem(new Imagem(sai.getDirBaseImagensEntrada("cafe2.jpg")), TipoArranjo.Horizontal, Color.BLACK.getRGB());

        Font f = new Font("Arial",Font.BOLD,120);

        ImagemTamanhoAjustado cafeCubico = new ImagemTamanhoAjustado(sai.getDirBaseImagensEntrada("cafeteira_vermelha.png"), Interpolacao.Cubica, 10.0, 10.0);
        cafeCubico.setColor(Color.BLACK);
        cafeCubico.setFonte(f);
        cafeCubico.escreveTexto("Cubico !!", 150, 150);
        arranjoLinha1.adicionarImagem(cafeCubico, TipoArranjo.Horizontal, Color.BLACK.getRGB());

        ImagemTamanhoAjustado cafeLinear = new ImagemTamanhoAjustado(sai.getDirBaseImagensEntrada("cafeteira_vermelha.png"), Interpolacao.Linear, 10.0, 10.0);
        cafeLinear.setColor(Color.BLACK);
        cafeLinear.setFonte(f);
        cafeLinear.escreveTexto("Linear !!", 150, 150);
        arranjoLinha2.adicionarImagem(cafeLinear, TipoArranjo.Horizontal, Color.BLACK.getRGB());

        arranjoLinha2.adicionarImagem(new Imagem(sai.getDirBaseImagensEntrada("cafe2.jpg")), TipoArranjo.Horizontal, Color.BLACK.getRGB());

        arranjoResultado.adicionarImagem(arranjoLinha1, TipoArranjo.Vertical, Color.BLACK.getRGB());
        arranjoResultado.adicionarImagem(arranjoLinha2, TipoArranjo.Vertical, Color.BLACK.getRGB());

        new ExibidorDeImagem("Colocando 1 do lado da outra.", new ImagemAjustadaTela(arranjoResultado, 0.95), true);
    }
}

package br.com.spedison.biblioteca_pdi_javafx.gui;

import javafx.scene.image.Image;

public class DadosDeImagemDaJanela implements  Comparable<DadosDeImagemDaJanela> {
    public DadosDeImagemDaJanela(String nomeJanela, Image image) {
        this.nomeJanela = nomeJanela;
        this.image = image;
    }

    public DadosDeImagemDaJanela() {
    }

    public String nomeJanela;
    public Image image;

    @Override
    public int compareTo(DadosDeImagemDaJanela o) {
        return this.nomeJanela.compareTo(o.nomeJanela);
    }
}

package br.com.spedison.biblioteca_pdi.base;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class ArranjoEntreImagensMatriz extends Imagem {

    List<Imagem> listaImagens = null;
    int linhas;
    int colunas;
    int corDeFundo;

    int espacoX = 0;
    int espacoY = 0;

    public ArranjoEntreImagensMatriz(BufferedImage[] bis) {
        listaImagens = Arrays.stream(bis).map(bImg -> new Imagem(bImg)).toList();
    }

    public ArranjoEntreImagensMatriz(List<Imagem> listaImagens) {
        this.listaImagens = listaImagens;
    }

    public ArranjoEntreImagensMatriz(List<Imagem> listaImagens, int corDeFundo) {
        this.listaImagens = listaImagens;
        this.corDeFundo = corDeFundo;
    }

    public ArranjoEntreImagensMatriz(String[] nomeArquivos) {
        listaImagens = Arrays.stream(nomeArquivos).map(nomeArquivo -> new Imagem(nomeArquivo)).toList();
    }

    public void setListaImagens(List<Imagem> listaImagens) {
        this.listaImagens = listaImagens;
    }

    public ArranjoEntreImagensMatriz setLinhas(int linhas) {
        this.linhas = linhas;
        return this;
    }

    public ArranjoEntreImagensMatriz setColunas(int colunas) {
        this.colunas = colunas;
        return this;
    }

    public int getCorDeFundo() {
        return corDeFundo;
    }

    public ArranjoEntreImagensMatriz setCorDeFundo(int corDeFundo) {
        this.corDeFundo = corDeFundo;
        return this;
    }

    public int getEspacoX() {
        return espacoX;
    }

    public ArranjoEntreImagensMatriz setEspacoX(int espacoX) {
        this.espacoX = espacoX;
        return this;
    }

    public int getEspacoY() {
        return espacoY;
    }

    public ArranjoEntreImagensMatriz setEspacoY(int espacoY) {
        this.espacoY = espacoY;
        return this;
    }

    public ArranjoEntreImagensMatriz processaImagens() {
        calculaTamanhoImagemSaida();
        adicionarImagems();
        return this;
    }

    public List<Imagem> getListaImagens() {
        return listaImagens;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public ArranjoEntreImagensMatriz calculaTamanhoImagemSaida() {
        int tamXMax = 0;
        int tamYMax = 0;

        for (int l = 0; l < linhas; l++) {
            int tamYAcc = 0;
            int tamXAcc = 0;

            for (int c = 0; c < colunas; c++) {
                int pos = l * colunas + c;

                if (pos >= listaImagens.size())
                    break;

                System.out.println("Adicionando imagem : %d - Linha = %d , Coluna = %d".formatted(pos, l, c));
                tamXAcc += listaImagens.get(pos).getLargura();
                tamYAcc = Math.max(listaImagens.get(pos).getAltura(), tamYAcc);
            }
            tamXMax = Math.max(tamXMax, tamXAcc);
            tamYMax += tamYAcc;
        }

        tamXMax += (espacoX * (colunas - 1));
        tamYMax += (espacoY * (linhas - 1));

        super.novaImagem(tamXMax, tamYMax, BufferedImage.TYPE_INT_RGB);
        super.pintaFundo(corDeFundo);
        System.out.println("Processanto %d imagens com o resultado Primeira de : %d x %d Final de %d x %d".
                formatted(
                        listaImagens.size(),
                        listaImagens.get(0).getLargura(), listaImagens.get(0).getAltura(),
                        getLargura(), getAltura()));

        return this;
    }

    @Override
    public ArranjoEntreImagensMatriz copiaImagemDe(Imagem fonte, int xInicio, int yInicio) {
        super.copiaImagemDe(fonte, xInicio, yInicio);
        return this;
    }

    public ArranjoEntreImagensMatriz adicionarImagems() {

        int posX;
        int posY = 0;
        int maxAltura;

        for (int l = 0; l < linhas; l++) {
            maxAltura = 0;
            posX = 0;
            for (int c = 0; c < colunas; c++) {
                int pos = l * colunas + c;

                if (pos >= listaImagens.size())
                    break;

                this.copiaImagemDe(listaImagens.get(pos), posX, posY);
                maxAltura = Math.max(maxAltura, listaImagens.get(pos).getAltura());
                posX += listaImagens.get(pos).getLargura();
                posX += espacoX;
            }
            posY += maxAltura;
            posY += espacoY;
        }
        return this;
    }
}
package br.com.spedison.biblioteca_pdi.transformacoes.espacial;

import br.com.spedison.biblioteca_pdi.base.Imagem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class FlipRodaImagem extends Imagem {

    private class NovoPonto {
        NovoPonto(int x, int y, int[] dado) {
            this.x = x;
            this.y = y;
            this.dado = dado;
        }

        public int x;
        public int y;
        public int[] dado;
    }

    public FlipRodaImagem(BufferedImage bi) {
        super(bi);
    }

    public FlipRodaImagem(String nomeArquivo) {
        super(nomeArquivo);
    }

    public FlipRodaImagem(Imagem ai) {
        super(ai.clone());
    }

    public FlipRodaImagem flipv() {
        var saida = new Imagem(getTamanho());
        getStreamAltura().parallel().forEach(
                posY -> {
                    getStreamLargura().parallel().forEach(
                            posX -> {
                                int newPosX = -(posX - getOrigemX() - getLimiteX());
                                var valPixel = getPixel(posX, posY);
                                saida.setPixel(newPosX, posY, valPixel);
                            });
                });
        setImageBuffer(saida.getImageBuffer());
        return this;
    }

    public FlipRodaImagem fliph() {
        Imagem saida = new Imagem(this.getTamanho());
        getStreamAltura().parallel().forEach(
                posY -> {
                    getStreamLargura().parallel().
                            mapToObj(posX -> new NovoPonto(
                                    posX, -(posY - getOrigemY() - getLimiteY()),
                                    getPixel(posX, posY))).
                            forEach(novoPonto -> {
                                saida.setPixel(novoPonto.x, novoPonto.y, novoPonto.dado);
                            });
                });
        this.setImageBuffer(saida.getImageBuffer());
        return this;
    }

    public FlipRodaImagem roda90gHorario() {
        var destino = new Imagem(getAltura(), getLargura()); // Cria imagem com tamanhos invertidos (X,Y) -> (Y,X)
        var origem = this;

        origem.guardaOrigem();
        origem.setOrigemX(0);
        origem.setOrigemY(0);

        origem.getStreamAltura().forEach(
                posY -> {
                    origem
                            .getStreamLargura()
                            .forEach(posX ->
                                    destino.setPixel(posY, getLargura() - posX - 1, getPixel(posX, posY)));
                });
        setImageBuffer(destino.getImageBuffer());
        restauraOrigem();
        return this;
    }

    public FlipRodaImagem inverteXY() {
        var saida = new Imagem(getTamanho()[1], getTamanho()[0]);
        saida.setOrigemY(getOrigemX()); // Troca as Origens.
        saida.setOrigemX(getOrigemY());
        getStreamAltura().parallel().forEach(
                posY -> {
                    getStreamLargura().parallel().forEach(
                            posX -> {
                                var valPixel = getPixel(posX, posY);
                                saida.setPixel(posY, posX, valPixel);
                            });
                });
        setImageBuffer(saida.getImageBuffer());
        return this;
    }

    public FlipRodaImagem salva(String nomeArquivo) {
        super.salva(nomeArquivo);
        return this;
    }
}
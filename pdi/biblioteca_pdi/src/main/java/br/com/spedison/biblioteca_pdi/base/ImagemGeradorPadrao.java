package br.com.spedison.biblioteca_pdi.base;

import java.awt.*;

public class ImagemGeradorPadrao extends Imagem {

    public ImagemGeradorPadrao(int xLen, int yLen) {
        super(xLen, yLen);
    }

    public ImagemGeradorPadrao(int xLen, int yLen, int rgbCorFundo) {
        super(xLen, yLen, rgbCorFundo);
    }

    public ImagemGeradorPadrao(int xLen, int yLen, int rgbCorFundo, int type) {
        super(xLen, yLen, rgbCorFundo, type);
    }

    @FunctionalInterface
    public static interface ProcessaPadrao {
        int[] processaPadrao(Point p, int[] rgb);
    }

    public void processa(ProcessaPadrao p) {
        getStreamAltura()
                .parallel()
                .forEach(y -> {
                    getStreamLargura()
                            .parallel()
                            .forEach(x -> {
                                int[] val = p.processaPadrao(new Point(x, y), getPixel(x, y));
                                setPixel(x, y, val);
                            });
                });
    }
}

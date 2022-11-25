package br.com.spedison.biblioteca_pdi.padroes;

import br.com.spedison.biblioteca_pdi.base.Imagem;

public abstract class ImagemPadrao extends Imagem {

    public ImagemPadrao(int xLen, int yLen) {
        super(xLen, yLen);
    }

    public abstract void geraPadrao();
}

package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;

public class TransformacaoIdentidade implements TransformacaoPixelRGB<TransformacaoIdentidade> {

    @Override
    public int[] transformaRGB(int[] valorPixel, int x, int y, Imagem imagem) {
        return valorPixel;
    }

    @Override
    public String toString() {
        return "Classe : %s \nF(x) = X".
                formatted(
                        this.getClass().getSimpleName()
                );
    }
}

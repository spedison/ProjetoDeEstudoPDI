package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;

public class TransformacaoInverte implements TransformacaoPixelRGB<TransformacaoInverte> {
    @Override
    public int[] transformaRGB(int[] valorPixel, int x, int y, Imagem imagem) {
        return new int[]{
                (imagem.getValorMaximo() - valorPixel[Imagem.Vermelho]),
                (imagem.getValorMaximo() - valorPixel[Imagem.Verde]),
                (imagem.getValorMaximo() - valorPixel[Imagem.Azul])};
    }

    @Override
    public String toString() {
        return "Classe : %s -> F(X) = 255 - X | Em RGB.".
                formatted(
                        this.getClass().getSimpleName()
                );
    }

    @Override
    public String getNomeModelo() {
        return "Inversao :: F(X) = Max - X";
    }
}

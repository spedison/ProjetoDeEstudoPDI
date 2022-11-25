package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;

public class TransformacaoBitsEDesloca implements TransformacaoPixelRGB<TransformacaoBitsEDesloca> {

    int[] bits = {0xFF, 0xFF, 0xFF};
    int deslocamentos = 0;

    static final int maxByte = 0xFF;

    public TransformacaoBitsEDesloca(int[] bits, int deslocamentos) {
        this.bits = bits;
        this.deslocamentos = deslocamentos;
    }

    public TransformacaoBitsEDesloca() {
    }

    public void setBits(int[] bits) {
        this.bits = bits;
    }

    public void setBits(int bits, int rgb) {
        this.bits[rgb] = bits;
    }

    public void setDeslocamentos(int deslocamentos) {
        this.deslocamentos = deslocamentos;
    }

    private int transforma(int valor, int rgb) {
        if (deslocamentos == 0)
            return maxByte & (valor & bits[rgb]);

        if (deslocamentos > 0)
            return maxByte & ((valor & bits[rgb]) >> deslocamentos);

        return maxByte & ((valor & bits[rgb]) << Math.abs(deslocamentos));
    }

    @Override
    public int[] transformaRGB(int[] valorPixel, int x, int y, Imagem imagem) {
        return new int[]{
                transforma(valorPixel[Imagem.Vermelho], Imagem.Vermelho),
                transforma(valorPixel[Imagem.Verde], Imagem.Verde),
                transforma(valorPixel[Imagem.Azul], Imagem.Azul)
        };
    }

    @Override
    public String toString() {
        return "Classe : %s - Manipulando Bits [%d,%d,%d] %s %d".
                formatted(
                        this.getClass().getSimpleName(),
                        bits[0], bits[1], bits[2],
                        deslocamentos >= 0 ? ">>" : "<<",
                        Math.abs(deslocamentos)
                );
    }
}
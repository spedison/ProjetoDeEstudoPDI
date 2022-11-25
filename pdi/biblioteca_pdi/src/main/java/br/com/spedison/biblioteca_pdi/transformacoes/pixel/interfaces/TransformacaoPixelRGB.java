package br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces;

import br.com.spedison.biblioteca_pdi.base.Imagem;

public interface TransformacaoPixelRGB <R> {
    int [] transformaRGB(int[] valorPixel, int x, int y, Imagem imagem);
    default R inicializa(Imagem imagem) {
        return (R) this;
    }

    default R finaliza() {
        return (R) this;
    }

    default String getNomeModelo() {
        return "Sem Nome";
    }
}

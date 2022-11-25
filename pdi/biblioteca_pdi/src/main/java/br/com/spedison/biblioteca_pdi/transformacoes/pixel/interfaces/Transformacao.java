package br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces;

import br.com.spedison.biblioteca_pdi.base.Imagem;

public interface Transformacao<R> {

    int transforma(int x, int y, int val, int rgb);

    default int transforma(int x, int y, int val) {
        return transforma(x, y, val, Imagem.Vermelho);
    }

    default R inicializa(Imagem imagem) {
        return (R) this;
    }

    default R finaliza() {
        return (R) this;
    }
}

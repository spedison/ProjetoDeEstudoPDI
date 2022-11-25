package br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias;

import br.com.spedison.biblioteca_pdi.base.Imagem;

public interface InferenciaPonto {

    void setImagem (Imagem imagem);
    void inferePonto (double px, double py, ResultadoInferencia resultado);

    default String getNome() {
        return this.getClass().getName();
    }
}

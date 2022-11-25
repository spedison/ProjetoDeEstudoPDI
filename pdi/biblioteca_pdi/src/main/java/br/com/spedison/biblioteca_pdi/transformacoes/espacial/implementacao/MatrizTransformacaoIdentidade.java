package br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao;


public class MatrizTransformacaoIdentidade extends MatrizTransformacaoEspacial {

    public MatrizTransformacaoIdentidade() {
        super();
        calculaInverso();
    }
}

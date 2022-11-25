package br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao;

public class MatrizTransformacaoCisalhamentoHorizontal extends MatrizTransformacaoEspacial {

    double escalaY;

    public MatrizTransformacaoCisalhamentoHorizontal() {
        super();
    }

    public MatrizTransformacaoCisalhamentoHorizontal(double escalaY) {
        setEscalaY(escalaY);
    }

    public double getEscalaY() {
        return escalaY;
    }

    public MatrizTransformacaoCisalhamentoHorizontal setEscalaY(double escalaY) {
        this.escalaY = escalaY;
        direta.setEntry(0, 1, escalaY);
        calculaInverso();
        return this;
    }

}

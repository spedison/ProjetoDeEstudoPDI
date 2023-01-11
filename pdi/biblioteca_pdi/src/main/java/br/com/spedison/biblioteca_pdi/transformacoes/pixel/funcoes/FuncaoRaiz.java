package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;

import br.com.spedison.usogeral.Arredondador;

public class FuncaoRaiz extends FuncaoBase {
    double raiz;

    public FuncaoRaiz() {
        super();
        raiz = 2.0;
    }

    public FuncaoRaiz(double multiplica, double soma, double raiz) {
        super(multiplica, soma);
        this.raiz = raiz;
    }

    public void setRaiz(double raiz) {
        this.raiz = raiz;
    }

    @Override
    public int transforma(int x, int y, int val, int rgb) {
        double resultado = Math.pow(val, 1.0 / raiz) * multiplica;
        resultado += soma;
        return Arredondador.arredonda(resultado);
    }

    @Override
    public String toString() {
        return "Funcão : F(X) =  SQR[%f](X) * %f + %f".
                formatted(
                        raiz,
                        multiplica,
                        soma
                );
    }

    @Override
    public String getNome() {
        return "RAIZ(" + raiz + ")";
    }
}

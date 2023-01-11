package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;

import br.com.spedison.usogeral.Arredondador;

public class FuncaoPotencia extends FuncaoBase {
    double potencia;


    public FuncaoPotencia() {
        super();
        potencia = 10.0;
    }

    public FuncaoPotencia(double multiplicador, double soma, double potencia) {
        super(multiplicador, soma);
        this.potencia = potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    @Override
    public int transforma(int x, int y, int val, int rgb) {
        double resultado = Math.pow(val, potencia) * multiplica;
        resultado += soma;
        return Arredondador.arredonda(resultado);
    }

    @Override
    public String toString() {
        return "Funcão : F(X) = %f * X^%f + %f".
                formatted(
                        multiplica,
                        potencia,
                        soma
                );
    }

    @Override
    public String getNome() {
        return "Potencia(X,"+potencia+")";
    }

}

package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;

import br.com.spedison.biblioteca_pdi.auxiliar.Arredondador;

public class FuncaoLogInverso extends FuncaoBase {

    double base;


    public FuncaoLogInverso() {
        super();
        base = 10.0;
    }

    public FuncaoLogInverso(double multiplica, double soma, double base) {
        super(multiplica, soma);
        this.base = base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    @Override
    public int transforma(int x, int y, int val, int rgb) {
        double resultado = soma + (Math.pow(base, val) * multiplica);
        return Arredondador.arredonda(resultado);
    }

    @Override
    public String toString() {
        return "Funcão : F(X) = %f * %f^X + %f".
                formatted(
                        multiplica,
                        base,
                        soma
                );
    }

    @Override
    public String getNome() {
        return "Potencia("+base+",X)";
    }
}

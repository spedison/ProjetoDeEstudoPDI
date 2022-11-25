package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;


public class FuncaoLinear extends FuncaoBase {

    public FuncaoLinear() {
        super();
    }

    public FuncaoLinear(double multiplicador, double soma) {
        super(multiplicador, soma);
    }

    @Override
    public int transforma(int x, int y, int val, int rgb) {
        return (int) (val * multiplica + soma + 0.5);
    }

    @Override
    public String toString() {
        return "Funcão : F(X) = %f*X + %f".
                formatted(
                        multiplica,
                        soma
                );
    }

    @Override
    public String getNome() {
        return "Modelo Linear";
    }
}

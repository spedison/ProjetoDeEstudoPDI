package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;

public class FuncaoMinMax extends FuncaoBase {

    public TipoMinMax tipo = TipoMinMax.Minimo;

    private double limite;
    private double soma = 0.;
    private double multiplica = 1.;

    public FuncaoMinMax() {
    }

    public FuncaoMinMax(double multiplica, double soma, double limite, TipoMinMax tipo) {
        this.tipo = tipo;
        this.limite = limite;
        this.soma = soma;
        this.multiplica = multiplica;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public void setTipo(TipoMinMax tipo) {
        this.tipo = tipo;
    }

    @Override
    public int transforma(int x, int y, int val, int rgb) {
        double resultado = (tipo == TipoMinMax.Maximo) ?
                Math.max(val, limite) :
                Math.min(val, limite);
        resultado *= multiplica;
        resultado += soma;
        return (int) (resultado + .5);
    }

    @Override
    public String toString() {
        return "Funcão : F(X) = %s(%f,X) * %f + %f ".
                formatted(
                        tipo.toString(),
                        multiplica,
                        soma
                );
    }

    @Override
    public void setSoma(double x) {
        this.soma = x;
    }

    @Override
    public void setMultiplicador(double x) {
        this.multiplica = x;
    }

    @Override
    public String getNome() {
        return (tipo == TipoMinMax.Maximo ? "Máximo(X," : "Mínimo(X,") + limite + ")";
    }
}
package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;

import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.Funcao;

public class FuncaoGama extends FuncaoBase {

    private double maximo = 255;
    private double gama = .5;

    public FuncaoGama() {
        this(.5,255);
    }

    public FuncaoGama(double gama, double maximo) {
        this(gama, maximo, 1., 0.);
    }

    public FuncaoGama(double gama, double maximo, double multiplica, double soma) {
        setMaximo(maximo);
        setGama(gama);
        setSoma(soma);
        setMultiplicador(multiplica);
    }

    public void setGama(double gama) {
        this.gama = gama;
    }

    public void setMaximo(double maximo) {
        this.maximo = maximo;
    }

    @Override
    public int transforma(int x, int y, int val, int rgb) {
        double resultado = Math.pow(val / maximo, gama) * maximo;
        resultado *= multiplica;
        resultado += soma;
        return (int) (resultado + .5);
    }

    @Override
    public String toString() {
        return "Funcão : F(X) = ( (X/$f ^ %f) * %f ) * %f + %f ".
                formatted(
                        maximo,
                        gama,
                        maximo,
                        multiplica,
                        soma
                );
    }

    @Override
    public String getNome() {
        return "Gama(X," + gama + ")";
    }
}
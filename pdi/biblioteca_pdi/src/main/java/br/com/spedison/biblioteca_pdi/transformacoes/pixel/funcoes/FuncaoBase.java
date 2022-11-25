package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;

import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.Funcao;

public abstract class  FuncaoBase implements Funcao<FuncaoBase> {
    double multiplica;
    double soma;

    public FuncaoBase() {
        multiplica = 1.0;
        soma = 0.0;
    }

    public FuncaoBase(double multiplica, double soma) {
        this.multiplica = multiplica;
        this.soma = soma;
    }

    public void setMultiplicador(double multiplica) {
        this.multiplica = multiplica;
    }

    public void setSoma(double soma) {
        this.soma = soma;
    }

}

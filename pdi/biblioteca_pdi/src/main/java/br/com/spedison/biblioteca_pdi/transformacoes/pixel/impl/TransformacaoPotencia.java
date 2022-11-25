package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoPotencia;

import java.util.ArrayList;


/***
 * Aplica uma transformação Logaritma em qq base com uma multiplicação (na potencia) e uma soma
 * Não deixando ser menor que zero e nem maior que 255.
 * Modelo padrão: 1 * (x ^ 2 ) + 0
 */
public class TransformacaoPotencia extends TransformacaoBaseFuncoes<TransformacaoPotencia,FuncaoPotencia> {


    public TransformacaoPotencia(ArrayList<FuncaoPotencia> funcoes) {
        this.funcoes = funcoes;
    }

    public TransformacaoPotencia(double multiplicador, double soma, double potencia) {
        funcoes = new ArrayList<FuncaoPotencia>(3);
        funcoes.add(new FuncaoPotencia(multiplicador, soma, potencia));
        funcoes.add(new FuncaoPotencia(multiplicador, soma, potencia));
        funcoes.add(new FuncaoPotencia(multiplicador, soma, potencia));
    }

    public TransformacaoPotencia() {
        this(1, 0, 2);
    }

    public void setPotencia(double potencia) {
        for (FuncaoPotencia l : funcoes)
                l.setPotencia(potencia);
    }

    @Override
    public String getNomeModelo() {
        return "Transformação Potência :: F(X) = Multiplicador * X ^ potencia + Soma";
    }

}
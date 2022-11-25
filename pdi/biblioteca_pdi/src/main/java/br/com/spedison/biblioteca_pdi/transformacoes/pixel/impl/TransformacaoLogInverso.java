package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoLogInverso;

import java.util.ArrayList;


/***
 * Aplica uma transformação Logaritma em qq base com uma multiplicação (na potencia) e uma soma
 * Não deixando ser menor que zero e nem maior que 255.
 * Modelo padrão: 1 * (10 ^ x )  + 0
 */
public class TransformacaoLogInverso extends TransformacaoBaseFuncoes<TransformacaoLogInverso, FuncaoLogInverso> {

    public TransformacaoLogInverso(ArrayList<FuncaoLogInverso> funcoes) {
        this.funcoes = funcoes;
    }

    public TransformacaoLogInverso(double multiplicador, double soma, double baseLog) {
        funcoes = new ArrayList<FuncaoLogInverso>(3);
        funcoes.add(new FuncaoLogInverso(multiplicador, soma, baseLog));
        funcoes.add(new FuncaoLogInverso(multiplicador, soma, baseLog));
        funcoes.add(new FuncaoLogInverso(multiplicador, soma, baseLog));
    }

    public TransformacaoLogInverso() {
        this(1, 0, 10);
    }

    public TransformacaoLogInverso setBaseLogInverso(double baseLog) {
        for (FuncaoLogInverso l : funcoes) {
            l.setBase(baseLog);
        }
        return this;
    }

    @Override
    public String getNomeModelo() {
        return "Transformação Inverso do Log :: F(X) = Multiplica * (base ^ X) + Soma";
    }
}
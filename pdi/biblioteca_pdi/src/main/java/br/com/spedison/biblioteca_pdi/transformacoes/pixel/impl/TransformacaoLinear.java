package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoLinear;

import java.util.ArrayList;


/***
 * Aplica uma transformação Linear simples em todo o espectro
 * Modelo Padrão : x * 2.0 - 1.0
 */
public class TransformacaoLinear extends TransformacaoBaseFuncoes<TransformacaoLinear, FuncaoLinear> {

    public TransformacaoLinear(ArrayList<FuncaoLinear> funcoes) {
        this.funcoes = funcoes;
    }

    public TransformacaoLinear(double soma, double multiplicador) {
        funcoes = new ArrayList<FuncaoLinear>(3);
        funcoes.add(new FuncaoLinear(multiplicador, soma));
        funcoes.add(new FuncaoLinear(multiplicador, soma));
        funcoes.add(new FuncaoLinear(multiplicador, soma));
    }

    public TransformacaoLinear() {
        this(-1., 2.);
    }

    @Override
    public String getNomeModelo() {
        return "Transformação Linear :: F(X) = AX + B";
    }
}
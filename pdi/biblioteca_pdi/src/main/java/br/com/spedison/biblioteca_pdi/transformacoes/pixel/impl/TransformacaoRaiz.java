package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoRaiz;

import java.util.ArrayList;


/***
 * Aplica uma transformação Logaritma em qq base com uma multiplicação (na potencia) e uma soma
 * Não deixando ser menor que zero e nem maior que 255.
 * Modelo padrão: 1 * sqrt(x) + 0
 */
public class TransformacaoRaiz extends TransformacaoBaseFuncoes<TransformacaoRaiz, FuncaoRaiz> {


    public TransformacaoRaiz(ArrayList<FuncaoRaiz> funcoes) {
        this.funcoes = funcoes;
    }

    public TransformacaoRaiz(double multiplicador, double soma, double raiz) {
        funcoes = new ArrayList<FuncaoRaiz>();
        funcoes.add(new FuncaoRaiz(raiz, multiplicador, soma));
        funcoes.add(new FuncaoRaiz(raiz, multiplicador, soma));
        funcoes.add(new FuncaoRaiz(raiz, multiplicador, soma));
    }

    public TransformacaoRaiz() {
        this(1.0, 1, 2.0);
    }

    public void setRaiz(double raiz) {
        for (FuncaoRaiz l : funcoes)
            l.setRaiz(raiz);
    }

    @Override
    public String getNomeModelo() {
        return "Transformação Raiz :: F(X) = Raiz(raiz,X) * Multiplicador + Soma";
    }
}
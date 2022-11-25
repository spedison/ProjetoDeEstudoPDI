package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoLog;

import java.util.ArrayList;


/***
 * Aplica uma transformação Logaritma em qq base com uma multiplicação (na potencia) e uma soma
 * Não deixando ser menor que zero e nem maior que 255.
 * Modelo padrão: Long10(x ^ 1)  + 0
 */
public class TransformacaoLog extends TransformacaoBaseFuncoes<TransformacaoLog, FuncaoLog> {


    public TransformacaoLog(ArrayList<FuncaoLog> funcoes) {
        this.funcoes = funcoes;
    }

    public TransformacaoLog(double multiplicador, double soma, double baseLog) {
        funcoes = new ArrayList<FuncaoLog>(3);
        funcoes.add(new FuncaoLog(multiplicador, soma, baseLog));
        funcoes.add(new FuncaoLog(multiplicador, soma, baseLog));
        funcoes.add(new FuncaoLog(multiplicador, soma, baseLog));
    }

    public TransformacaoLog() {
        this(1, 0, 10);
    }

    public void setBaseLog(double baseLog) {
        for (FuncaoLog l : funcoes)
            l.setBaseLog(baseLog);
    }

    @Override
    public String getNomeModelo() {
        return "Transformação Logarítma :: F(X) = Log(base,X) * A + B";
    }

}

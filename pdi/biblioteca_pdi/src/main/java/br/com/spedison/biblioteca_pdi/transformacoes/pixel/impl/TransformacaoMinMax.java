package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoMinMax;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.TipoMinMax;

import java.util.ArrayList;


/***
 * Aplica uma transformação Min/Max
 * Modelo padrão: Min/Max(x,limite)  * 1. + 0.
 */
public class TransformacaoMinMax extends TransformacaoBaseFuncoes<TransformacaoMinMax, FuncaoMinMax> {

    static private final double limitePadrao = 255 / 2;

    public TransformacaoMinMax(ArrayList<FuncaoMinMax> funcoes) {
        setFuncoes(funcoes);
    }

    public TransformacaoMinMax(double multiplicador, double soma, double limite, TipoMinMax tipo) {
        funcoes = new ArrayList<FuncaoMinMax>(3);
        funcoes.add(new FuncaoMinMax(multiplicador, soma, limite, tipo));
        funcoes.add(new FuncaoMinMax(multiplicador, soma, limite, tipo));
        funcoes.add(new FuncaoMinMax(multiplicador, soma, limite, tipo));
    }

    public TransformacaoMinMax() {
        this(1., 0, limitePadrao, TipoMinMax.Maximo);
    }

    public void setLimite(double baseLog) {
        for (FuncaoMinMax l : funcoes)
            l.setLimite(baseLog);
    }

    public void setTipo(TipoMinMax tipo) {
        for (FuncaoMinMax l : funcoes)
                l.setTipo(tipo);
    }

    @Override
    public String getNomeModelo() {
        return "Transformação MAX/MIN :: F(X) = " + funcoes.get(0).getNome() ;
    }
}

package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoGama;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoLog;

import java.util.ArrayList;


/***
 * Aplica uma transformação Gama
 * Não deixando ser menor que zero e nem maior que 255.
 * Modelo padrão: Pow(x/Max,gama)*Max*Multiplica +  Soma
 */
public class TransformacaoGama extends TransformacaoBaseFuncoes<TransformacaoGama, FuncaoGama> {


    public TransformacaoGama(ArrayList<FuncaoGama> funcoes) {
        this.funcoes = funcoes;
    }

    public TransformacaoGama(double gama, double limite, double multiplicador, double soma) {
        funcoes = new ArrayList<FuncaoGama>(3);
        funcoes.add(new FuncaoGama(gama, limite, multiplicador, soma));
        funcoes.add(new FuncaoGama(gama, limite, multiplicador, soma));
        funcoes.add(new FuncaoGama(gama, limite, multiplicador, soma));
    }

    public TransformacaoGama() {
        this(0.5, 255, 1., 0.);
    }

    public void setMaximo(double maximo) {
        for (FuncaoGama l : funcoes)
            l.setMaximo(maximo);
    }

    public void setGama(double gama) {
        for (FuncaoGama l : funcoes)
            l.setGama(gama);
    }

    @Override
    public String getNomeModelo() {
        return "Transformação Gamma :: F(X) = Gamma(g,X) * Multiplica + Soma";
    }

}

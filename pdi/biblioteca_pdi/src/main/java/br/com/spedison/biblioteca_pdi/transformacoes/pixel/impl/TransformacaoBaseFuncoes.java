package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.Funcao;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoBase;

import java.util.ArrayList;

/***
 * Classe abstrata Básica para todas as funções algébricas do tipo F(X).
 * @param <R> Nome da Classe que a herdará.
 * @param <F> Nome da Classe de função que será usada.
 */
public abstract class TransformacaoBaseFuncoes<R,F extends FuncaoBase> implements TransformacaoPixelRGB<R> {

    protected ArrayList<F> funcoes;

    public void setMultiplicador(double multiplicador) {
        for (F l : funcoes) {
            l.setMultiplicador(multiplicador);
        }
    }

    public void setSoma(double soma) {
        for (Funcao l : funcoes) {
            l.setSoma(soma);
        }
    }

    public void setFuncoes(ArrayList<F> funcoes) {
        this.funcoes = funcoes;
    }

    public ArrayList<F> getFuncoes() {
        return this.funcoes;
    }

    private int transforma(int x, int y, int valor, int rgb) {
        return funcoes
                .get(rgb)
                .transforma(x, y, valor);
    }

    @Override
    public int[] transformaRGB(int[] valorPixel, int x, int y, Imagem imagem) {
        return new int[]{
                transforma(x, y, valorPixel[Imagem.Vermelho], Imagem.Vermelho),
                transforma(x, y, valorPixel[Imagem.Verde], Imagem.Verde),
                transforma(x, y, valorPixel[Imagem.Azul], Imagem.Azul)
        };
    }



    @Override
    public String toString() {
        return "Classe : %s \n Usando os modelo %s\n %s : R\n %s : G\n %s : B.".
                formatted(
                        this.getClass().getSimpleName(),
                        getNomeModelo(),
                        funcoes.get(0).toString(),
                        funcoes.get(1).toString(),
                        funcoes.get(2).toString()
                );
    }
}

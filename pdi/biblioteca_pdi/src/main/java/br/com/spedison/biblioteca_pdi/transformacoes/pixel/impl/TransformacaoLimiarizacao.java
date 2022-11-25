package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoLimiarizar;

public class TransformacaoLimiarizacao implements TransformacaoPixelRGB<TransformacaoLimiarizacao> {

    FuncaoLimiarizar[] funcoesLimiarizar;

    public TransformacaoLimiarizacao(FuncaoLimiarizar[] funcoesLimiarizar) {
        this.funcoesLimiarizar = funcoesLimiarizar;
    }

    public TransformacaoLimiarizacao(int minimo, int maximo) {
        FuncaoLimiarizar i = new FuncaoLimiarizar(minimo, maximo);
        funcoesLimiarizar = new FuncaoLimiarizar[]{i, i, i};
    }

    public TransformacaoLimiarizacao(Imagem imagem) {
        this(imagem.getValorMinimo(), imagem.getValorMaximo());
    }


    public TransformacaoLimiarizacao adicionaIntervalos(int intervaloInicio, int intervaloFinal, int valor) {
        for (int i = 0; i < funcoesLimiarizar.length; i++) {
            adicionaIntervalos(intervaloInicio, intervaloFinal, valor, i);
        }
        return this;
    }

    public TransformacaoLimiarizacao adicionaIntervalos(int intervaloInicio, int intervaloFinal, int valor, int rgb) {
        funcoesLimiarizar[rgb].addLimite(intervaloInicio, intervaloFinal, valor);
        return this;
    }

    private int transforma(int x, int y, int valor, int rgb) {
        return funcoesLimiarizar[rgb].transforma(x, y, valor);
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
    public String getNomeModelo() {
        return "Transformação de Limiarização usando regras que dentro de intervalos retorna uma constante.";
    }
}
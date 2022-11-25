package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoHistogramaProporcionalLimites;

public class TransformacaoHistogramaPropocionalLimites implements TransformacaoPixelRGB<TransformacaoHistogramaPropocionalLimites> {

    FuncaoHistogramaProporcionalLimites funcao;
    double percentualMinimoHistograma;

    boolean processaHistogramaDeImagemCinza;

    public TransformacaoHistogramaPropocionalLimites(boolean processaHistogramaDeImagemCinza) {
        this.processaHistogramaDeImagemCinza = processaHistogramaDeImagemCinza;
    }

    public TransformacaoHistogramaPropocionalLimites() {
        processaHistogramaDeImagemCinza = false;
        percentualMinimoHistograma = 1. / 300.;
    }

    public TransformacaoHistogramaPropocionalLimites(boolean processaHistogramaDeImagemCinza, double percentualMinimoHistograma) {
        this.percentualMinimoHistograma = percentualMinimoHistograma;
        this.processaHistogramaDeImagemCinza = processaHistogramaDeImagemCinza;
    }

    public double getPercentualMinimoHistograma() {
        return percentualMinimoHistograma;
    }

    public void setPercentualMinimoHistograma(double percentualMinimoHistograma) {
        this.percentualMinimoHistograma = percentualMinimoHistograma;
    }

    @Override
    public TransformacaoHistogramaPropocionalLimites inicializa(Imagem imagem) {
        funcao = new FuncaoHistogramaProporcionalLimites(processaHistogramaDeImagemCinza, percentualMinimoHistograma);
        funcao.inicializa(imagem);
        return this;
    }

    @Override
    public int[] transformaRGB(int[] valorPixel, int x, int y, Imagem imagem) {
        return new int[]{
                funcao.transforma(x, y, valorPixel[Imagem.Vermelho], Imagem.Vermelho),
                funcao.transforma(x, y, valorPixel[Imagem.Verde], Imagem.Verde),
                funcao.transforma(x, y, valorPixel[Imagem.Azul], Imagem.Azul)};
    }

    @Override
    public String getNomeModelo() {
        return "Modelo de Auto Ajuste com histograma usando proporcionalidade e definindo bordas de corte por nível mínimo.";
    }
}
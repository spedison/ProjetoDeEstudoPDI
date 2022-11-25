package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.base.HistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoHistogramaProbabilidadeAcumulada;

public class TransformacaoHistogramaProbabilidadeAcumulada implements TransformacaoPixelRGB<TransformacaoHistogramaProbabilidadeAcumulada> {

    FuncaoHistogramaProbabilidadeAcumulada funcao;

    public TransformacaoHistogramaProbabilidadeAcumulada() {
    }

    @Override
    public TransformacaoHistogramaProbabilidadeAcumulada inicializa(Imagem imagem) {
        HistogramaImagem hist = new HistogramaImagem();
        hist.processaHistograma(imagem);
        funcao = new FuncaoHistogramaProbabilidadeAcumulada(hist);
        funcao.inicializa(imagem);
        return this;
    }

    @Override
    public int[] transformaRGB(int[] valorPixel, int x, int y, Imagem imagem) {
        int r = funcao.transforma(x, y, valorPixel[Imagem.Vermelho], Imagem.Vermelho);
        int g = funcao.transforma(x, y, valorPixel[Imagem.Verde], Imagem.Verde);
        int b = funcao.transforma(x, y, valorPixel[Imagem.Azul], Imagem.Azul);
        return new int[]{r, g, b};
    }

    @Override
    public String getNomeModelo() {
        return "Transformação usando o Histograma de probabilidade acumulado * Valor Máximo";
    }
}

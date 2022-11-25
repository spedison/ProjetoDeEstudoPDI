package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;

import br.com.spedison.biblioteca_pdi.auxiliar.Arredondador;
import br.com.spedison.biblioteca_pdi.base.HistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.Transformacao;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.TransformacaoCinza;

import java.util.stream.IntStream;

public class FuncaoHistogramaProporcionalLimites implements Transformacao<FuncaoHistogramaProporcionalLimites> {

    // Representa a conversão de histograma para Cada uma das cores.
    private int[][] histogramaAjustado;
    private final double percentualMinimoParaConsiderarNoHistograma;
    private final boolean processaImagemComoCinza;

    private HistogramaImagem histogramaImagem;

    public FuncaoHistogramaProporcionalLimites() {
        percentualMinimoParaConsiderarNoHistograma = 0.05;
        processaImagemComoCinza = false;
    }

    public FuncaoHistogramaProporcionalLimites(boolean processaImagemComoCinza, double percentualMinimoNoHistograma) {
        this.processaImagemComoCinza = processaImagemComoCinza;
        this.percentualMinimoParaConsiderarNoHistograma = percentualMinimoNoHistograma;
    }

    public void setHistogramaImagem(HistogramaImagem histogramaImagem) {
        this.histogramaImagem = histogramaImagem;
    }

    @Override
    public int transforma(int x, int y, int val, int rgb) {
        return histogramaAjustado[rgb][val];
    }

    private int converteInt(double valor) {
        return Arredondador.arredonda(valor);
    }

    private int calculaPonto(int x, int minimo, int novoMinimo, int maximo, int novoMaximo) {

        if (x >= novoMaximo)
            return maximo;

        if (x <= novoMinimo)
            return minimo;

        double fracaoNumerador = (maximo - minimo);
        double fracaoDenominador = (novoMaximo - novoMinimo);
        double multiplicador = (x - novoMinimo);
        double ret = minimo + (multiplicador * (fracaoNumerador / fracaoDenominador));
        return converteInt(ret);
    }

    @Override
    public FuncaoHistogramaProporcionalLimites inicializa(Imagem imagem) {
        TransformadorPixel p = new TransformadorPixel(imagem);

        if (histogramaImagem == null) {

            if (processaImagemComoCinza) {
                TransformacaoCinza t = new TransformacaoCinza();
                p.transforma(t);
            }

            histogramaImagem = new HistogramaImagem();
            histogramaImagem.processaHistograma(p);
        }

        // Assim calcula 1 vez só.
        int[][] novosLimites = histogramaImagem.getValorMinimoEMaximoDaBanda(percentualMinimoParaConsiderarNoHistograma, processaImagemComoCinza);

        histogramaAjustado = new int[3][imagem.getValorMaximo() + 1];

        // Monta uma tabela de relação para todos os valores possíveis. (MIN a MAX) para todas as cores.
        IntStream
                .range(0, histogramaAjustado.length)
                .parallel()
                .forEach(cor ->
                        IntStream
                                .range(0, histogramaAjustado[cor].length)
                                .forEach(pontoCalculado ->
                                        histogramaAjustado[cor][pontoCalculado] = calculaPonto(pontoCalculado, imagem.getValorMinimo(), novosLimites[cor][0], imagem.getValorMaximo(), novosLimites[cor][1])
                                )
                );

        return this;
    }

    @Override
    public FuncaoHistogramaProporcionalLimites finaliza() {
        histogramaImagem = null;
        return this;
    }
}
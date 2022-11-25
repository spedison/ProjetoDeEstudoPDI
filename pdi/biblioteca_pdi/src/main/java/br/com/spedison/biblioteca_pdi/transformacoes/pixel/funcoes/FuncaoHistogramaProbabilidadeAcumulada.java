package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;

import br.com.spedison.biblioteca_pdi.auxiliar.Arredondador;
import br.com.spedison.biblioteca_pdi.base.HistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.Funcao;

public class FuncaoHistogramaProbabilidadeAcumulada implements Funcao<FuncaoHistogramaProbabilidadeAcumulada> {

    private int minimo;
    private int maximo;

    private double soma;
    private double multiplica;

    double[][] histProbabilidade;
    HistogramaImagem histogramaImagem;

    int rgb;

    public HistogramaImagem getHistogramaImagem() {
        return histogramaImagem;
    }

    public void setHistogramaImagem(HistogramaImagem histogramaImagem) {
        this.histogramaImagem = histogramaImagem;
    }

    public FuncaoHistogramaProbabilidadeAcumulada() {
        multiplica = 1.0;
        soma = 0.0;
    }

    public FuncaoHistogramaProbabilidadeAcumulada(HistogramaImagem histogramaImagem) {
        this();
        this.histogramaImagem = histogramaImagem;
        maximo = histogramaImagem.getMaximo();
        minimo = histogramaImagem.getMinimo();
    }

    @Override
    public int transforma(int x, int y, int val, int rgb) {
        double ret = (histProbabilidade[rgb][val] * maximo) * multiplica + soma;
        return Arredondador.arredonda(ret);
    }

    @Override
    public void setSoma(double x) {
        soma = x;
    }

    @Override
    public void setMultiplicador(double x) {
        multiplica = x;
    }

    @Override
    public FuncaoHistogramaProbabilidadeAcumulada inicializa(Imagem imagem) {
        if (histogramaImagem == null) {
            histogramaImagem = new HistogramaImagem();
            histogramaImagem.processaHistograma(imagem);
            maximo = imagem.getValorMaximo();
            minimo = imagem.getValorMinimo();
        }
        histProbabilidade = histogramaImagem.getHistogramProb(false, false, true);
        return this;
    }

    @Override
    public String getNome() {
        return "Normaliza Histograma com P(X)*MAX";
    }
}
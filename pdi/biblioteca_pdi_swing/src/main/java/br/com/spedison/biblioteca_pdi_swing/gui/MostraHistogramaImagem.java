package br.com.spedison.biblioteca_pdi_swing.gui;

import br.com.spedison.biblioteca_pdi.base.HistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MostraHistogramaImagem {

    public enum TipoGrafico {
        Probabilidade,
        Contagem
    }

    private XYSeries montaArrayParaPlotar(String nome, int[] valores) {
        final XYSeries ret = new XYSeries(nome);
        final int[] pos = {0};

        Arrays.stream(valores).mapToDouble(valArr -> valArr).forEach(x -> ret.add(pos[0]++, x));
        return ret;
    }

    private XYSeries montaArrayParaPlotar(String nome, double[] valores) {
        final XYSeries ret = new XYSeries(nome);
        final int[] pos = {0};

        Arrays.stream(valores).forEach(x -> ret.add(pos[0]++, x));
        return ret;
    }

    private int tamanhoX;
    private int tamanhoY;

    private final String nomeImagem;
    private HistogramaImagem imagem;

    private TipoGrafico tipoGrafico;

    private Imagem imgemResultado;

    public MostraHistogramaImagem(HistogramaImagem imagem, String nomeImagem) {
        this(imagem, nomeImagem, TipoGrafico.Contagem, 1000, 500);
    }

    public MostraHistogramaImagem(HistogramaImagem imagem, String nomeImagem, TipoGrafico tipoGrafico) {
        this(imagem, nomeImagem, tipoGrafico, 1000, 500);
    }

    public MostraHistogramaImagem(HistogramaImagem imagem, String nomeImagem, TipoGrafico tipoGrafico, int tamanhoX, int tamanhoY) {
        this.tipoGrafico = tipoGrafico;
        this.imagem = imagem;
        this.nomeImagem = nomeImagem;
        this.tamanhoX = tamanhoX;
        this.tamanhoY = tamanhoY;
    }

    public Imagem getImgemResultado() {
        return imgemResultado;
    }

    private XYSeriesCollection montaDataset(int[][] valores) {
        return montaDataset(valores[0], valores[1], valores[3]);
    }

    private XYSeriesCollection montaDataset(int[] vermelho, int[] verde, int[] azul) {
        final XYSeriesCollection resultado = new XYSeriesCollection();
        resultado.addSeries(montaArrayParaPlotar("Vermelho", vermelho));
        resultado.addSeries(montaArrayParaPlotar("Verde", verde));
        resultado.addSeries(montaArrayParaPlotar("Azul", azul));
        return resultado;
    }

    private XYSeriesCollection montaDataset(double[][] valores) {
        return montaDataset(valores[0], valores[1], valores[2]);
    }

    private XYSeriesCollection montaDataset(double[] vermelho, double[] verde, double[] azul) {
        final XYSeriesCollection resultado = new XYSeriesCollection();
        resultado.addSeries(montaArrayParaPlotar("Vermelho", vermelho));
        resultado.addSeries(montaArrayParaPlotar("Verde", verde));
        resultado.addSeries(montaArrayParaPlotar("Azul", azul));
        return resultado;
    }

    private void formataTodosGraficos(boolean acc) {

        XYSeriesCollection dados;
        if (tipoGrafico == TipoGrafico.Contagem)
            dados = montaDataset(imagem.getHistograma(Imagem.Vermelho, acc),
                    imagem.getHistograma(Imagem.Verde, acc),
                    imagem.getHistograma(Imagem.Azul, acc));
        else
            dados = montaDataset(
                    imagem.getHistogramProb(false, true, acc));

        String titulo = "Histograma de %s".formatted(tipoGrafico == TipoGrafico.Contagem ? "Contagem" : "Probabilidade");

        JFreeChart histograma = ChartFactory.
                createXYLineChart(titulo,
                        "Intensidade da cor",
                        tipoGrafico == TipoGrafico.Contagem ? "Contagem" : "Probabilidade"
                        , dados);

        histograma.getXYPlot().getRenderer().setSeriesPaint(0, Color.RED);
        histograma.getXYPlot().getRenderer().setSeriesPaint(1, Color.GREEN);
        histograma.getXYPlot().getRenderer().setSeriesPaint(2, Color.BLUE);

        List titulos = new ArrayList<Title>();
        titulos.add(new TextTitle("Imagem : " + nomeImagem));
        histograma.setSubtitles(titulos);
        histograma.getXYPlot().getDomainAxis().setRange(0, 255);
        histograma.getXYPlot().getDomainAxis().setLabelFont(new Font("Arial", Font.TRUETYPE_FONT, 18));
        imgemResultado = new Imagem(histograma.createBufferedImage(tamanhoX, tamanhoY));
    }

    public Imagem getImagem(boolean acc) {
        formataTodosGraficos(acc);
        return imgemResultado;
    }

    public void Show(String nomeJanela, boolean acc, boolean saidaAoFechar) {
        new ExibidorDeImagem(nomeJanela, getImagem(acc), saidaAoFechar);
    }

}

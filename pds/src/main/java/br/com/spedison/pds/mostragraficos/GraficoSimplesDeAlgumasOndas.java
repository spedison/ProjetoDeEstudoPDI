package br.com.spedison.pds.mostragraficos;

import br.com.spedison.usogeral.sinais.*;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.awt.Color;

public class GraficoSimplesDeAlgumasOndas {
    public static void main(String[] args) throws Exception {

        int numeroDePontos = 10_000;
        double inicio = -.001;
        double fim = .001;

        Sinal somaSenos = new SinalSomaSenos(new double[]{50, 5, 2, 4}, new double[]{900.D, 5000.D, 550.D,500000.});
        Sinal quadrado = new SinalRetangular(0.0005, .5, -10., 10.);
        Sinal denteDeSerra = new SinalDenteDeSerra(.00002,1000000.,0);
        Sinal produtoSenos = new SinalProdutoSenos(new double[]{10, 4}, new double[]{95000.D,4501.});

// Trecho para convolução do sinal
//        var conv = new Convolucao1D();
//        conv.setKernel(new double[]{1 - 1. / 17., 1 - 3. / 17., 1 - 9. / 17., 1 - 3. / 17., 1 - 1. / 17.});
//        double[] dadosParaImpressaoConvolucao = conv.convolucao(dadosParaImpressaoOriginal[1]);
//        chart.addSeries("Sinal Após Convolução", dadosParaImpressaoOriginal[0], dadosParaImpressaoConvolucao);

        double[][] dadosParaImpressaoSomaSenos = somaSenos.geraArrayDadosParaProcessamento(inicio, fim, (fim-inicio)/numeroDePontos);
        double[][] dadosParaImpressaoQuadrado = quadrado.geraArrayDadosParaProcessamento(inicio, fim, (fim-inicio)/numeroDePontos);
        double[][] dadosParaImpressaoDenteDeSerra = denteDeSerra.geraArrayDadosParaProcessamento(inicio, fim, (fim-inicio)/numeroDePontos);
        double[][] dadosParaImpressaoProdutoSenos = produtoSenos.geraArrayDadosParaProcessamento(inicio, fim, (fim-inicio)/numeroDePontos);

        // Create Chart
        final XYChart chart = QuickChart.getChart(
                "Sinais de Onda", "Tempo (Em segundos)",
                "Amplitude (F(t))", "Soma de Senos",
                dadosParaImpressaoSomaSenos[0], dadosParaImpressaoSomaSenos[1]);

        chart.addSeries("Onda Quadrada", dadosParaImpressaoQuadrado[0],dadosParaImpressaoQuadrado[1]);
        chart.addSeries("Dente de Serra", dadosParaImpressaoDenteDeSerra[0],dadosParaImpressaoDenteDeSerra[1]);
        chart.addSeries("Produto de Senos", dadosParaImpressaoProdutoSenos[0],dadosParaImpressaoProdutoSenos[1]);

        chart.getSeriesMap().get("Dente de Serra").setMarkerColor(Color.GREEN).setLineColor(Color.GREEN);
        chart.getSeriesMap().get("Produto de Senos").setMarkerColor(Color.BLACK).setLineColor(Color.BLACK);

        chart.getStyler().setZoomEnabled(true);

        chart.getStyler().setSeriesColors(new Color[]{Color.RED, Color.CYAN});

        final SwingWrapper<XYChart> sw = new SwingWrapper<>(chart);
        sw.displayChart();
    }
}

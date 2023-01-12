package br.com.spedison.pds.fourier.continuo;


import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class MainMostraSinal {

    public static void mainTempo(String[] args) {

        int tamanho = 10_000;
        double inicio = 0;
        double fim = CaracteristicasSinal.tempoBase*5;

        double[][] dados = CaracteristicasSinal.sinal.geraArrayDadosParaProcessamento(inicio, fim, (fim - inicio) / tamanho);

        XYChart chart = QuickChart.getChart("Análise de Sinais", "X", "Y", CaracteristicasSinal.sinal.getNome(), dados[0], dados[1]);

        var serie = chart.getSeriesMap().get(CaracteristicasSinal.sinal.getNome());
        serie.setLineStyle(SeriesLines.SOLID);
        serie.setMarker(SeriesMarkers.NONE);
        serie.setShowInLegend(true);

        chart.getStyler().setZoomEnabled(true); // Habilita o Zoom
        chart
                .getStyler()
                .setLegendPosition(Styler.LegendPosition.OutsideS)// Seta a posicção da Legenda.
                .setToolTipsEnabled(true); // Mostra os tooltips de cada ponto mostrando os valores ao passar o Mouse sobre o ponto.
        // Para não mostrar a legenda.
        //chart.getStyler().setLegendVisible(false);
// Show it
        SwingWrapper<XYChart> janelaDoGraficoDeSinal = new SwingWrapper<>(chart);
        janelaDoGraficoDeSinal.setTitle("Gráficos para análise de sinais");
        janelaDoGraficoDeSinal.displayChart();

// Exemplos para gravar o gráfico em arquivos de imagenes.
// Save it
//        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);
// or save it in high-res
//        BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
    }

    public static void main(String[] args) throws Exception {
        mainTempo(args);
    }
}
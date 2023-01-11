package br.com.spedison.pds.continuo;


import br.com.spedison.pds.ferramentas.CalculaIntegralComplexa;
import br.com.spedison.pds.ferramentas.Complexo;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class MainMostraSerieDeFourierDoSinal {


    static Complexo calculaPontoNaFrequencia(double n, double l) {
        final var tempoInicio = 0;
        final var tempoFim = 2 * l;
        final var passoIntegracao = l / 100_000.;
        var integral = new CalculaIntegralComplexa();
        CalculaIntegralComplexa.Funcao1Complexa funcao1Complexa =
                (tempo) -> Complexo.expNegativo((n * Math.PI * tempo) / l).mutiplica(CaracteristicasSinal.sinal.calculaAmplitude(tempo));
        integral.setFuncaoComplexa(funcao1Complexa);
        integral.setPasso(passoIntegracao);
        return integral
                .getIntegral(tempoInicio, tempoFim)
                .mutiplica(1. / (2. * l));
    }

    public static void mainFrequencia(String[] args) throws Exception {
        double[] xData;
        double[] yDataImaginario;
        int tamanho = 200;

        // Calcula as frequencias usadas para colocar no gráfico.
        xData = IntStream
                .range(-tamanho, tamanho)
                .mapToDouble(x -> x) // N inteiro
                .map(x -> x / (2. * CaracteristicasSinal.tempoBaseAnalise)) // F = n / ( 2 . l )
                .toArray();

        System.out.println("Frequências preenchidas.");

        var yDataComplexo = IntStream.range(-tamanho, tamanho)
                .mapToDouble(x -> x)
                .map(x -> {
                    System.out.println("Processamendo Frequência " + (x / (CaracteristicasSinal.tempoBaseAnalise * 2.)));
                    return x;
                })
                .mapToObj(n -> calculaPontoNaFrequencia(n, CaracteristicasSinal.tempoBaseAnalise))
                // .mapToDouble(Complexo::imaginario)
                .toArray(Complexo[]::new);

        yDataImaginario = Arrays.stream(yDataComplexo).mapToDouble(Complexo::imaginario).toArray();
        var yDataReal = Arrays.stream(yDataComplexo).mapToDouble(Complexo::real).toArray();
        var yDataModulo = Arrays.stream(yDataComplexo).mapToDouble(Complexo::getModulo).toArray();
// Create Chart
        XYChart chart = QuickChart.getChart("Transformada de Fourier", "Frequência", "Amplitude", CaracteristicasSinal.sinal.getNome() + "(Imaginário)", xData, yDataImaginario);

        var serie = chart.getSeriesMap().get(CaracteristicasSinal.sinal.getNome() + "(Imaginário)");
        serie.setMarker(SeriesMarkers.CIRCLE);
        serie.setMarkerColor(Color.MAGENTA);
        serie.setLineStyle(SeriesLines.NONE);

        chart.addSeries(CaracteristicasSinal.sinal.getNome() + "(Real)",xData, yDataReal);
        var serieReal = chart.getSeriesMap().get(CaracteristicasSinal.sinal.getNome() + "(Real)");
        serieReal.setMarker(SeriesMarkers.CROSS);
        serieReal.setMarkerColor(Color.GREEN);
        serieReal.setLineStyle(SeriesLines.NONE);


        chart.getStyler().setZoomEnabled(true); // Habilita o Zoom
        chart
                .getStyler()
                .setLegendPosition(Styler.LegendPosition.OutsideS)// Seta a posicção da Legenda.
                .setToolTipsEnabled(true); // Mostra os tooltips de cada ponto mostrando os valores ao passar o Mouse sobre o ponto.
// Show it
        new SwingWrapper<>(chart).displayChart();
    }

    public static void main(String[] args) throws Exception {
        mainFrequencia(args);
    }
}
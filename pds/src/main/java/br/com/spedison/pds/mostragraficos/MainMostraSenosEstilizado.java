package br.com.spedison.pds.mostragraficos;


import org.knowm.xchart.*;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.Circle;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.knowm.xchart.style.markers.Square;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class MainMostraSenosEstilizado {

    static double A = 40.;
    static double M = 1.5;

    static double delta = 10;
    static double WC = 100.;
    static double WC_Plus = WC + delta;
    static double WC_Minus = WC - delta;
    static double PHI = 3. * Math.PI / 2.;


    static double calculaPonto(double x) {
        return A * Math.sin(WC * x) +
                A * M / 2. * Math.sin(WC_Minus * x + PHI) +
                A * M / 2. * Math.sin(WC_Plus * x - PHI);
    }

    public static void main(String[] args) throws Exception {
        double[] xData; // = new double[1000];
        double[] yData; // = new double[1000];
        int tamanho = 10000;

        xData = IntStream.range(0, tamanho).mapToDouble(x -> 10. * ((double) x / (double) tamanho)).toArray();
        yData = Arrays.stream(xData).map(MainMostraSenosEstilizado::calculaPonto).toArray(); //x -> 10 * Math.sin(x * 50) * 10 * Math.sin(x + 1.)).toArray();
        var yData2 = Arrays.stream(xData).map(x -> 40. * Math.sin(x )).
                toArray(); //x -> 10 * Math.sin(x * 50) * 10 * Math.sin(x + 1.)).toArray();

// Create Chart
        XYChart chart = QuickChart.getChart("Seno X", "X", "Sin(X)", "F(x) = 10*sin(x) ", xData, yData);

        // Customize Chart
        //chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setAxisTitlesVisible(true);
        chart.getStyler().setXAxisDecimalPattern("0.0000000");
        chart.getStyler().setAxisTicksLineVisible(false);

        chart.addSeries("Seno Normal", xData, yData2);
        chart.getSeriesMap().get("Seno Normal").setMarker(SeriesMarkers.CROSS);
        chart.getSeriesMap().get("Seno Normal").setMarkerColor(Color.CYAN);
        chart.getSeriesMap().get("Seno Normal").setLineStyle(SeriesLines.DOT_DOT);
        chart.getSeriesMap().get("Seno Normal").setLineWidth(0.F);

        chart.getStyler().setZoomEnabled(true);
        chart.getStyler().setSeriesColors(new Color[]{Color.RED, Color.BLUE});
        //chart.getStyler().setSeriesLines(new BasicStroke[]{});

        //final BasicStroke[] strokes = new BasicStroke[2];
        //final var DEFAULT_LINE_STROKE = SeriesLines.NONE;
        //Arrays.fill(strokes, DEFAULT_LINE_STROKE);
        //chart.getStyler().setSeriesLines(strokes);

        //chart.getStyler().setMarkerSize(15);
        //final Marker [] markers = new Marker[2];
        //Arrays.fill(markers, new Circle());
        //chart.getStyler().setSeriesMarkers(markers);


// Show it
        new SwingWrapper<>(chart).displayChart();
    }
}
package br.com.spedison.mostragraficos;


import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class MainMostraSenos2 {

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
        yData = Arrays.stream(xData).map(MainMostraSenos2::calculaPonto).toArray(); //x -> 10 * Math.sin(x * 50) * 10 * Math.sin(x + 1.)).toArray();
        var yData2 = Arrays.stream(xData).map(x -> 40. * Math.sin(x )).
                toArray(); //x -> 10 * Math.sin(x * 50) * 10 * Math.sin(x + 1.)).toArray();

// Create Chart
        XYChart chart = QuickChart.getChart("Seno X", "X", "Sin(X)", "F(x) = 10*sin(x) ", xData, yData);
        chart.addSeries("Seno Normal", xData, yData2);
        chart.getSeriesMap().get("Seno Normal").setMarker(SeriesMarkers.NONE);
        chart.getSeriesMap().get("Seno Normal").setLineWidth(1.F);
        chart.getStyler().setZoomEnabled(true);
        chart.getStyler().setSeriesColors(new Color[]{Color.RED, Color.BLUE});
        //chart.getStyler().setSeriesLines(new BasicStroke[]{});


// Show it
        new SwingWrapper<>(chart).displayChart();

// Save it
        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);

// or save it in high-res
        BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
    }
}
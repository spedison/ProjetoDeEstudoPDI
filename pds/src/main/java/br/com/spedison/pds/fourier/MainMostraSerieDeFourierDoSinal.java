package br.com.spedison.pds.fourier;


import br.com.spedison.pds.ferramentas.CalculaIntegralComplexa;
import br.com.spedison.pds.ferramentas.Complexo;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MainMostraSerieDeFourierDoSinal {

    static double A = 14.D;
    static double limiteTempo = 1. * (1. / (900.));

    static double calculaPontoNoTempo(double x) {
        return A * 1. * Math.sin(2 * Math.PI * 900. * x) +
                A * 1. * Math.sin(2. * Math.PI * 3110. * x);
    }

    static double calculaPontoNaFrequencia(double phy) {
        final var tempoAnalisado = 1. * limiteTempo;
        final var tempoInicio = -tempoAnalisado / 2.;
        final var tempoFim = +tempoAnalisado / 2.;
        final var tempoDelta = (tempoFim - tempoInicio) / 300_000.;
        var integral = new CalculaIntegralComplexa();
        CalculaIntegralComplexa.Funcao1Complexa funcao1Complexa = (tempo) -> Complexo.exp(1. / phy).mutiplica(calculaPontoNoTempo(tempo)).mutiplica(1 / tempo);
        integral.setFuncaoComplexa(funcao1Complexa);
        integral.setPasso(tempoDelta);
        return integral
                .getIntegral(tempoInicio, tempoFim)
                .getModulo();//Imaginario();
    }

    public static void mainFrequencia(String[] args) throws Exception {
        double[] xData; // = new double[1000];
        double[] yData; // = new double[1000];
        double tamanho = 6_000;
        double inicio = -6_000.;
        double fim = Math.abs(inicio);

        xData = IntStream
                .range(0, (int) tamanho)
                .mapToDouble(x -> (double) x)
                .map(x -> ((fim - inicio) * (x / tamanho)) + inicio).toArray();

        yData = Arrays
                .stream(xData)
                .map(x -> {
                    System.out.println("Processamendo Frequencia " + x);
                    return x;
                })
                .map(MainMostraSerieDeFourierDoSinal::calculaPontoNaFrequencia)
                .toArray(); //x -> 10 * Math.sin(x * 50) * 10 * Math.sin(x + 1.)).toArray();
        //var yData2 = Arrays.stream(xData).map(x -> 40. * Math.sin(x)).
        //        toArray(); //x -> 10 * Math.sin(x * 50) * 10 * Math.sin(x + 1.)).toArray();

// Create Chart
        XYChart chart = QuickChart.getChart("Análise do Sinal", "Phy", "Amplitude de Phy", "Transf. Fourier F(x)", xData, yData);
        //chart.addSeries("Seno Normal", xData, yData2);
        //chart.getSeriesMap().get("Seno Normal").setMarker(SeriesMarkers.NONE);
        //chart.getSeriesMap().get("Seno Normal").setLineWidth(1.F);
        chart.getStyler().setZoomEnabled(true);
        //chart.getStyler().setSeriesColors(new Color[]{Color.RED, Color.BLUE});
        //chart.getStyler().setSeriesLines(new BasicStroke[]{});
// Show it
        new SwingWrapper<>(chart).displayChart();
// Save it
        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);
// or save it in high-res
        BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
    }

    public static void mainTempo(String[] args) throws Exception {
        double[] xData; // = new double[1000];
        double[] yData; // = new double[1000];

        int tamanho = 10_000;
        double inicio = -limiteTempo / 2.;
        double fim = limiteTempo / 2.;

        xData = IntStream
                .range(0, tamanho)
                .mapToDouble(x -> (fim - inicio) * ((double) x / (double) tamanho) + inicio)
                .toArray();

        yData = Arrays
                .stream(xData)
                .map(MainMostraSerieDeFourierDoSinal::calculaPontoNoTempo)
                .toArray(); //x -> 10 * Math.sin(x * 50) * 10 * Math.sin(x + 1.)).toArray();

// Create Chart
        XYChart chart = QuickChart.getChart("Analisando Sinais", "X", "Y", "2 Senos somados", xData, yData);
        chart.getStyler().setZoomEnabled(true);

// Show it
        new SwingWrapper<>(chart).displayChart();

// Save it
        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);

// or save it in high-res
        BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
    }

    public static void main(String[] args) throws Exception {
        //mainTempo(args);
        mainFrequencia(args);
        //mainCalcula1Frequencia(args);
    }

    private static void mainCalcula1Frequencia(String[] args) {
        double valorEm1700Vezes2Pi = calculaPontoNaFrequencia(2 * Math.PI * 1700.);
        double valorEm1700 = calculaPontoNaFrequencia(1700.);

        double valorEm1200Vezes2Pi = calculaPontoNaFrequencia(2 * Math.PI * 1200.);
        double valorEm1200 = calculaPontoNaFrequencia(1200.);

        System.out.println("valorEm1700 = " + valorEm1700);
        System.out.println("valorEm1700Vezes2Pi = " + valorEm1700Vezes2Pi);

        System.out.println("valorEm1200 = " + valorEm1200);
        System.out.println("valorEm1200Vezes2Pi = " + valorEm1200Vezes2Pi);

    }
}
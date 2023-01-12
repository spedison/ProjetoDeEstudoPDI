package br.com.spedison.pds.fourier;


import br.com.spedison.pds.auxiliar.CalculaIntegralComplexa;
import br.com.spedison.pds.auxiliar.Complexo;
import br.com.spedison.usogeral.sinais.Sinal;
import br.com.spedison.usogeral.sinais.SinalRetangular;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MainMostraSerieDeFourierDoSinal {

    static final double tempoBase = 1. / 900.;
    static final double lAnalise = 1. / 1800.;

    //private static Sinal senos = new SinalSomaSenos(new double[]{A, A / 2., A / 3.}, new double[]{frequanciaBase, frequanciaBase * 5., frequanciaBase * 9.});
    private static final Sinal senos = new SinalRetangular(tempoBase, 0.5, 0., 10.);

    //SinalSomaSenos(new double[]{A, A / 2., A / 3.}, new double[]{frequanciaBase, frequanciaBase * 5., frequanciaBase * 9.});
    //private static SinalSomaSenos senos = new SinalSomaSenos(new double[]{A}, new double[]{frequanciaBase});

    static double calculaPontoNoTempo(double x) {
        return senos.calculaAmplitude(x);
    }

    static Complexo calculaPontoNaFrequencia(double n, double l) {
        final var tempoInicio = 0;
        final var tempoFim = 2 * l;
        final var passoIntegracao = l / 1_000_000.;
        var integral = new CalculaIntegralComplexa();
        CalculaIntegralComplexa.Funcao1Complexa funcao1Complexa =
                (tempo) -> Complexo.expNegativo((n * Math.PI * tempo) / l).mutiplica(calculaPontoNoTempo(tempo));
        integral.setFuncaoComplexa(funcao1Complexa);
        integral.setPasso(passoIntegracao);
        return integral
                .getIntegral(tempoInicio, tempoFim)
                .mutiplica(1. / (2. * l));
    }

    public static void mainFrequencia(String[] args) {
        double[] xData; // = new double[1000];
        double[] yData; // = new double[1000];
        int tamanho = 150;


        // Calcula as frequencias usadas para colocar no gráfico.
        xData = IntStream
                .range(-tamanho, tamanho)
                .mapToDouble(x -> x) // N inteiro
                .map(x -> x / (2. * lAnalise)) // F = n / ( 2 . l )
                .toArray();

        System.out.println("Frequencias preenchidas.");

        yData = IntStream.range(-tamanho, tamanho)
                .mapToDouble(x -> x)
                .peek(x -> System.out.println("Processamendo Frequencia " + (x / (lAnalise * 2.))))
                .mapToObj(n -> calculaPontoNaFrequencia(n, lAnalise))
                .mapToDouble(Complexo::getModulo)
                .toArray(); //x -> 10 * Math.sin(x * 50) * 10 * Math.sin(x + 1.)).toArray();

// Create Chart
        XYChart chart = QuickChart.getChart("Análise do Sinal", "Phy", "Amplitude de Phy", "Transf. Fourier F(x)", xData, yData);

        /*chart.getStyler().setSeriesLines(new BasicStroke[]{SeriesLines.DOT_DOT});
        chart.getStyler().setMarkerSize(5);
        chart.getStyler().setMark  (Color.RED);*/

        //chart.addSeries("Seno Normal", xData, yData2);
        //chart.getSeriesMap().get("Seno Normal").setMarker(SeriesMarkers.NONE);
        //chart.getSeriesMap().get("Seno Normal").setLineWidth(1.F);
        chart.getStyler().setZoomEnabled(true);
        //chart.getStyler().setSeriesColors(new Color[]{Color.RED, Color.BLUE});
        //chart.getStyler().setSeriesLines(new BasicStroke[]{});
// Show it
        new SwingWrapper<>(chart).displayChart();
// Save it
        //BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);
// or save it in high-res
        //BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
    }

    public static void mainTempo(String[] args) throws Exception {
        double[] xData; // = new double[1000];
        double[] yData; // = new double[1000];

        int tamanho = 500;
        double inicio = 0;
        double fim = tempoBase;

        xData = IntStream
                .rangeClosed(0, tamanho)
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
//        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);
// or save it in high-res
//        BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
    }

    public static void main(String[] args) throws Exception {
        //mainTempo(args);
        mainFrequencia(args);
        //mainCalcula1Frequencia(args);
    }

    private static void mainCalcula1Frequencia(String[] args) {
        double valorEm1700Vezes2Pi = calculaPontoNaFrequencia(1., 1. / 900.).imaginario();
        double valorEm1700 = calculaPontoNaFrequencia(1700. / 900., 1. / 900.).imaginario();

        double valorEm1200Vezes2Pi = calculaPontoNaFrequencia(1.1, 1. / 900.).imaginario();
        double valorEm1200 = calculaPontoNaFrequencia(1200. / 900., 1. / 900.).imaginario();

        System.out.println("valorEm1700 = " + valorEm1700);
        System.out.println("valorEm1700Vezes2Pi = " + valorEm1700Vezes2Pi);

        System.out.println("valorEm1200 = " + valorEm1200);
        System.out.println("valorEm1200Vezes2Pi = " + valorEm1200Vezes2Pi);

    }
}
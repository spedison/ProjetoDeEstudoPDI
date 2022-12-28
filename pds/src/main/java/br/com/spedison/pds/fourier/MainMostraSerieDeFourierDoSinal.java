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

    // Frequencia fundamental. (Tamanho)
    static double l = 1. / 900.;


    static double calculaPontoNoTempo(double x) {
        final double omega = 2. * Math.PI * 900.;
        return A * 1. * Math.sin(omega * x);//+ // n = 1
        //A * 1. * Math.sin(2. * Math.PI * 1890. * x); // n= 2.1
    }

    static Complexo calculaPontoNaFrequencia(double n, double l) {
        final var tempoInicio = -l;
        final var tempoFim = +l;
        final var passoIntegracao = (2. * l) / 700_000.;
        var integral = new CalculaIntegralComplexa();
        CalculaIntegralComplexa.Funcao1Complexa funcao1Complexa = (tempo) -> Complexo.exp(-n * Math.PI * tempo / l).mutiplica(calculaPontoNoTempo(tempo));
        integral.setFuncaoComplexa(funcao1Complexa);
        integral.setPasso(passoIntegracao);
        return integral
                .getIntegral(tempoInicio, tempoFim)
                .mutiplica(1. / (2. * l));
    }

    public static void mainFrequencia(String[] args) throws Exception {
        double[] xData; // = new double[1000];
        double[] yData; // = new double[1000];
        int tamanho = 6_000;


        // Calcula as frequencias usadas para colocar no gráfico.
        xData = IntStream
                .range(-tamanho / 2, tamanho / 2)
                .mapToDouble(x -> x / 1000.)
                .map(x -> x / (2. * l))
                .toArray();

        System.out.println("Frequencias preenchidas.");

        yData = IntStream.range(-tamanho / 2, tamanho / 2)
                .mapToDouble(x -> x / 1000.)
                .map(x -> {
                    System.out.println("Processamendo Frequencia " + (x / (2. * l)));
                    return x;
                })
                .mapToObj(n -> calculaPontoNaFrequencia(n, l))
                .mapToDouble(Complexo::getModulo)
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
        //BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);
// or save it in high-res
        //BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
    }

    public static void mainTempo(String[] args) throws Exception {
        double[] xData; // = new double[1000];
        double[] yData; // = new double[1000];

        int tamanho = 10_000;
        double inicio = -l / 2.;
        double fim = l / 2.;

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
        double valorEm1700Vezes2Pi = calculaPontoNaFrequencia(1., 1. / 900.).getImaginario();
        double valorEm1700 = calculaPontoNaFrequencia(1700. / 900., 1. / 900.).getImaginario();

        double valorEm1200Vezes2Pi = calculaPontoNaFrequencia(1.1, 1. / 900.).getImaginario();
        double valorEm1200 = calculaPontoNaFrequencia(1200. / 900., 1. / 900.).getImaginario();

        System.out.println("valorEm1700 = " + valorEm1700);
        System.out.println("valorEm1700Vezes2Pi = " + valorEm1700Vezes2Pi);

        System.out.println("valorEm1200 = " + valorEm1200);
        System.out.println("valorEm1200Vezes2Pi = " + valorEm1200Vezes2Pi);

    }
}
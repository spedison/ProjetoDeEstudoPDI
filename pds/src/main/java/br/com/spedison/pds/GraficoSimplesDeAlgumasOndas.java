package br.com.spedison.pds;

import br.com.spedison.usogeral.sinais.Sinal;
import br.com.spedison.usogeral.sinais.SinalSomaSenos;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.awt.Color;

public class GraficoSimplesDeAlgumasOndas {
    public static void main(String[] args) throws Exception {

        //Onda onda = new OndaRetangular(4, .5, 0., 1.);
        Sinal sinal = new SinalSomaSenos(new double[]{100, 5, 2}, new double[]{6.D, 100.D, 550.D});
        //Onda onda = new OndaDenteDeSerra();
        //Onda onda = new OndaProdutoSenos(new double[]{100, 50, 25}, new double[]{10.D, 5.D, 2.5D});


//        var conv = new Convolucao1D();
//        conv.setKernel(new double[]{1 - 1. / 17., 1 - 3. / 17., 1 - 9. / 17., 1 - 3. / 17., 1 - 1. / 17.});
        double[][] dadosParaImpressaoOriginal = sinal.geraArrayDadosParaProcessamento(-10, 10, .01);
//        double[] dadosParaImpressaoConvolucao = conv.convolucao(dadosParaImpressaoOriginal[1]);

        // Create Chart
        final XYChart chart = QuickChart.getChart(
                "Sinais de Onda", "Tempo (Em segundos)",
                "Amplitude (F(t))", "Sinal de entrada",
                dadosParaImpressaoOriginal[0], dadosParaImpressaoOriginal[1]);

//        chart.addSeries("Sinal Após Convolução", dadosParaImpressaoOriginal[0], dadosParaImpressaoConvolucao);

        chart.getStyler().setZoomEnabled(true);

        chart.getStyler().setSeriesColors(new Color[]{Color.RED, Color.CYAN});

        final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
        sw.displayChart();

    }
}

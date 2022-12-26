package br.com.spedison;

import br.com.spedison.usogeral.auxiliar.MatrizAuxiliar;
import br.com.spedison.usogeral.auxiliar.wave.DadosArquivoWAV;
import br.com.spedison.usogeral.auxiliar.wave.ManipuladorArquivoWAV;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.io.File;

public class MainConvolucaoEImpressaoArquivoWave {
    public static void main(String[] args) throws Exception {

        var manip = new ManipuladorArquivoWAV();

        if (!new File(ManipuladorArquivoWAV.arquivoTmp).exists())
            manip.downloadAudioFile();

        double multiplicador = 1.;
        DadosArquivoWAV dw = manip.leDadosArquivosWaveTodosCanais(ManipuladorArquivoWAV.arquivoTmp, multiplicador, false);

        // Create Chart
        final XYChart chart = QuickChart.getChart("Dados do Arquivo " + ManipuladorArquivoWAV.arquivoTmp, "Tempo (base de 44kHz)", "Amplitude * 1E5", "Canal 1", dw.dadosTempos, dw.dadosAudio[0]);

        chart.addSeries("Canal 2", dw.dadosTempos, dw.dadosAudio[1]);

        // Filtro passa alta.
        //var kernelConvolucao = new double[]{-.75, -.75, 3.0, -.75, -.75};

        // Filtro passa mais alta.
        // var kernelConvolucao = new double[]{3., 3., -12., 3., 3.};
        // Mais suavizado
        //var kernelConvolucao = new double[]{6. / 96., 24. / 96., 36. / 96., 24. / 96., 6. / 96.};
        // Quase mantem o sinal original.
        //var kernelConvolucao = new double[]{1. / 100., 1. / 100., 6. / 100., 24. / 100., 36. / 100., 24. / 100., 6. / 100., 1. / 100., 1. / 100.};
        // Enfatiza os graves
        //var kernelConvolucao = new double[]{1 - 1. / 100., 1 - 1. / 100., 1 - 6. / 100., 1 - 24. / 100., 1 - 36. / 100., 1 - 24. / 100., 1 - 6. / 100., 1 - 1. / 100., 1 - 1. / 100.};
        //var kernelConvolucao = new double[]{1 - 1. / 100., 1 - 1. / 100., 1 - 6. / 100., 1 - 24. / 100., 1 - 36. / 100., 1 - 24. / 100., 1 - 6. / 100., 1 - 1. / 100., 1 - 1. / 100.};
        var kernelConvolucao = new double[]{-1., -1., -1., 8., -1, -1., -1.};

        var conv = new Convolucao1D();
        conv.setKernel(kernelConvolucao);

        var convolucao = conv.convolucao(dw.dadosAudio, true);
        var convolucaoParaImprimir = MatrizAuxiliar.transpor(convolucao);
        chart.addSeries("Convolução do Canal 1", dw.dadosTempos, convolucaoParaImprimir[0]);
        chart.addSeries("Convolução do Canal 2", dw.dadosTempos, convolucaoParaImprimir[1]);

        chart.getStyler().setSeriesColors(new Color[]{Color.RED, Color.BLUE, Color.CYAN, Color.GRAY});

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setCursorLineWidth(1.F);
        chart.getStyler().setPlotGridLinesVisible(true);
        chart.getSeriesMap().get("Canal 1").setMarker(SeriesMarkers.NONE);
        chart.getSeriesMap().get("Canal 2").setMarker(SeriesMarkers.NONE);
        chart.getSeriesMap().get("Convolução do Canal 1").setMarker(SeriesMarkers.NONE);
        chart.getSeriesMap().get("Convolução do Canal 2").setMarker(SeriesMarkers.NONE);


        new ManipuladorArquivoWAV().escreveDadosArquivosWaveTodosCanais("out.wav",
                convolucaoParaImprimir,
                1. / multiplicador, false, dw.sampleRate);

        final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
        sw.displayChart();

        /*while (true) {

            phase += 2 * Math.PI * 2 / 20.0;

            Thread.sleep(100);

            final double[][] data = getSineData(phase);

            chart.updateXYSeries("sine", data[0], data[1], null);
            sw.repaintChart();
        }*/

    }

    private static double[][] getSineData(double phase) {

        double[] xData = new double[100];
        double[] yData = new double[100];
        for (int i = 0; i < xData.length; i++) {
            double radians = phase + (2 * Math.PI / xData.length * i);
            xData[i] = radians;
            yData[i] = Math.sin(radians);
        }
        return new double[][]{xData, yData};
    }
}

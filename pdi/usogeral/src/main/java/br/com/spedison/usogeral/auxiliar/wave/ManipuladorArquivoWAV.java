package br.com.spedison.usogeral.auxiliar.wave;

import br.com.spedison.usogeral.auxiliar.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.stream.IntStream;

/***
 * Classe de interface para gravar dados em arquivos WAV de 16 Bits.
 */
public class ManipuladorArquivoWAV {

    static final private String nomeArquivo = "mixkit-fast-small-sweep-transition-166.wav";
    static final public String caminhoGit = "https://github.com/spedison/SonsDeTeste/raw/main/";
    static final public String arquivoDw = caminhoGit + nomeArquivo;
    static final public String arquivoTmp = Paths.get(System.getProperty("java.io.tmpdir"), nomeArquivo).toString();


    public void downloadAudioFile() {
        System.out.println("arquivoTmp = " + arquivoTmp);
        System.out.println("arquivoDw = " + arquivoDw);
        System.out.println("Fazendo download do arquivo " + nomeArquivo);
        Downloader.downloadFile(arquivoDw, arquivoTmp);
        System.out.println("Download do arquivo " + nomeArquivo + " realizado");
    }

    public DadosArquivoWAV leDadosArquivosWaveTodosCanais(String nomeArquivo, double multiplicador, boolean comTransposicao)
            throws IOException, WavFileException {
        // File inputStream = new File(nomeArquivo);

        WavFile wf = WavFile.openWavFile(new File(nomeArquivo));

        int numChannels = wf.getNumChannels();
        System.out.println("Channels = " + numChannels);

        long sampleRate = wf.getSampleRate();
        System.out.println("SampleRate = " + sampleRate);

        int numFrames = (int) wf.getNumFrames();
        System.out.println("NumFrames = " + numFrames);

        double[][] dados = new double[numChannels][numFrames];
        double[] dadosTempos = new double[(int) numFrames];
        wf.readFrames(dados, numFrames);

        final double[] tempos = {0.};
        IntStream.range(0, dados[0].length).forEach(pos -> {
            dadosTempos[pos] = tempos[0];
            tempos[0] += (1. / (float) sampleRate);
        });

        for (int c = 0; c < dados.length; c++) {
            for (int l = 0; l < dados[c].length; l++) {
                dados[c][l] *= multiplicador;
            }
        }

        double[][] dadosTranspostos = comTransposicao ? MatrizAuxiliar.transpor(dados) : dados;

        //System.out.println("SampleRate = " + sampleRate);
        //System.out.println(ImprimeArray.strDouble2D(dadosTranspostos, 12, 3));

        DadosArquivoWAV dw = new DadosArquivoWAV();
        dw.dadosAudio = dadosTranspostos;
        dw.sampleRate = sampleRate;
        dw.multiplicador = multiplicador;
        dw.dadosTempos = dadosTempos;
        return dw;
    }

    public void escreveDadosArquivosWaveTodosCanais(String nomeArquivo, DadosArquivoWAV dadosArquivoWAV) throws IOException, WavFileException {
        escreveDadosArquivosWaveTodosCanais(nomeArquivo, dadosArquivoWAV.dadosAudio, dadosArquivoWAV.multiplicador, false, dadosArquivoWAV.sampleRate);
    }

    public void escreveDadosArquivosWaveTodosCanais(String nomeArquivo, double[][] dados, double multiplicador, boolean transporAntesDeGravar, long taxaAmostragem)
            throws IOException, WavFileException {

        double[][] dadosParaGravar = transporAntesDeGravar ? MatrizAuxiliar.transpor(dados) : dados;

        IntStream.range(0, dados.length).forEach(posL -> {
            IntStream.range(0, dados[posL].length).forEach(posC -> dadosParaGravar[posL][posC] *= multiplicador);
        });

        WavFile wavFile = WavFile.newWavFile(new File(nomeArquivo), dadosParaGravar.length, dadosParaGravar[0].length, 16, taxaAmostragem);

        wavFile.writeFrames(dadosParaGravar, dadosParaGravar[0].length);
        wavFile.close();
    }


    public static void main(String[] args) throws IOException, WavFileException {
        ManipuladorArquivoWAV mps = new ManipuladorArquivoWAV();
        //mps.downloadAudioFile();
        DadosArquivoWAV dw = mps.leDadosArquivosWaveTodosCanais(arquivoTmp, 1.D, true);
        System.out.println(ImprimeArray.strDouble2D(dw.dadosAudio));
        System.out.println(ImprimeArray.strDouble(dw.dadosTempos, 10, 7));

        mps.escreveDadosArquivosWaveTodosCanais("out.wav", dw.dadosAudio, 1.0, true, dw.sampleRate);

    }
}
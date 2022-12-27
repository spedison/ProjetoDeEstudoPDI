package br.com.spedison.pds;

import br.com.spedison.usogeral.auxiliar.Downloader;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Paths;

public class MainPlaySinal extends SoundPlayerUsingClip {

    static final private String nomeArquivo = "mixkit-fast-small-sweep-transition-166.wav";
    static final private String arquivoDw = "https://github.com/spedison/SonsDeTeste/raw/main/" + nomeArquivo;
    static final private String arquivoTmp = Paths.get(System.getProperty("java.io.tmpdir"), nomeArquivo).toString();


    public void downloadAudioFile() {
        System.out.println("arquivoTmp = " + arquivoTmp);
        System.out.println("arquivoDw = " + arquivoDw);
        System.out.println("Fazendo download do arquivo " + nomeArquivo);
        Downloader.downloadFile(arquivoDw, arquivoTmp);
        System.out.println("Download do arquivo " + nomeArquivo + " realizado");
    }

    public void tocaArquivo() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(arquivoTmp)));

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);

        AudioFormat audioFormat = audioStream.getFormat();
        System.out.println(audioFormat.getEncoding().toString());
        System.out.println(audioFormat.getFrameSize());
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        Clip audioClip = AudioSystem.getClip();
        audioClip.open(audioStream);
        audioClip.addLineListener(this);
        audioClip.setMicrosecondPosition(0L);
        System.out.println(audioClip.getMicrosecondLength());
        audioClip.start();
        while (audioClip.isRunning());
            //audioClip.con
        audioClip.close();
        audioStream.close();
    }


    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        MainPlaySinal mps = new MainPlaySinal();
        //mps.downloadAudioFile();
        mps.tocaArquivo();

    }
}
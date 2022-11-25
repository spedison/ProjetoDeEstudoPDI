package br.com.spedison.main.BH_TransformacaoEspacial;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.TransformadorEspacialImagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoRotacao;//implementacao.MatrizTransformacaoRotacao;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.stream.IntStream;

import org.jcodec.javase.api.awt.AWTSequenceEncoder;

public class Main_Aula10_RodandoAngulos {


    public static void main(String[] args) throws IOException {

        var sai = SistemaArquivoImagens.getInstance();
        Imagem imgOriginal = new ImagemAjustadaTela(sai.getDirBaseImagensEntrada("cafe1.jpg"), 0.2);

        File arquivoVideo = new File(sai.getDirBaseImagensSaida("rotacoes5.mp4"));
        var enc = AWTSequenceEncoder.createSequenceEncoder(arquivoVideo, 25);

        // ExibidorListaDeImagens.adicionaImagem("Café", imgOriginal);
        final double multiplicador = 2.0;
        int[] totalFrames = {0};
        MatrizTransformacaoRotacao mtr = new MatrizTransformacaoRotacao();
        System.out.printf("%s - Iniciando processamento.\n", new Date());
        IntStream.range(0, 400).filter(i -> i % 1 == 0).mapToDouble(i -> i / 10.0).forEach(anguloRotacaoGraus -> {
                    System.out.printf("%s - Processando ângulo %f\n", new Date(), anguloRotacaoGraus);
                    totalFrames[0]++;
                    TransformadorEspacialImagem tei = new TransformadorEspacialImagem(imgOriginal);
                    tei.aplicaTransformacao(
                                    mtr.setAnguloGraus(anguloRotacaoGraus),
                                    multiplicador, multiplicador,
                                    (-imgOriginal.getLargura() / 2), (-imgOriginal.getAltura() / 2),
                                    (int) (-imgOriginal.getLargura() * (multiplicador / 2.0)), (int) (-imgOriginal.getAltura() * (multiplicador / 2.0)))
                            .setColor(Color.RED)
                            .setFonte(new Font("Arial", Font.BOLD, 20))
                            .escreveTexto("Angulo = %.3f".formatted(anguloRotacaoGraus), 10, 30);
                    try {
                        enc.encodeImage(tei.getImageBuffer());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        enc.finish();
        System.out.printf("%s - Processamento foi terminado com %d frames.\n\n", new Date(), totalFrames[0]);
    }

}

package br.com.spedison.main.BH_TransformacaoEspacial;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.TransformadorEspacialImagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoCisalhamentoVertical;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoEscala;
import org.jcodec.javase.api.awt.AWTSequenceEncoder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.stream.IntStream;

public class Main_Aula30_EscalaXY {


    public static void main(String[] args) throws IOException {

        var sai = SistemaArquivoImagens.getInstance();
        Imagem imgOriginal = new ImagemAjustadaTela(sai.getDirBaseImagensEntrada("cafe1.jpg"), 0.2);

        File arquivoVideo = new File(sai.getDirBaseImagensSaida("escala-1.mp4"));
        var enc = AWTSequenceEncoder.createSequenceEncoder(arquivoVideo, 25);

        final double multiplicador = 2.0;
        int[] totalFrames = {0};
        MatrizTransformacaoEscala mte = new MatrizTransformacaoEscala();

        System.out.printf("%s - Iniciando processamento.\n", new Date());
        IntStream.range(1, 600).mapToDouble(i -> i / 100.0).forEach(escala -> {
                    System.out.printf("%s - Processando escala %.3f\n", new Date(), escala);
                    totalFrames[0]++;
                    TransformadorEspacialImagem tei = new TransformadorEspacialImagem(imgOriginal);
                    tei.aplicaTransformacao(
                                    mte.setEscalaX(escala).setEscalaY(escala),
                                    multiplicador, multiplicador,
                                    (-imgOriginal.getLargura() / 2), (-imgOriginal.getAltura() / 2),
                                    (int) (-imgOriginal.getLargura() * (multiplicador / 2.0)), (int) (-imgOriginal.getAltura() * (multiplicador / 2.0)))
                            .setColor(Color.RED)
                            .setFonte(new Font("Arial", Font.BOLD, 18))
                            .escreveTexto("Escala = %.2f".formatted(escala), 10, 30);

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

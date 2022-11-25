package br.com.spedison.main.BH_TransformacaoEspacial;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.TransformadorEspacialImagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoCisalhamentoHorizontal;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoTranslacao;
import org.jcodec.javase.api.awt.AWTSequenceEncoder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.stream.IntStream;

public class Main_Aula20_CisalhamentoHorizontal {


    public static void main(String[] args) throws IOException {

        var sai = SistemaArquivoImagens.getInstance();
        Imagem imgOriginal = new ImagemAjustadaTela(sai.getDirBaseImagensEntrada("cafe1.jpg"), 0.2);

        File arquivoVideo = new File(sai.getDirBaseImagensSaida("cisalhamentoHorizontal-1.mp4"));
        var enc = AWTSequenceEncoder.createSequenceEncoder(arquivoVideo, 25);

        final double multiplicador = 2.0;
        int[] totalFrames = {0};
        MatrizTransformacaoCisalhamentoHorizontal mtch = new MatrizTransformacaoCisalhamentoHorizontal();

        System.out.printf("%s - Iniciando processamento.\n", new Date());
        IntStream.range(-400, 400).mapToDouble(i->i/100.0).forEach(escalaCisalhamentoY -> {
                    System.out.printf("%s - Processando cisalhamento Horizontal %.3f\n", new Date(), escalaCisalhamentoY);
                    totalFrames[0]++;
                    TransformadorEspacialImagem tei = new TransformadorEspacialImagem(imgOriginal);
                    tei.aplicaTransformacao(
                                    mtch.setEscalaY(escalaCisalhamentoY),
                                    multiplicador, multiplicador,
                                    (-imgOriginal.getLargura() / 2), (-imgOriginal.getAltura() / 2),
                                    (int) (-imgOriginal.getLargura() * (multiplicador / 2.0)), (int) (-imgOriginal.getAltura() * (multiplicador / 2.0)))
                            .setColor(Color.RED)
                            .setFonte(new Font("Arial", Font.BOLD, 18))
                            .escreveTexto("Esc. Cisalhamento = %.2f".formatted(escalaCisalhamentoY), 10, 30);

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

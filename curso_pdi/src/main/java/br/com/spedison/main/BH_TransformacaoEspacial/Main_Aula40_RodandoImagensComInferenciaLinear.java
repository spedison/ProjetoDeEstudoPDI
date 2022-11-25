package br.com.spedison.main.BH_TransformacaoEspacial;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.InferenciaPonto;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.*;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.TransformadorEspacialImagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoRotacao;
import org.jcodec.javase.api.awt.AWTSequenceEncoder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.stream.IntStream;

public class Main_Aula40_RodandoImagensComInferenciaLinear {


    public static void main(String[] args) throws IOException {

        // InferenciaPonto ip = new InferePontoCubica(); // InferenciaPontoBilinearV2();
        InferenciaPonto ip = new InferenciaPontoCubica();
        // InferenciaPonto ip = new InferenciaPontoBilinearV1();
        // InferenciaPonto ip = new InferenciaPontoBilinearV2();
        // InferenciaPonto ip = new InferenciaPontoBilinearV3();
        // InferenciaPonto ip = new InferenciaPontoMaisProximo();


        String nomeArquivoVideoSaida = "rotacoes_InferenciaCubica_V5.mp4";

        var sai = SistemaArquivoImagens.getInstance();
        // Imagem imgOriginal = new Imagem(sai.getDirBaseImagensEntrada("cafe1.jpg")); //  new ImagemAjustadaTela(sai.getDirBaseImagensEntrada("cafe1.jpg"), 1.5);
        // Imagem imgOriginal = new Imagem(sai.getDirBaseImagensEntrada("cafe1.jpg")); //  new ImagemAjustadaTela(sai.getDirBaseImagensEntrada("cafe1.jpg"), 1.5);
        Imagem imgOriginal = new ImagemAjustadaTela(sai.getDirBaseImagensEntrada("cafe1.jpg"), 0.53);

        File arquivoVideoSemInferencia = new File(sai.getDirBaseImagensSaida(nomeArquivoVideoSaida));
        var fps = 25;
        var encSaida = AWTSequenceEncoder.createSequenceEncoder(arquivoVideoSemInferencia, fps);

        final double multiplicador = 1.05;
        int[] totalFrames = {0};
        MatrizTransformacaoRotacao mtr = new MatrizTransformacaoRotacao();
        System.out.printf("%s - Iniciando processamento.\n", new Date());


        IntStream.range(0, 3600).mapToDouble(i -> i / 10.0).limit(50).forEach(anguloRotacaoGraus -> {
                    System.out.printf("%s - Processando ângulo %f\n", new Date(), anguloRotacaoGraus);
                    totalFrames[0]++;

                    TransformadorEspacialImagem tei = new TransformadorEspacialImagem(imgOriginal);
                    tei.aplicaTransformacao(
                                    mtr.setAnguloGraus(anguloRotacaoGraus),
                                    multiplicador, multiplicador,
                                    (-imgOriginal.getLargura() / 2), (-imgOriginal.getAltura() / 2),
                                    (int) (-imgOriginal.getLargura() * (multiplicador / 2.0)), (int) (-imgOriginal.getAltura() * (multiplicador / 2.0)),ip)
                            .setColor(Color.RED)
                            .setFonte(new Font("Arial", Font.BOLD, 20))
                            .escreveTexto("Angulo = %.3f".formatted(anguloRotacaoGraus), 10, 30);

                    try {
                        encSaida.encodeImage(tei.getImageBuffer());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        encSaida.finish();
        System.out.printf("%s - Processamento foi terminado com %d frames.\n\n", new Date(), totalFrames[0]);
    }

}

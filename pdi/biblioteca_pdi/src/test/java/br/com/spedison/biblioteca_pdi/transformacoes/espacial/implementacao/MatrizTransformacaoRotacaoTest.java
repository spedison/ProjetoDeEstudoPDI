package br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao;

import br.com.spedison.biblioteca_pdi.auxiliar.Arredondador;
import br.com.spedison.biblioteca_pdi.auxiliar.Downloader;
import br.com.spedison.biblioteca_pdi.auxiliar.MD5;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.InferenciaPonto;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.ResultadoInferencia;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.*;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.TransformadorEspacialImagem;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.util.stream.IntStream;

public class MatrizTransformacaoRotacaoTest {

    @Test
    public void calculaDireta() {
        MatrizTransformacaoRotacao mr = new MatrizTransformacaoRotacao();

        for (double angulo = 0.0; angulo <= 360.0; angulo += 0.1) {
            int xOrigem = 10;
            int yOrigem = 0;
            mr.setAnguloGraus(angulo);
            double[] pDestino = mr.calculaPontoDireto((double) xOrigem, (double) yOrigem);
            double raio = Math.sqrt(Math.pow(pDestino[0], 2.0) + Math.pow(pDestino[1], 2.0));
            System.out.printf("Angulo = %f, X = %f, Y = %f, Raio = %f\n", angulo, pDestino[0], pDestino[1], raio);
            Assert.assertEquals(raio, 10., 1E-6);
        }
    }

    @Test
    public void roda21Graus() {
        MatrizTransformacaoRotacao mr = new MatrizTransformacaoRotacao(21);
        InferenciaPonto inferePontoBilinear = new InferenciaPontoBilinearV1();
        InferenciaPonto inferePontoCubica = new InferenciaPontoCubica();
        TransformadorEspacialImagem imgRotacionadaComInferenciaBilinear = new TransformadorEspacialImagem(1000, 1000);
        imgRotacionadaComInferenciaBilinear.pintaFundo(Color.WHITE.getRGB());
        imgRotacionadaComInferenciaBilinear
                .setFonte(new Font("Arial", Font.BOLD, 250))
                .setColor(Color.RED)
                .escreveTexto("Teste 123", 1, 250)
                .salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-sem-rotacao.bmp"));

        // Fiz uma cópia (Sem Inferencia)
        var imgRotacionadaSemInferencia = new TransformadorEspacialImagem(imgRotacionadaComInferenciaBilinear);

        // Fiz uma cópia (Com Inferencia Cúbica)
        var imgRotacionadaComInferenciaCublica = new TransformadorEspacialImagem(imgRotacionadaComInferenciaBilinear);

        imgRotacionadaSemInferencia.aplicaTransformacao(mr, 2, 2, -50, -50, -100, -100);
        imgRotacionadaSemInferencia.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-rotacionado-sem-inferencia.bmp"));

        imgRotacionadaComInferenciaBilinear.aplicaTransformacao(mr, 2, 2, -50, -50, -100, -100, inferePontoBilinear);
        imgRotacionadaComInferenciaBilinear.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-rotacionado-com-inferencia-implementacoes.bmp"));

        imgRotacionadaComInferenciaCublica.aplicaTransformacao(mr, 2, 2, -50, -50, -100, -100, inferePontoCubica);
        imgRotacionadaComInferenciaCublica.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-rotacionado-com-inferencia-cubica.bmp"));
    }

    final static String[] arquivos = {
            SistemaArquivoImagens.getInstance().getDirBaseImagensEntrada("teste-cafe_download.jpg"),
            SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-rotacionado-sem-inferencia.bmp"),
            SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-rotacionado-com-inferencia-bilinear_1.bmp"),
            SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-rotacionado-com-inferencia-bilinear_2.bmp"),
            SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-rotacionado-com-inferencia-bilinear_3.bmp"),
            SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-rotacionado-com-inferencia-cubica.bmp")
    };

    final static InferenciaPonto[] inferencias = new InferenciaPonto[]{
            null,
            new InferenciaPontoMaisProximo(),
            new InferenciaPontoBilinearV1(),
            new InferenciaPontoBilinearV2(),
            new InferenciaPontoBilinearV3(),
            new InferenciaPontoCubica()
    };

    final static String[] md5Hash = {
            null,
            "6A6AC6631DF101AE16F38319121A601A",
            "A07083D28AFCDD50C4BAF134C27774C8",
            "56380B09974CC27540DEAB90BA7139CC",
            "BEC11DAE7FCDC880173C406084EB0052",
            "BB7C6B2CA600F918D14603B3336779E8"
    };
    final static String urlDownloadArquivo = "https://github.com/spedison/ImagensDeTeste/raw/main/cafe1.jpg";

    @Test
    public void roda21GrausCafe() {

        double angulo = 21;
        double multiplicador = 1.0;

        Downloader.downloadFile(urlDownloadArquivo, arquivos[0]);

        MatrizTransformacaoRotacao mr = new MatrizTransformacaoRotacao(angulo);
        IntStream.range(1, arquivos.length).forEach(
                teste -> {
                    TransformadorEspacialImagem imgRotacionadaSemInferencia = new TransformadorEspacialImagem(arquivos[0]);

                    var origemXImagemOrigem = -(imgRotacionadaSemInferencia.getLargura() / 2);
                    var origemYImagemOrigem = -(imgRotacionadaSemInferencia.getAltura() / 2);

                    var origemXImagemDestino = (int) -(imgRotacionadaSemInferencia.getLargura() * multiplicador / 2.0);
                    var origemYImagemDestino = (int) -(imgRotacionadaSemInferencia.getAltura() * multiplicador / 2.0);

                    var arquivoSaida = SistemaArquivoImagens.getInstance()
                            .getDirBaseImagensSaida(
                                    "teste-rotacionado-21-graus___%s__.bmp".formatted(
                                            inferencias[teste].getNome()
                                    ));

                    imgRotacionadaSemInferencia.aplicaTransformacao(mr, multiplicador, multiplicador,
                            origemXImagemOrigem, origemYImagemOrigem, origemXImagemDestino, origemYImagemDestino, inferencias[teste]);
                    imgRotacionadaSemInferencia.salva(arquivoSaida);
                    try {
                        Assert.assertEquals(
                                "Erro ao processar Inferencia - %s"
                                        .formatted(inferencias[teste].getNome()),
                                md5Hash[teste],
                                MD5.calculaMD5Arquivo(arquivoSaida));
                        System.out.println("Processando inferencia : " + inferencias[teste].getNome());
                    } finally {
                        // Apaga arquivo gerado....
                        new File(arquivoSaida).delete();
                    }
                }
        );
        new File(arquivos[0]).delete();
    }

    @Test
    public void roda21GrausTestaIdaEVolta() {

        IntStream
                .range(-50, 50)
                .forEach(angulo -> {
                    MatrizTransformacaoRotacao mr = new MatrizTransformacaoRotacao(angulo);
                    IntStream
                            .range(0, 700)
                            .parallel()
                            .forEach(x -> {
                                IntStream
                                        .range(0, 700)
                                        .forEach(y -> {
                                                    double[] ret = mr.calculaPontoInverso((double) x, (double) y);
                                                    double[] retOriginal = mr.calculaPontoDireto(ret[0], ret[1]);
                                                    Assert.assertEquals(Arredondador.arredonda(retOriginal[0]), x);
                                                    Assert.assertEquals(Arredondador.arredonda(retOriginal[1]), y);
                                                }
                                        );
                            });
                });
    }
}
package br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao;

import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.InferenciaPonto;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.InferenciaPontoBilinearV1;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.TransformadorEspacialImagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.InferenciaPontoCubica;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.stream.IntStream;

public class MatrizTransformacaoRotacaoTest {

    @Test
    public void calculaDireta() {
        MatrizTransformacaoRotacao mr = new MatrizTransformacaoRotacao();

        for (double angulo = 0.0; angulo <= 360.0; angulo += 0.1) {
            int xOrigem = 10;
            int yOrigem = 0;
            mr.setAnguloGraus(angulo);
            int[] pDestino = mr.calculaPontoDireto(xOrigem, yOrigem);
            double raio = Math.sqrt(Math.pow(pDestino[0], 2.0) + Math.pow(pDestino[1], 2.0));
            System.out.printf("Angulo = %f, X = %d, Y = %d, Raio = %f\n", angulo, pDestino[0], pDestino[1], raio);
            Assert.assertEquals(raio, 10, 2);
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

    @Test
    public void roda21GrausCafe() {

        double angulo = 21;
        double multiplicador = 1.0;

        MatrizTransformacaoRotacao mr = new MatrizTransformacaoRotacao(angulo);
        InferenciaPonto inferePontoBilinear = new InferenciaPontoBilinearV1();
        InferenciaPonto inferePontoCubica = new InferenciaPontoCubica();
        TransformadorEspacialImagem imgRotacionadaComInferenciaBilinear = new TransformadorEspacialImagem(SistemaArquivoImagens.getInstance().getDirBaseImagensEntrada("cafe1.jpg"));
        /*imgRotacionadaComInferenciaBilinear.pintaFundo(Color.WHITE.getRGB());
        imgRotacionadaComInferenciaBilinear
                .setFonte(new Font("Arial", Font.BOLD, 250))
                .setColor(Color.RED)
                .escreveTexto("Teste 123", 1, 250)*/
        imgRotacionadaComInferenciaBilinear.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-sem-rotacao.bmp"));

        var origemXImagemOrigem = -(imgRotacionadaComInferenciaBilinear.getLargura() / 2);
        var origemYImagemOrigem = -(imgRotacionadaComInferenciaBilinear.getAltura() / 2);

        var origemXImagemDestino = (int) -(imgRotacionadaComInferenciaBilinear.getLargura() * multiplicador / 2.0);
        var origemYImagemDestino = (int) -(imgRotacionadaComInferenciaBilinear.getAltura() * multiplicador / 2.0);

        // Fiz uma cópia (Sem Inferencia)
        var imgRotacionadaSemInferencia = new TransformadorEspacialImagem(imgRotacionadaComInferenciaBilinear);

        // Fiz uma cópia (Com Inferencia Cúbica)
        var imgRotacionadaComInferenciaCublica = new TransformadorEspacialImagem(imgRotacionadaComInferenciaBilinear);

        imgRotacionadaSemInferencia.aplicaTransformacao(mr, multiplicador, multiplicador, origemXImagemOrigem, origemYImagemOrigem, origemXImagemDestino, origemYImagemDestino);
        imgRotacionadaSemInferencia.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-rotacionado-sem-inferencia.bmp"));

        imgRotacionadaComInferenciaBilinear.aplicaTransformacao(mr, multiplicador, multiplicador,
                origemXImagemOrigem, origemYImagemOrigem, origemXImagemDestino, origemYImagemDestino, inferePontoBilinear);
        imgRotacionadaComInferenciaBilinear.salva(
                SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-rotacionado-com-inferencia-implementacoes.bmp"));

        imgRotacionadaComInferenciaCublica.aplicaTransformacao(mr, multiplicador, multiplicador,
                origemXImagemOrigem, origemYImagemOrigem, origemXImagemDestino, origemYImagemDestino, inferePontoCubica);
        imgRotacionadaComInferenciaCublica.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste-rotacionado-com-inferencia-cubica.bmp"));
    }

    @Test
    public void roda21GrausTestaIdaEVolta() {
        MatrizTransformacaoRotacao mr = new MatrizTransformacaoRotacao(21);

        final int[] pontoOk = {0};
        final int[] pontoArredondados = {0};

        IntStream.range(0, 101).forEach(x -> {
            IntStream.range(0, 101).forEach(y -> {
                        double[] ret = mr.calculaPontoInverso(x, y);
                        int[] retOriginal = mr.calculaPontoDireto(ret[0], ret[1]);
                        if (retOriginal[0] == x && retOriginal[1] == y) {
                            System.out.printf("Ponto (%d,%d) tem ida e volta OK.\n", x, y);
                            pontoOk[0]++;
                        } else {
                            System.out.printf("Ponto (%d,%d) tem ida e volta diferente (%d,%d).\n", x, y, retOriginal[0], retOriginal[1]);
                            pontoArredondados[0]++;
                        }
                    }
            );
        });
        System.out.println("pontoArredondados = " + pontoArredondados[0]);
        System.out.println("pontoOk = " + pontoOk[0]);

    }

}
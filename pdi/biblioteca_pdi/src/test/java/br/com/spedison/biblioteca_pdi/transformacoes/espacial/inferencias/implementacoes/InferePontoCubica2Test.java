package br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes;

import br.com.spedison.biblioteca_pdi.GeradorImagensParaTeste;
import br.com.spedison.biblioteca_pdi.PointCompare;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.MatrizIntImagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.ResultadoInferencia;
import junit.framework.TestCase;
import org.junit.Assert;

import java.awt.*;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

public class InferePontoCubica2Test extends TestCase {

    static final double passosDadosNaImagemPorPontoParaTeste = 100.0D;

    boolean testeDaCriacaoPontos(Point[] pts, Imagem i, final double pontoX, final double pontoY) {

        // Tamanho OK
        Assert.assertEquals("Quantidade de pontos está incorreta. Deve ser 16 atualmente é "
                .formatted(pts.length), pts.length, 16);

        // Todos os pontos devem ser válidos
        Arrays.stream(pts).forEach(p ->
                Assert.assertTrue("Ponto inválido(fora da imagem) eh (%d;%d)"
                        .formatted(p.x, p.y), i.isPontoValidoInferencia(p.x, p.y)));

        // Não deve haver pontos repetidos
        assertEquals("Não pode haver pontos repetidos.", pts.length,
                Arrays.stream(pts).map(PointCompare::new).distinct().count()
        );

        // Nenhum ponto deve ter uma distância maior que 3*SQRT(2)
        Arrays
                .stream(pts)
                .map(PointCompare::new)
                .forEach(p ->
                {
                    Assert.assertTrue("Exite pontos com distância maior que 3*sqrt(2) ponto (%d;%d) de (%f;%f)"
                                    .formatted(p.x, p.y, pontoX, pontoY),
                            p.dist(pontoX, pontoY) <= 3. * Math.sqrt(2.));
                });
        return true;
    }


    public void testCriaPontos2() {
        Imagem i = GeradorImagensParaTeste.criaImagemPadraoInferencia1();

        InferenciaPontoCubica ic = new InferenciaPontoCubica();
        double x = 4.990D;
        double y = 4.990D;
        System.out.println("Criando pontos : %f : %f".formatted(x, y));
        ic.setImagem(i);
        Point[] pts = ic.criaPontos(x, y);
        System.out.println(Arrays.toString(pts));

        x = -4.990D;
        y = -4.990D;

        System.out.println("Criando pontos : %f : %f".formatted(x, y));
        ic.setImagem(i);
        pts = ic.criaPontos(x, y);
        System.out.println(Arrays.toString(pts));

    }

    public void testCriaPontos() {

        Imagem i = GeradorImagensParaTeste.criaImagemPadraoInferencia1();
        InferenciaPontoCubica ic = new InferenciaPontoCubica();

        IntStream
                .rangeClosed(i.getOrigemX() * (int) passosDadosNaImagemPorPontoParaTeste,
                        i.getLimiteX() * (int) passosDadosNaImagemPorPontoParaTeste)
                .parallel()
                .mapToDouble(valInt -> ((double) valInt) / passosDadosNaImagemPorPontoParaTeste)
                .forEach(x -> {
                    IntStream
                            .rangeClosed(i.getOrigemY() * (int) passosDadosNaImagemPorPontoParaTeste,
                                    i.getLimiteY() * (int) passosDadosNaImagemPorPontoParaTeste)
                            .mapToDouble(valInt -> ((double) valInt) / passosDadosNaImagemPorPontoParaTeste)
                            .forEach(y -> {
                                System.out.println("Criando pontos : %f : %f".formatted(x, y));
                                ic.setImagem(i);
                                Point[] pts = ic.criaPontos(x, y);
                                try {
                                    testeDaCriacaoPontos(pts, i, x, y);
                                } catch (AssertionError e) {
                                    e.printStackTrace();
                                    System.out.println(Arrays.toString(pts));
                                    throw e;
                                }
                            });
                });
    }

    public void testInfere() {

        Imagem img = GeradorImagensParaTeste.criaImagemPadraoInferencia2();

        MatrizIntImagem imgPring = new MatrizIntImagem(img);

        System.out.println(imgPring.imprimeRgb(Imagem.Vermelho));

        System.out.println(img.toString());

        final InferenciaPontoCubica inferenciaCubica = new InferenciaPontoCubica();
        ResultadoInferencia ri2 = new ResultadoInferencia();
        inferenciaCubica.setImagem(img);


        IntStream
                .rangeClosed(img.getOrigemX() * (int) passosDadosNaImagemPorPontoParaTeste,
                        img.getLimiteX() * (int) passosDadosNaImagemPorPontoParaTeste)
                .mapToDouble(x -> x / passosDadosNaImagemPorPontoParaTeste)
                .filter(x -> x > (double) img.getOrigemX() && x < (double) img.getLimiteX())
                .forEach(xInfere ->
                        IntStream
                                .rangeClosed(img.getOrigemY() * (int) passosDadosNaImagemPorPontoParaTeste,
                                        img.getLimiteY() * (int) passosDadosNaImagemPorPontoParaTeste)
                                .mapToDouble(x -> x / passosDadosNaImagemPorPontoParaTeste)
                                .filter(x -> x > (double) img.getOrigemY() && x < (double) img.getLimiteY() / passosDadosNaImagemPorPontoParaTeste)
                                .forEach(yInfere -> {
                                            ResultadoInferencia ri = new ResultadoInferencia();
                                            inferenciaCubica.setImagem(img);
                                            inferenciaCubica.inferePonto(xInfere, yInfere, ri);
                                            try {
                                                Point[] pontos = inferenciaCubica.criaPontos(xInfere, yInfere);
                                                IntSummaryStatistics iss =
                                                        Arrays
                                                                .stream(pontos)
                                                                .mapToInt(point -> img.getPixel(point.x, point.y)[Imagem.Vermelho])
                                                                .summaryStatistics();
                                                int vpMax = iss.getMax();
                                                int vpMin = iss.getMin();
                                                try {
                                                    Assert.assertTrue(ri.getValorVermelho() >= vpMin && ri.getValorVermelho() <= vpMax);
                                                    Assert.assertTrue(ri.isProcessado());
                                                } catch (AssertionError se) {
                                                    System.out.printf("Erro com o ponto: (%f;%f)\n",
                                                            xInfere,
                                                            yInfere);
                                                    System.out.println("Pontos usados = " + Arrays.toString(pontos));
                                                    System.out.printf("ri.getVermelho = %d ; Valor Float = %f ",
                                                            ri.getValorVermelho(),
                                                            ri.getValorVermelhoDouble());
                                                    System.out.println("Valor Mínimo = %d e Valor Máximo = %d"
                                                            .formatted(vpMin, vpMax));
                                                    System.out.println("-------------");
                                                    se.printStackTrace();
                                                    throw se;
                                                }
                                            } catch (ArrayIndexOutOfBoundsException aioob) {
                                                System.out.println("Problemas ao acessar os pontos da imagem");
                                                System.out.println("Ponto processado (%f;%f)".formatted(xInfere, yInfere));
                                                aioob.printStackTrace();
                                                throw aioob;
                                            }
                                        }
                                )
                );

    }
}
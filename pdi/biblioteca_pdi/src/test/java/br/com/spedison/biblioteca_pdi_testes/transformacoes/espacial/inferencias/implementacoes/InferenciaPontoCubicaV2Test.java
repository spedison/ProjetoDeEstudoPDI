package br.com.spedison.biblioteca_pdi_testes.transformacoes.espacial.inferencias.implementacoes;

import br.com.spedison.biblioteca_pdi_testes.GeradorImagensParaTeste;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.padroes.ImagemPadraoFaixaDupla;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.InferenciaPonto;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.ResultadoInferencia;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.InferenciaPontoCubica;
import br.com.spedison.biblioteca_pdi_testes.GeradorImagensParaTeste;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

public class InferenciaPontoCubicaV2Test {

    final static double passosDadosNaImagemPorPontoParaTeste = 100.0D;

    @Test
    public void verificaFloor() {
        Assert.assertEquals(-2., Math.floor(-1.45), 1E-6);
        Assert.assertEquals(-3., Math.floor(-2.45), 1E-6);
        Assert.assertEquals(-10., Math.floor(-9.75), 1E-6);

        Assert.assertEquals(1., Math.floor(1.45), 1E-6);
        Assert.assertEquals(2., Math.floor(2.45), 1E-6);
        Assert.assertEquals(9., Math.floor(9.75), 1E-6);

        Assert.assertEquals(-6., Math.floor(-4.42) - 1, 1E-6);


    }

    @Test
    public void testaInferencia() {
        Imagem img = GeradorImagensParaTeste.criaImagemPadraoInferencia2();

        InferenciaPontoCubica inferenciaPonto = new InferenciaPontoCubica();
        inferenciaPonto.setImagem(img);

        final boolean[] continuaProcessamento = {true};
        final int[] contaTestes = {0};

        // Gera pontos (double) para passear por toda a imagem em X.
        IntStream
                .rangeClosed((int) (img.getOrigemX() * passosDadosNaImagemPorPontoParaTeste), (int) (img.getLimiteX() * passosDadosNaImagemPorPontoParaTeste))
                .mapToDouble(x -> x / passosDadosNaImagemPorPontoParaTeste)
                .filter(k -> continuaProcessamento[0])
                .forEach(x -> {
                    // Gera pontos (double) para passear pela imagem em Y
                    IntStream
                            .rangeClosed((int) (img.getOrigemY() * passosDadosNaImagemPorPontoParaTeste), (int) (img.getLimiteY() * passosDadosNaImagemPorPontoParaTeste))
                            .mapToDouble(y -> y / passosDadosNaImagemPorPontoParaTeste)
                            .filter(k -> continuaProcessamento[0])
                            .forEach(y -> {
                                Point[] pontos = inferenciaPonto.criaPontos(x, y);
                                testaInferencias(img, inferenciaPonto, x, y, pontos);
                                continuaProcessamento[0] = true; // Podemos mudar o critério para testes dos pontos.

                                contaTestes[0]++;
                                if (contaTestes[0] % 100_000 == 0) {
                                    System.out.println("Testando ponto (%f;%f)".formatted(x, y));
                                }
                            });
                });
    }


    private void testaInferencias(Imagem img, InferenciaPonto inferenciaPonto, double xInfere, double yInfere, Point[] pontos) {

        ResultadoInferencia ri = new ResultadoInferencia();
        inferenciaPonto.setImagem(img);
        inferenciaPonto.inferePonto(xInfere, yInfere, ri);

        //if (!ri.isProcessado())
        //    return;

        IntSummaryStatistics issVermelho =
                Arrays
                        .stream(pontos)
                        .mapToInt(p -> img.getPixel(p.x, p.y)[Imagem.Vermelho])
                        .summaryStatistics();

        IntSummaryStatistics issVerde =
                Arrays
                        .stream(pontos)
                        .mapToInt(p -> img.getPixel(p.x, p.y)[Imagem.Verde])
                        .summaryStatistics();

        IntSummaryStatistics issAzul =
                Arrays
                        .stream(pontos)
                        .mapToInt(p -> img.getPixel(p.x, p.y)[Imagem.Azul])
                        .summaryStatistics();

        try {
            Assert.assertTrue(
                    "Inferência do vermelho ponto (%f;%f) => Min = %d, Max = %d, valor = %d, Processado = %s".formatted(
                            xInfere,
                            yInfere,
                            issVermelho.getMin(),
                            issVermelho.getMax(),
                            ri.getValorVermelho(),
                            ri.isProcessado() ? "Processado" : "Não Processado"),
                    ri.getValorVermelho() >= issVermelho.getMin() && ri.getValorVermelho() <= issVermelho.getMax());

            Assert.assertTrue(
                    "Inferência do verde ponto (%f,%f) => Min = %d, Max = %d, valor = %d".formatted(
                            xInfere,
                            yInfere,
                            issVerde.getMin(),
                            issVerde.getMax(),
                            ri.getValorVerde()),
                    ri.getValorVerde() >= issVerde.getMin() && ri.getValorVerde() <= issVerde.getMax());

            Assert.assertTrue(
                    "Inferência do azul ponto (%f,%f) => Min = %d, Max = %d, valor = %d".formatted(
                            xInfere,
                            yInfere,
                            issAzul.getMin(),
                            issAzul.getMax(),
                            ri.getValorAzul()),
                    ri.getValorAzul() >= issAzul.getMin() && ri.getValorAzul() <= issAzul.getMax());

            Assert.assertTrue(ri.isProcessado());
        } catch (AssertionError se) {
            System.out.println("Erro com o ponto: " + Arrays.toString(ri.getPonto()));
            se.printStackTrace();
            throw se;
        }

    }


    @Test
    public void testaCriacaoPontos() {

        ImagemPadraoFaixaDupla img = new ImagemPadraoFaixaDupla(11, 11);
        img.setTipoFaixa(ImagemPadraoFaixaDupla.TipoFaixa.Xadrez);
        img.setCorFaixa1(new int[]{255, 0, 0});
        img.setCorFaixa2(new int[]{255, 255, 255});
        img.setLarguraFaixa1(2);
        img.setLarguraFaixa2(2);
        img.geraPadrao();
        img.setOrigemX(-5);
        img.setOrigemY(-5);


        InferenciaPontoCubica inferenciaCubica = new InferenciaPontoCubica();
        inferenciaCubica.setImagem(img);

        final boolean[] continuaProcessamento = {true};
        final int[] contaTestes = {0};

        // Gera pontos (double) para passear por toda a imagem em X.
        IntStream
                .rangeClosed(img.getOrigemX() * (int) passosDadosNaImagemPorPontoParaTeste, img.getLimiteX() * (int) passosDadosNaImagemPorPontoParaTeste)
                .mapToDouble(x -> x / passosDadosNaImagemPorPontoParaTeste)
                .filter(k -> continuaProcessamento[0])
                .forEach(x -> {
                    // Gera pontos (double) para passear pela imagem em Y
                    IntStream
                            .rangeClosed(img.getOrigemY() * 100, img.getLimiteY() * 100)
                            .mapToDouble(y -> y / 100.0)
                            .filter(k -> continuaProcessamento[0])
                            .forEach(y -> {

                                // ResultadoInferencia ri = new ResultadoInferencia();
                                // Cria os pontos a partir da imagem que esteja ao refor do ponto em formato decimal.
                                Point[] pontos = inferenciaCubica.criaPontos(x, y);
                                Arrays
                                        .stream(pontos)
                                        .map(p ->
                                                new double[]{
                                                        x,
                                                        y,
                                                        p.x,
                                                        p.y,
                                                        Math.sqrt(
                                                                Math.pow(p.x - x, 2.0)
                                                                        + Math.pow(p.y - y, 2.0))})
                                        .map(pD -> {
                                            try {
                                                /**
                                                 * Cabe uma explicação do porquê usar o valor 3*sqrt(2).
                                                 * Apesar de serem usados 4 pontos, a distância entre eles é de 3.
                                                 * Como a maior distância de um ponto dentro de um quadrado é a diagonal
                                                 * e essa distância é o lado multiplicado por raiz de 2, temos 3 multiplicado
                                                 * por raiz de 2.
                                                 */
                                                Assert.assertTrue("Ponto criado com problema -> " +
                                                                "origem = (%f,%f), Ponto Avaliado = (x=%f;y=%f) Distância = %f\n".formatted(pD[0], pD[1], pD[2], pD[3], pD[4]),
                                                        pD[4] <= 3. * Math.sqrt(2));
                                                return "origem = (%f,%f), Ponto Avaliado = (x=%f;y=%f) Distância = %f\n".formatted(pD[0], pD[1], pD[2], pD[3], pD[4]);
                                            } catch (AssertionError ae) {
                                                ae.printStackTrace();
                                                throw ae; // Para os testes.
                                            }
                                        })
                                        .forEach(strProcessamento -> {
                                            contaTestes[0]++;
                                            if (contaTestes[0] % 100_000 == 0) { // Imprimime alguns pontos testados.
                                                System.out.println(strProcessamento);
                                            }
                                        });
                                continuaProcessamento[0] = true; // Podemos mudar o critério para testes dos pontos.
                            });
                });
    }
}
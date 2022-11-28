package br.com.spedison.biblioteca_pdi_testes.transformacoes.espacial.inferencias.implementacoes;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.MatrizIntImagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.ResultadoInferencia;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.InferenciaPontoBilinearV2;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InferePontoBiLinearTest {

    static final double multiplicador = 100.0;

    class PointCompare extends Point {

        double lastDistance = 0.0;
        public PointCompare(Point p) {
            this.x = p.x;
            this.y = p.y;
        }

        @Override
        public boolean equals(Object obj) {
            Point o = (PointCompare) obj;
            return o.x == this.x && o.y == this.y;
        }

        public double dist(double x, double y) {
            double dx = x - this.x;
            double dy = y - this.y;
            lastDistance = Math.sqrt((dx * dx) + (dy * dy));
            return lastDistance;
        }

        @Override
        public String toString() {
            return "PointCompare{" +
                    "lastDistance=" + lastDistance +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private boolean testeDaCriacaoPontos(Point[] pts, Imagem i, final double pontoX, final double pontoY) {

        // Tamanho OK
        assertEquals(pts.length, 4);

        // Todos os pontos devem ser válidos
        Arrays.stream(pts).forEach(p ->
                assertTrue("Ponto (%d,%d) Não é valido"
                                .formatted(p.x, p.y),
                        !i.isPontoInvalido(p.x, p.y)));

        // Não deve haver pontos repetidos
        assertEquals("Não existe 4 pontos únicos.", 4,
                Arrays.stream(pts).map(PointCompare::new).distinct().count()
        );

        PointCompare [] pc = Arrays.stream(pts).map(PointCompare::new).toArray(PointCompare[]::new);
        double [] dists = Arrays.stream(pc).mapToDouble(p->p.dist(pontoX, pontoY)).toArray();

        try {
            // Nenhum ponto deve ter uma distância maior que 4*SQRT(2)
            assertEquals("Nem todos os pontos de (%f;P%f) em (%s) tem a distância menor ou igual a sqrt(2)"
                            .formatted(pontoX, pontoY, Arrays.toString(pc)), 4,
                    Arrays
                            .stream(dists)
                            .filter(x -> x <=  Math.sqrt(2.))
                            .count());
        }catch (AssertionError ae){
            System.out.println("Pontos que apresentaram problemas. Em (%f;%f)".formatted(pontoX, pontoY));
            Arrays.stream(pc).forEach(System.out::println);
            throw ae;
        }
        return true;
    }

    private Imagem criaImagemPadrao() {
        Imagem i = new Imagem(11, 11);
        i.setOrigemX(-5);
        i.setOrigemY(-5);
        i.getStreamAltura().forEach(y ->
                i.getStreamLargura().forEach(x -> {
                    i.setPixel(x, y, new int[]{+10 + x + y, +10 + x + y, +10 + x + y});
                })
        );
        return i;
    }

    @Test
    public void testCriaPontos() {

        Imagem img = criaImagemPadrao();

        MatrizIntImagem imgPring = new MatrizIntImagem(img);

        System.out.println(imgPring.imprimeRgb(Imagem.Vermelho));

        System.out.println(img.toString());

        final InferenciaPontoBilinearV2 ic = new InferenciaPontoBilinearV2();
        ic.setImagem(img);


        final int[] contaTestes = {0};

        IntStream
                .range(img.getOrigemX() * (int) multiplicador, img.getLimiteX() * (int) multiplicador)
                .mapToDouble(x -> x / multiplicador)
                .filter(x -> x > (double) img.getOrigemX() && x < (double) img.getLimiteX())
                .forEach(xInfere ->
                        IntStream
                                .range(img.getOrigemY() * (int) multiplicador, img.getLimiteY() * (int) multiplicador)
                                .mapToDouble(x -> x / multiplicador)
                                .filter(x -> x > (double) img.getOrigemY() && x < (double) img.getLimiteY())
                                .forEach(yInfere -> {
                                    Point[] pontos = ic.criaPontos(xInfere, yInfere);
                                    testeDaCriacaoPontos(pontos, img, xInfere, yInfere);
                                    if (contaTestes[0]++ % 100_000 == 0) {
                                        System.out.println("Testando ponto (%f;%f)".formatted(xInfere, yInfere));
                                    }
                                })
                );
    }

    @Test
    public void testInfere() {

        Imagem img = criaImagemPadrao();

        MatrizIntImagem imgPring = new MatrizIntImagem(img);

        System.out.println(imgPring.imprimeRgb(Imagem.Vermelho));

        System.out.println(img.toString());

        final InferenciaPontoBilinearV2 ic = new InferenciaPontoBilinearV2();
        ic.setImagem(img);

        final int[] contaTestes = {0};

        IntStream
                .range(img.getOrigemX() * (int) multiplicador, img.getLimiteX() * (int) multiplicador)
                .mapToDouble(x -> x / multiplicador)
                .filter(x -> x > (double) img.getOrigemX() && x < (double) img.getLimiteX())
                .forEach(xInfere ->
                        IntStream
                                .range(img.getOrigemY() * (int) multiplicador, img.getLimiteY() * (int) multiplicador)
                                .mapToDouble(x -> x / multiplicador)
                                .filter(x -> x > (double) img.getOrigemY() && x < (double) img.getLimiteY())
                                .forEach(yInfere -> {
                                    ResultadoInferencia ri = new ResultadoInferencia();
                                    ic.inferePonto(xInfere, yInfere, ri);
                                    Point[] pontos = ic.criaPontos(xInfere, yInfere);
                                    IntSummaryStatistics issPontosVermelho =
                                            Arrays
                                                    .stream(pontos)
                                                    .mapToInt(p -> img.getPixel(p.x, p.y)[Imagem.Vermelho])
                                                    .summaryStatistics();
                                    int vpMaxVermelho = issPontosVermelho.getMax();
                                    int vpMinVermelho = issPontosVermelho.getMin();

                                    IntSummaryStatistics issPontosVerde =
                                            Arrays
                                                    .stream(pontos)
                                                    .mapToInt(p -> img.getPixel(p.x, p.y)[Imagem.Verde])
                                                    .summaryStatistics();
                                    int vpMaxVerde = issPontosVerde.getMax();
                                    int vpMinVerde = issPontosVerde.getMin();

                                    IntSummaryStatistics issPontosAzul =
                                            Arrays
                                                    .stream(pontos)
                                                    .mapToInt(p -> img.getPixel(p.x, p.y)[Imagem.Azul])
                                                    .summaryStatistics();
                                    int vpMaxAzul = issPontosAzul.getMax();
                                    int vpMinAzul = issPontosAzul.getMin();

                                    if (contaTestes[0]++ % 10_000 == 0) {
                                        System.out.println("ri.getPonto() = " + Arrays.toString(ri.getPonto()));
                                        System.out.println("Vermelho || Valor = %d ; Valor Mínimo = %d ; Valor Máximo = %d".formatted(ri.getValorVermelho(), vpMinVermelho, vpMaxVermelho));
                                        System.out.println("Verde    || Valor = %d ; Valor Mínimo = %d ; Valor Máximo = %d".formatted(ri.getValorVerde(), vpMinVerde, vpMaxVerde));
                                        System.out.println("Azul     || Valor = %d ; Valor Mínimo = %d ; Valor Máximo = %d".formatted(ri.getValorAzul(), vpMinAzul, vpMaxAzul));
                                        System.out.println("-------------");
                                    }
                                    try {
                                        Assert.assertTrue("Erro Vemelho : Valor fora dos limites. Valor = %d, Valor Mínimo = %d, Valor Máximo = %d"
                                                        .formatted(ri.getValorVermelho(), vpMinVermelho, vpMaxVermelho),
                                                ri.getValorVermelho() >= vpMinVermelho && ri.getValorVermelho() <= vpMaxVermelho);

                                        Assert.assertTrue("Erro Verde : Valor fora dos limites. Valor = %d, Valor Mínimo = %d, Valor Máximo = %d"
                                                        .formatted(ri.getValorVerde(), vpMinVerde, vpMaxVerde),
                                                ri.getValorVerde() >= vpMinVerde && ri.getValorVerde() <= vpMaxVerde);

                                        Assert.assertTrue("Erro Azul : Valor fora dos limites. Valor = %d, Valor Mínimo = %d, Valor Máximo = %d"
                                                        .formatted(ri.getValorVermelho(), vpMinVermelho, vpMaxVermelho),
                                                ri.getValorAzul() >= vpMinAzul && ri.getValorAzul() <= vpMaxAzul);

                                        assertEquals("Valor deveria ser igual Vemelho e Azul.", ri.getValorVermelho(), ri.getValorAzul());
                                        assertEquals("Valor deveria ser igual Verde e Azul.", ri.getValorVerde(), ri.getValorAzul());
                                        Assert.assertTrue("Não conseguiu processar o ponto.", ri.isProcessado());
                                    } catch (AssertionError se) {
                                        System.out.println("Erro com o ponto: " + Arrays.toString(ri.getPonto()));
                                        se.printStackTrace();
                                        throw se;
                                    }
                                })
                );
    }


    @Test
    public void testa1PontoCriacao(){

        Imagem img = criaImagemPadrao();

        final InferenciaPontoBilinearV2 ic = new InferenciaPontoBilinearV2();
        ic.setImagem(img);

        double xInfere = -4.99;
        double yInfere = 4.65;
        Point[] pontos = ic.criaPontos(xInfere, yInfere);
        testeDaCriacaoPontos(pontos, img, xInfere, yInfere);

        xInfere = -4.99;
        yInfere = -4.99;
        pontos = ic.criaPontos(xInfere, yInfere);
        testeDaCriacaoPontos(pontos, img, xInfere, yInfere);
        xInfere = 3.0;
        yInfere = 3.0;
        pontos = ic.criaPontos(xInfere, yInfere);
        testeDaCriacaoPontos(pontos, img, xInfere, yInfere);
    }
}
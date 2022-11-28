package br.com.spedison.biblioteca_pdi_testes.transformacoes.espacial.inferencias.implementacoes;

/*
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.MatrizIntImagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.ResultadoInferencia;
import junit.framework.TestCase;
import org.junit.Assert;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;


class PointCompare extends Point {
    public PointCompare(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    @Override
    public boolean equals(Object obj) {
        Point o = (InferePontoCubicaTest.PointCompare) obj;
        return o.x == this.x && o.y == this.y;
    }

    public double dist(double x, double y) {
        double dx = Math.abs(x - this.x);
        double dy = Math.abs(y - this.y);
        return Math.sqrt(dx * dx + dy * dy);
    }
}
public class InferePontoCubicaTest extends TestCase {


    boolean testeDaCriacaoPontos(Point[] pts, Imagem i, final double pontoX, final double pontoY) {

        // Tamanho OK
        Assert.assertEquals(pts.length, 16);

        // Todos os pontos devem ser válidos
        Arrays.stream(pts).forEach(p ->
                Assert.assertEquals(true, !i.isPontoInvalido(p.x, p.y)));

        // Não deve haver pontos repetidos
        assertEquals(16,
                Arrays.stream(pts).map(PointCompare::new).distinct().count()
        );

        // Nenhum ponto deve ter uma distância maior que 4*SQRT(2)
        Assert.assertEquals(16,
                Arrays
                        .stream(pts)
                        .map(PointCompare::new)
                        .mapToDouble(p -> p.dist(pontoX, pontoY))
                        .filter(x -> x <= 4 * Math.sqrt(2.0))
                        .count());
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

    public void testCriaPontos() {

        Imagem i = criaImagemPadrao();

        InferePontoCubica ic = new InferePontoCubica();

        var px = -.1;
        var py = .1;

        Point[] pts = ic.criaPontos(px, py, i);
        try {
            testeDaCriacaoPontos(pts, i, px, py);
        } catch (AssertionError e) {
            System.out.println(Arrays.toString(pts));
            throw e;
        }

        //System.out.println(Arrays.toString(pts));

        px = -1.1;
        py = 1.1;
        pts = ic.criaPontos(px, py, i);
        //System.out.println(Arrays.toString(pts));
        try {
            testeDaCriacaoPontos(pts, i, px, py);
        } catch (AssertionError e) {
            System.out.println(Arrays.toString(pts));
            throw e;
        }

        px = -2.0;
        py = 1.1;
        pts = ic.criaPontos(px, py, i);
        //System.out.println(Arrays.toString(pts));
        try {
            testeDaCriacaoPontos(pts, i, px, py);
        } catch (AssertionError e) {
            System.out.println(Arrays.toString(pts));
            throw e;
        }


        px = 2.1;
        py = 2.1;
        pts = ic.criaPontos(px, py, i);
        //System.out.println(Arrays.toString(pts));
        try {
            testeDaCriacaoPontos(pts, i, px, py);
        } catch (AssertionError e) {
            System.out.println(Arrays.toString(pts));
            throw e;
        }

        px = 7.1;
        py = 6.1;
        pts = ic.criaPontos(px, py, i);
        //System.out.println(Arrays.toString(pts));
        try {
            testeDaCriacaoPontos(pts, i, px, py);
            Assert.assertTrue(false);
        } catch (AssertionError e) {
            System.out.println(Arrays.toString(pts));
            Assert.assertTrue(true);
            System.out.println("Ok, esses pontos estão fora do limite da imagem.");
        }

        px = 4.3;
        py = 4.4;
        pts = ic.criaPontos(px, py, i);
        //System.out.println(Arrays.toString(pts));
        try {
            testeDaCriacaoPontos(pts, i, px, py);
        } catch (AssertionError e) {
            System.out.println(Arrays.toString(pts));
            throw e;
        }


        px = -.8;
        py = -1.4;
        pts = ic.criaPontos(px, py, i);
        //System.out.println(Arrays.toString(pts));
        try {
            testeDaCriacaoPontos(pts, i, px, py);
        } catch (AssertionError e) {
            System.out.println(Arrays.toString(pts));
            throw e;
        }

        px = 8.;
        py = 8.;
        pts = ic.criaPontos(px, py, i);
        //System.out.println(Arrays.toString(pts));
        try {
            testeDaCriacaoPontos(pts, i, px, py);
            Assert.assertTrue(false);
        } catch (AssertionError e) {
            System.out.println(Arrays.toString(pts));
            System.out.println("Ok, o ponto está mais distante do que poderia, passou da imagem");
            Assert.assertTrue(true);
            //throw e;
        }

        px = 5.0000000000000001; // Arredondado para 8.0 ;-)
        py = 5.0000000000000001;
        pts = ic.criaPontos(px, py, i);
        //System.out.println(Arrays.toString(pts));
        try {
            testeDaCriacaoPontos(pts, i, px, py);
        } catch (AssertionError e) {
            System.out.println(Arrays.toString(pts));
            throw e;
        }


    }

    public void testInfere() {

        Imagem img = criaImagemPadrao();

        MatrizIntImagem imgPring = new MatrizIntImagem(img);

        System.out.println(imgPring.imprimeRgb(Imagem.Vermelho));

        System.out.println(img.toString());

        final InferePontoCubica ic = new InferePontoCubica();
        IntStream
                .range(img.getOrigemX() * 100, img.getLimiteX() * 100)
                .mapToDouble(x -> x / 100.0)
                .filter(x -> x > (double) img.getOrigemX() && x < (double) img.getLimiteX())
                .forEach(xInfere ->
                        IntStream
                                .range(img.getOrigemY() * 100, img.getLimiteY() * 100)
                                .mapToDouble(x -> x / 100.)
                                .filter(x -> x > (double) img.getOrigemY() && x < (double) img.getLimiteY() / 100.)
                                .forEach(yInfere -> {                                        }
                                )
                );

    }
}*/
package br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes;

import br.com.spedison.usogeral.Arredondador;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.InferenciaPonto;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.ResultadoInferencia;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularMatrixException;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

/***
 * Fazendo do jeito clássico com matrizes usando Apache Commons.
 * Modelo usado : AX + B = P
 * Assim fica V(x,y) = A.x⁰y⁰ + B.x⁰y¹ + C.x¹.y⁰ + D.x¹.y¹  --> Onde A..D = Resultado, x,y = Pontos e V = Valores dos pontos
 * Colocando em matrizes V = Pontos . Fatores
 * Resultado : P
 * [F(x-1,y-1), F(x+1,y-1), F(x-1,y+1),F(x+1,y+1)] = [A B C D] . [[Linha de Valores de X]
 *                                                                [Linha de Valores de Y]
 *                                                                [Linha de Valores de X * Y]
 *                                                                [Linha de Constante 1]]
 */
public class InferenciaPontoBilinearV3 implements InferenciaPonto {

    static final int grau = 1;

    private Imagem imagem;

    public InferenciaPontoBilinearV3() {
    }

    public void setImagem(Imagem imagemOrigem) {
        this.imagem = imagemOrigem;
    }

    private void imprimeMatrix(RealMatrix matrix) {
        var str = matrix.toString()
                .replace("},", "\n")
                .replace("{", "")
                .replace("}}", "\n")
                .replace(",", "  ,  ")
                .replace("Array2DRowRealMatrix", "\n")
                .replace("BlockRealMatrix", "\n");
        System.out.println(str);
    }

    // Usa 4 pontos.
    private RealMatrix[] preencheMatrixPrincipal(Point[] pontos) {

        RealMatrix matrizPrincipal = MatrixUtils.createRealMatrix(pontos.length, pontos.length); // Matrix que representa o sistema original de pontos.
        IntStream.range(0, pontos.length).forEach(equacao -> {
            final int[] variavel = {0};
            IntStream.rangeClosed(0, grau).forEach(variavelX -> {
                IntStream.rangeClosed(0, grau).forEach(variavelY -> {
                    matrizPrincipal.setEntry(equacao, variavel[0]++, Math.pow(pontos[equacao].x, variavelX) * Math.pow(pontos[equacao].y, variavelY));
                });
            });
        });

        try {
            return new RealMatrix[]{matrizPrincipal, MatrixUtils.inverse(matrizPrincipal)};

        } catch (SingularMatrixException e) {
            System.out.println(":::Matriz Singular:::");
            imprimeMatrix(matrizPrincipal);
            Arrays.stream(pontos).forEach(System.out::println);
            e.printStackTrace();
            throw e;
        }
    }

    private void preencheValores(Point[] pontos, int RGB, RealMatrix valores) {

        int[] pos = {0};

        try {
            Arrays.stream(pontos).forEach(p -> valores.setEntry(pos[0]++, 0, (double) imagem.getPixel(p.x, p.y)[RGB]));

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("::Pontos lidos::");
            Arrays.stream(pontos).forEach(System.out::println);
            System.out.println("::Imagem::");
            System.out.println(imagem);
            e.printStackTrace();
            throw e;
        }

    }


    private int calculaPonto(double pontoX, double pontoY, RealMatrix fatores) {
        double[] resultado = {0.0};

        final int[] pontoResultado = {0};
        IntStream.rangeClosed(0, grau).forEach(grauVariavelX -> {
            IntStream.rangeClosed(0, grau).forEach(grauVariavelY -> {
                resultado[0] +=
                        fatores.getEntry(pontoResultado[0]++, 0)
                                * Math.pow(pontoX, grauVariavelX)
                                * Math.pow(pontoY, grauVariavelY);
            });
        });

        return imagem.limitaValores(Arredondador.arredonda(resultado[0]));
    }


    public void inferePonto(double pontoImagemX, double pontoImagemY, ResultadoInferencia resultado) {

        resultado.setProcessado(false);

        Objects.requireNonNull(resultado, "O Resultado não deve ser null");
        Objects.requireNonNull(imagem, "A Imagem não deve ser null");

        if (imagem.isPontoInvalidoInferencia(pontoImagemX, pontoImagemY)) {
            resultado.setMsgError("Ponto fora da imagem");
            return;
        }

        Point[] pontos = criaPontos(pontoImagemX, pontoImagemY);
        RealMatrix[] matrixPrincipalEInversa = null;
        try {
            matrixPrincipalEInversa = preencheMatrixPrincipal(pontos);
        } catch (SingularMatrixException e) {
            resultado.setMsgError("Problema ao inverter matrix principal. Ponto processado (" + pontoImagemX + " ; " + pontoImagemY + ")");
            e.printStackTrace();
            return;
        }

        // Calcula R, G e B Separados
        RealMatrix matrixDeValores = MatrixUtils.createRealMatrix(pontos.length, 1);
        try {
            // Vermelho
            preencheValores(pontos, Imagem.Vermelho, matrixDeValores);
            RealMatrix fatores = matrixPrincipalEInversa[1].multiply(matrixDeValores);
            resultado.setValorVermelho(
                    calculaPonto(pontoImagemX, pontoImagemY, fatores)
            );
        } catch (ArrayIndexOutOfBoundsException e) {
            resultado.setMsgError("Ponto fora matrix. Erro no Vemelho. Ponto processado (" + pontoImagemX + " ; " + pontoImagemY + ")");
            e.printStackTrace();
            return;
        }

        try {
            // Verde
            preencheValores(pontos, Imagem.Verde, matrixDeValores);
            RealMatrix fatores = matrixPrincipalEInversa[1].multiply(matrixDeValores);
            // Calcula R, G e B Separados
            resultado.setValorVerde(
                    calculaPonto(pontoImagemX, pontoImagemY, fatores)
            );
        } catch (ArrayIndexOutOfBoundsException e) {
            resultado.setMsgError("Ponto fora matrix. Erro no Verde. Ponto processado (" + pontoImagemX + " ; " + pontoImagemY + ")");
            e.printStackTrace();
            return;
        }

        try {
            // Azul
            preencheValores(pontos, Imagem.Azul, matrixDeValores);
            RealMatrix fatores = matrixPrincipalEInversa[1].multiply(matrixDeValores);
            // Calcula R, G e B Separados
            resultado.setValorAzul(
                    calculaPonto(pontoImagemX, pontoImagemY, fatores)
            );
        } catch (ArrayIndexOutOfBoundsException e) {
            resultado.setMsgError("Ponto fora matrix. Erro no Azul. Ponto processado (" + pontoImagemX + " ; " + pontoImagemY + ")");
            e.printStackTrace();
            return;
        }

        resultado.setPonto(pontoImagemX, pontoImagemY);
        resultado.setProcessado(true);
    }

    public Point[] criaPontos(double pontoImagemX, double pontoImagemY) {
        final int xP1;
        final int yP1;
        final int[] contaPontos = {0};
        final Point[] ret = new Point[4];

        if (Math.abs(imagem.getOrigemX() - pontoImagemX) < 1.) {
            xP1 = imagem.getOrigemX();
        } else {
            if ((int) pontoImagemX >= imagem.getLimiteX())
                xP1 = imagem.getLimiteX() - 1;
            else
                xP1 = (int) pontoImagemX;
        }

        if (Math.abs(imagem.getOrigemY() - pontoImagemY) < 1.) {
            yP1 = imagem.getOrigemY();
        } else {
            if ((int) pontoImagemX >= imagem.getLimiteY())
                yP1 = imagem.getLimiteY() - 1;
            else
                yP1 = (int) pontoImagemY;
        }

        IntStream.range(xP1, xP1 + 2).forEach(
                x -> {
                    IntStream.range(yP1, yP1 + 2).forEach(
                            y -> {
                                ret[contaPontos[0]++] = new Point(x, y);
                            });
                });

        return ret;
    }

    @Override
    public String getNome() {
        return "InferenciaBilinearV3";
    }
}
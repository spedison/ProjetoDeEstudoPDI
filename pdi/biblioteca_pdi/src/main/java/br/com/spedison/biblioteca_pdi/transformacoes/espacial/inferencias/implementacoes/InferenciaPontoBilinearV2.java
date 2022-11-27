package br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes;

import br.com.spedison.biblioteca_pdi.auxiliar.Arredondador;
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
 * Modelo usado : AX + B
 * Assim fica F(x,y) = A.x + B.y + C.x.y + D
 * Colocando em matrizes F = Fatores . Pontos
 * [F(x-1,y-1), F(x+1,y-1), F(x-1,y+1),F(x+1,y+1)] = [A B C D] . [[Linha de Valores de X]
 *                                                                [Linha de Valores de Y]
 *                                                                [Linha de Valores de X * Y]
 *                                                                [Linha de Constante 1]]
 */
public class InferenciaPontoBilinearV2 implements InferenciaPonto {

    Imagem imagem;

    public InferenciaPontoBilinearV2() {
    }

    @Override
    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
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
        RealMatrix matrizPrincipal = MatrixUtils.createRealMatrix(4, 4);
        // Linha 0 -> Baseado em X
        final int[] pos = {0};
        Arrays.stream(pontos).forEach(p -> matrizPrincipal.setEntry(0, pos[0]++, p.x));
        // Linha 1 -> Baseado em Y
        pos[0] = 0;
        Arrays.stream(pontos).forEach(p -> matrizPrincipal.setEntry(1, pos[0]++, p.y));
        // Linha 2 -> Baseado em X * Y
        pos[0] = 0;
        Arrays.stream(pontos).forEach(p -> matrizPrincipal.setEntry(2, pos[0]++, p.y * p.x));
        // Linha 3 -> Baseado na constante (1)
        pos[0] = 0;
        Arrays.stream(pontos).forEach(p -> matrizPrincipal.setEntry(3, pos[0]++, 1.));

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

    private RealMatrix preencheValores(Point[] pontos, int RGB, RealMatrix valores) {
        int[] pos = {0};
        try {
            Arrays.stream(pontos).forEach(p -> valores.setEntry(0, pos[0]++, (double) imagem.getPixel(p.x, p.y)[RGB]));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("::Pontos lidos::");
            Arrays.stream(pontos).forEach(System.out::println);
            System.out.println("::Imagem::");
            System.out.println(imagem);
            e.printStackTrace();
            throw e;
        }
        return valores;
    }


    private double calculaPonto(double pontoX, double pontoY, RealMatrix fatores) {
        double resultado = 0.0;

        resultado += fatores.getEntry(0, 0) * pontoX;
        resultado += fatores.getEntry(0, 1) * pontoY;
        resultado += fatores.getEntry(0, 2) * pontoY * pontoX;
        resultado += fatores.getEntry(0, 3);

        return resultado;
    }


    public void inferePonto(double pontoImagemX, double pontoImagemY, ResultadoInferencia resultado) {

        resultado.setProcessado(false);

        Objects.requireNonNull(resultado, "O Resultado não deve ser null");
        Objects.requireNonNull(imagem, "A Imagem não deve ser null");

        if (imagem.isPontoInvalidoInferencia(pontoImagemX, pontoImagemY)) {
            resultado.setPonto(pontoImagemX, pontoImagemY);
            resultado.setMsgError("Ponto fora da imagem");
            return;
        }

        Point[] pontos = criaPontos(pontoImagemX, pontoImagemY);
        RealMatrix[] matrixPrincipal = null;
        try {
            matrixPrincipal = preencheMatrixPrincipal(pontos);
        } catch (SingularMatrixException e) {
            System.out.println(":::Matriz Singula::");
            System.out.println("Ponto processado (" + pontoImagemX + " ; " + pontoImagemY + ")");
            throw e;
        }

        // Calcula R, G e B Separados
        RealMatrix matrixDeValores = MatrixUtils.createRealMatrix(1, 4);
        try {
            // Vermelho
            preencheValores(pontos, Imagem.Vermelho, matrixDeValores);
            RealMatrix fatores = matrixDeValores.multiply(matrixPrincipal[1]);
            resultado.setValorVermelhoDouble(
                    calculaPonto(pontoImagemX, pontoImagemY, fatores)
            );
            resultado.setValorVermelho(
                    imagem.limitaValores(
                            Arredondador.arredonda(
                                    resultado.getValorVermelhoDouble()
                            )
                    ));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("::Erro no Vermelho::");
            System.out.println("pontoImagemX = " + pontoImagemX);
            System.out.println("pontoImagemY = " + pontoImagemY);
            e.printStackTrace();
            throw e;
        }

        try {
            // Verde
            preencheValores(pontos, Imagem.Verde, matrixDeValores);
            RealMatrix fatores = matrixDeValores.multiply(matrixPrincipal[1]);
            // Calcula R, G e B Separados
            resultado.setValorVerdeDouble(
                    calculaPonto(pontoImagemX, pontoImagemY, fatores)
            );
            resultado.setValorVerde(
                    imagem.limitaValores(
                            Arredondador.arredonda(
                                    resultado.getValorVerdeDouble()
                            )
                    ));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("::Erro no Verde::");
            System.out.println("pontoImagemX = " + pontoImagemX);
            System.out.println("pontoImagemY = " + pontoImagemY);
            e.printStackTrace();
            throw e;
        }

        try {
            // Azul
            preencheValores(pontos, Imagem.Azul, matrixDeValores);
            RealMatrix fatores = matrixDeValores.multiply(matrixPrincipal[1]);
            // Calcula R, G e B Separados
            resultado.setValorAzulDouble(
                    calculaPonto(pontoImagemX, pontoImagemY, fatores)
            );
            resultado.setValorAzul(
                    imagem.limitaValores(
                            Arredondador.arredonda(
                                    resultado.getValorAzulDouble()
                            )
                    ));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("::Erro no Azul::");
            System.out.println("pontoImagemX = " + pontoImagemX);
            System.out.println("pontoImagemY = " + pontoImagemY);
            e.printStackTrace();
            throw e;
        }

        resultado.setPonto(pontoImagemX, pontoImagemY);

        resultado.setProcessado(true);
    }

    private int definePrimeiroPontoUsadoParaInferencia(double ponto, int origem, int limite) {

        var retPonto = Arredondador.menorNumeroProximoInt(ponto);

        if (retPonto <= origem) {
            retPonto = origem;
        }

        if ((retPonto + 1) > limite) {
            retPonto = limite;
        }

        return retPonto;
    }

    public Point[] criaPontos(double pontoImagemX, double pontoImagemY) {
        final int xP1;
        final int yP1;
        final int[] contaPontos = {0};
        final Point[] ret = new Point[4];

        xP1 = definePrimeiroPontoUsadoParaInferencia(pontoImagemX, imagem.getOrigemX(), imagem.getLimiteX());
        yP1 = definePrimeiroPontoUsadoParaInferencia(pontoImagemY, imagem.getOrigemY(), imagem.getLimiteY());

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
        return "InferenciaBilinearV2";
    }
}
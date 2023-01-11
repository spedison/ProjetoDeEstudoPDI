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
 * Assim fica V(x,y) = A.x⁰.y⁰ + B.x⁰.y¹ + C.x⁰.y² + D.x⁰.y³ +
 *                     E.x¹.y⁰ + F.x¹.y¹ + G.x¹.y² + H.x¹.y³ +
 *                     I.x².y⁰ + J.x².y¹ + K.x².y² + L.x¹.y³ +
 *                     M.x³.y⁰ + N.x³.y¹ + O.x³.y² + P.x³.y³ +
 *
 *                     --> Onde A..P = Fatores,
 *                              x,y  = Pontos e
 *                              V    = Valores dos pontos
 * Colocando em matrizes V = Pontos . Fatores
 *                       Resultado = Fatores⁻¹ * Valores (Aqui tenho todos os valores de A..P
 */
public class InferenciaPontoCubica implements InferenciaPonto {

    static final int grau = 3;

    private Imagem imagem;

    public InferenciaPontoCubica() {
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
            IntStream.rangeClosed(0, grau).forEach(grauVariavelX -> {
                IntStream.rangeClosed(0, grau).forEach(grauVariavelY -> {
                    matrizPrincipal.setEntry(equacao,
                            variavel[0]++,
                            Math.pow(pontos[equacao].x - pontos[0].x, grauVariavelX)
                                    * Math.pow(pontos[equacao].y - pontos[0].y, grauVariavelY));
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


    private double calculaPonto(Point primeiroPonto, double pontoX, double pontoY, RealMatrix fatores) {
        double[] resultado = {0.0};

        final int[] pontoResultado = {0};
        final double _pontoX = pontoX - (double) primeiroPonto.x;
        final double _pontoY = pontoY - (double) primeiroPonto.y;

        IntStream.rangeClosed(0, grau).forEach(grauVariavelX -> {
            IntStream.rangeClosed(0, grau).forEach(grauVariavelY -> {
                resultado[0] +=
                        fatores.getEntry(pontoResultado[0], 0)
                                * Math.pow(_pontoX, grauVariavelX)
                                * Math.pow(_pontoY, grauVariavelY);
                pontoResultado[0]++;
            });
        });

        return resultado[0];
    }


    public void inferePonto(double pontoImagemX, double pontoImagemY, ResultadoInferencia resultado) {

        resultado.setProcessado(false);
        resultado.setPonto(pontoImagemX, pontoImagemY);
        Objects.requireNonNull(resultado, "O Resultado não deve ser null");
        Objects.requireNonNull(imagem, "A Imagem não deve ser null");

        if (imagem.isPontoInvalidoInferencia(pontoImagemX, pontoImagemY)) {
            resultado.setMsgError("Ponto fora da imagem");
            return;
        }

        if (processaPontoExato(pontoImagemX, pontoImagemY, resultado)) return;

        Point[] pontos = criaPontos(pontoImagemX, pontoImagemY);
        RealMatrix[] matrixPrincipalEInversa;
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
            resultado.setValorVermelhoDouble(
                    calculaPonto(pontos[0], pontoImagemX, pontoImagemY, fatores)
            );
            resultado.setValorVermelho(imagem.limitaValores(Arredondador.arredonda(resultado.getValorVermelhoDouble())));
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
            resultado.setValorVerdeDouble(
                    calculaPonto(pontos[0], pontoImagemX, pontoImagemY, fatores)
            );
            resultado.setValorVerde(imagem.limitaValores(Arredondador.arredonda(resultado.getValorVerdeDouble())));
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
            resultado.setValorAzulDouble(
                    calculaPonto(pontos[0], pontoImagemX, pontoImagemY, fatores)
            );
            resultado.setValorAzul(imagem.limitaValores(Arredondador.arredonda(resultado.getValorAzulDouble())));
        } catch (ArrayIndexOutOfBoundsException e) {
            resultado.setMsgError("Ponto fora matrix. Erro no Azul. Ponto processado (" + pontoImagemX + " ; " + pontoImagemY + ")");
            e.printStackTrace();
            return;
        }

        resultado.setMsgError("Ponto Processado com Sucesso.");
        resultado.setProcessado(true);
    }

    private boolean processaPontoExato(double pontoImagemX, double pontoImagemY, ResultadoInferencia resultado) {
        var pontoXExato = Math.abs(Math.rint(pontoImagemX) - pontoImagemX) <= 1E-7;
        var pontoYExato = Math.abs(Math.rint(pontoImagemY) - pontoImagemY) <= 1E-7;
        if (pontoXExato && pontoYExato) {
            resultado.setProcessado(true);
            resultado.setMsgError(
                    ("Ponto passado é muito próximo ao ponto já definido na imagem. " +
                            "Não processei inferência.Pontos Originais (%.12f,%.12f)")
                            .formatted(pontoImagemX, pontoImagemY));
            resultado.setPonto(
                    Arredondador.arredonda(pontoImagemX),
                    Arredondador.arredonda(pontoImagemY));

            int[] valImg = imagem
                    .getPixel(
                            (int) resultado.getPontoX(),
                            (int) resultado.getPontoY());

            resultado.setValorVermelho(valImg[Imagem.Vermelho]);
            resultado.setValorVerde(valImg[Imagem.Verde]);
            resultado.setValorAzul(valImg[Imagem.Azul]);
            resultado.setValorVermelhoDouble(valImg[Imagem.Vermelho]);
            resultado.setValorVerdeDouble(valImg[Imagem.Verde]);
            resultado.setValorAzulDouble(valImg[Imagem.Azul]);
            return true;
        }
        return false;
    }

    private int definePrimeiroPontoUsadoParaInferencia(double ponto, int origem, int limite) {
        int retPonto = Arredondador.menorNumeroProximoInt(ponto) - 1;

        if (Math.abs(retPonto - ponto) < 1.5)
            retPonto--;

        if (retPonto < origem) {
            retPonto = origem;
        } else if ((retPonto + 3) > limite) {
            retPonto = limite - 3;
        }
        return retPonto;
    }

    public Point[] criaPontos(double pontoImagemX, double pontoImagemY) {
        final int xP1;
        final int yP1;
        final int[] contaPonto = {0};
        Point[] ret = new Point[16];

        // Pega o menor ponto a esquerda sem ultrapassar os limites. Caso ultrapasse, usa o limite.
        xP1 = definePrimeiroPontoUsadoParaInferencia(pontoImagemX, imagem.getOrigemX(), imagem.getLimiteX());
        yP1 = definePrimeiroPontoUsadoParaInferencia(pontoImagemY, imagem.getOrigemY(), imagem.getLimiteY());

        // gera os 16 pontos. (4x4)
        IntStream.range(xP1, xP1 + 4).forEach(x ->
        {
            IntStream.range(yP1, yP1 + 4).forEach(y -> {
                ret[contaPonto[0]++] = new Point(x, y);
            });
        });

        return ret;
    }

    @Override
    public String getNome() {
        return "InferenciaBiCubica";
    }
}
package br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes;

import br.com.spedison.usogeral.Arredondador;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.InferenciaPonto;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.ResultadoInferencia;

import java.util.Objects;

/***
 * Baseada no exemplo : http://marmsx.msxall.com/artigos/interpolation.pdf
 * Modelo usado : AX + B
 * Assim fica F(x,y) = A.x + B.y + C.x.y + D
 * Colocando em matrizes F = Fatores . Pontos
 * [F(x-1,y-1), F(x+1,y-1), F(x-1,y+1),F(x+1,y+1)] = [A B C D] . [[Linha de Valores de X]
 *                                                                [Linha de Valores de Y]
 *                                                                [Linha de Valores de X * Y]
 *                                                                [Linha de Constante 1]]
 */
public class InferenciaPontoBilinearV1 implements InferenciaPonto {

    Imagem imagem;

    public InferenciaPontoBilinearV1() {
    }

    @Override
    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public void inferePonto(double pontoImagemX, double pontoImagemY, ResultadoInferencia resultado) {

        resultado.setProcessado(false);

        Objects.requireNonNull(resultado, "O Resultado não deve ser null");
        Objects.requireNonNull(imagem, "A Imagem não deve ser null");

        if (imagem.isPontoInvalidoInferencia(pontoImagemX, pontoImagemY))
            return;

        int Pox = Arredondador.menorNumeroEmModulo(pontoImagemX);
        int Poy = Arredondador.menorNumeroEmModulo(pontoImagemY);

        double xn = Math.abs(Arredondador.parteFracionaria(pontoImagemX));
        double yn = Math.abs(Arredondador.parteFracionaria(pontoImagemY));
        double compX = Arredondador.complemento(xn);
        double compY = Arredondador.complemento(yn);

        try {
            resultado.setValorVermelho((int) Math.ceil(
                    imagem.getPixel(Pox, Poy)[0] * compX * compY +
                            imagem.getPixel(Pox+1, Poy + 1)[0] * xn * compY +
                            imagem.getPixel(Pox , Poy+1)[0] * compX * yn +
                            imagem.getPixel(Pox + 1, Poy + 1)[0] * xn * yn));

            resultado.setValorVerde((int) Math.ceil(
                    imagem.getPixel(Pox, Poy)[1] * compX * compY +
                            imagem.getPixel(Pox+1, Poy + 1)[1] * xn * compY +
                            imagem.getPixel(Pox , Poy+1)[1] * compX * yn +
                            imagem.getPixel(Pox + 1, Poy + 1)[1] * xn * yn));

            resultado.setValorAzul((int) Math.ceil(
                    imagem.getPixel(Pox, Poy)[2] * compX * compY +
                            imagem.getPixel(Pox+1, Poy + 1)[2] * xn * compY +
                            imagem.getPixel(Pox , Poy+1)[2] * compX * yn +
                            imagem.getPixel(Pox + 1, Poy + 1)[2] * xn * yn));

        } catch (ArrayIndexOutOfBoundsException aiob) {
            System.out.println("Problemas ao ler o ponto: (" + Pox + " ; " + Poy + ")");
            System.out.println("Pontos originais: (" + pontoImagemX + " ; " + pontoImagemY + ")");
            System.out.println(imagem.toString());
            throw aiob;
        }
        resultado.setPonto(pontoImagemX, pontoImagemY);

        resultado.setProcessado(true);
    }

    @Override
    public String getNome() {
        return "InferenciaBilinearV1";
    }
}
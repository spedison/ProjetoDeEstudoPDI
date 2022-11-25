package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;

import java.util.Arrays;

/***
 * Aplica-se uma imagem máscara a qual é usada a condição 0 e diferente de 0.
 * Se a máscara for 0:
 *      Aplica-se um valor padráo (que inicialmente é 0xFF)
 *      Caso contrário: Aplica-se o valor da própria imagem.
 */
public class TransformacaoAplicaMascara implements TransformacaoPixelRGB<TransformacaoAplicaMascara> {

    private Imagem mascara;
    int[] valorPadrao = {0xFF, 0xFF, 0xFF};

    public TransformacaoAplicaMascara(Imagem mascara, int[] valorPadrao) {
        this.mascara = mascara;
        this.valorPadrao = valorPadrao;
    }


    @Override
    public int[] transformaRGB(int[] valorPixel, int x, int y, Imagem imagem) {

        if (imagem.getLargura() != mascara.getLargura() ||
                imagem.getAltura() != mascara.getAltura()) {
            throw new RuntimeException("Tamanho da máscara deve ser igual ao da máscara");
        }

        int vermelho = mascara.getPixel(x, y)[Imagem.Vermelho] == 0 ?
                valorPadrao[Imagem.Vermelho] : valorPixel[Imagem.Vermelho];

        int verde = mascara.getPixel(x, y)[Imagem.Verde] == 0 ?
                valorPadrao[Imagem.Verde] : valorPixel[Imagem.Verde];

        int azul = mascara.getPixel(x, y)[Imagem.Azul] == 0 ?
                valorPadrao[Imagem.Azul] : valorPixel[Imagem.Azul];

        return new int[]{vermelho, verde, azul};
    }

    @Override
    public String toString() {
        return "TransformacaoAplicaMascara{" +
                "valoPadrao => " + Arrays.toString(valorPadrao) +
                "Mascara == null => " + (mascara == null ? "Sim" : "Não") + '}';
    }

    @Override
    public String getNomeModelo() {
        return "Aplica Mascara :: F(X) = (M != 0) ? X : Padrão";
    }

}

package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;

public class TransformacaoCinza implements TransformacaoPixelRGB<TransformacaoCinza> {

    int[] pesos = new int[]{1, 1, 1};
    int soma = 3;

    public TransformacaoCinza(int[] pesos) {
        setPesos(pesos);
    }

    public TransformacaoCinza() {
    }

    public void setPesos(int[] pesos) {
        this.pesos = pesos;
        soma = pesos[0] + pesos[1] + pesos[2];
    }

    @Override
    public int[] transformaRGB(int[] valorPixel, int x, int y, Imagem imagem) {
        var valMedia = (
                (valorPixel[Imagem.Vermelho] * pesos[Imagem.Vermelho]) +
                        (valorPixel[Imagem.Verde] * pesos[Imagem.Verde]) +
                        (valorPixel[Imagem.Azul] * pesos[Imagem.Azul])
        ) / soma;
        valMedia = imagem.limitaValores(valMedia);
        return new int[]{valMedia, valMedia, valMedia};
    }

    @Override
    public String toString() {
        return "Classe : %s \nMédia Ponderada = (R*%d + G*%d + B*%d) / %d".
                formatted(
                        this.getClass().getSimpleName(),
                        pesos[Imagem.Vermelho],
                        pesos[Imagem.Verde],
                        pesos[Imagem.Azul],
                        soma
                );
    }

    @Override
    public String getNomeModelo() {
        return "Transformação tons de Cinza :: F(Xr, Xg, Xb) = (Pr*Xr + Pg*Xg + Pb*Xb) / (Pr+Pg+Pb)";
    }

}

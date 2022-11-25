package br.com.spedison.biblioteca_pdi.padroes;

import br.com.spedison.biblioteca_pdi.base.Imagem;

public class ImagemPadraoLinear extends ImagemPadrao {

    private int[] coeficienteAngularX = new int[]{2, 2, 2};
    private int[] coeficienteAngularY = new int[]{5, 5, 5};
    private int[] coeficienteLinear = new int[]{10, 10, 10};

    public ImagemPadraoLinear(int xLen, int yLen) {
        super(xLen, yLen);
    }

    public void setCoeficienteAngularX(int[] coeficienteAngularX) {
        this.coeficienteAngularX = coeficienteAngularX;
    }

    public void setCoeficienteAngularY(int[] coeficienteAngularY) {
        this.coeficienteAngularY = coeficienteAngularY;
    }

    public void setCoeficienteLinear(int[] coeficienteLinear) {
        this.coeficienteLinear = coeficienteLinear;
    }

    @Override
    public void geraPadrao() {

        getStreamLargura().forEach(x -> {
            getStreamAltura().forEach(y -> {
                var vermelho = x * coeficienteAngularX[Imagem.Vermelho] + coeficienteLinear[Imagem.Vermelho] + y * coeficienteAngularY[Imagem.Vermelho];
                var verde = x * coeficienteAngularX[Imagem.Verde] + coeficienteLinear[Imagem.Verde] + y * coeficienteAngularY[Imagem.Verde];
                var azul = x * coeficienteAngularX[Imagem.Azul] + coeficienteLinear[Imagem.Azul] + y * coeficienteAngularY[Imagem.Azul];
                setPixel(x, y, new int[]{vermelho, verde, azul});
            });
        });
    }
}
package br.com.spedison.biblioteca_pdi.transformacoes.pixel;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;

import java.awt.image.BufferedImage;

public class TransformadorPixel extends Imagem implements TransformadorImagem<TransformadorPixel> {

    public TransformadorPixel(BufferedImage bi) {
        super(bi);
    }

    public TransformadorPixel(String nomeArquivo) {
        super(nomeArquivo);
    }

    public TransformadorPixel() {
    }

    public TransformadorPixel(Imagem img) {
        super(img);
    }

    public TransformadorPixel(int xLen, int yLen) {
        super(xLen, yLen);
    }

    public TransformadorPixel(int xLen, int yLen, int type) {
        super(xLen, yLen, 0x000000, type);
    }

    public TransformadorPixel(int[] size) {
        super(size);
    }


    @Override
    public TransformadorPixel transforma(TransformacaoPixelRGB transformacaoRGB) {
        return transforma(transformacaoRGB, getOrigemX(), getLimiteX(), getOrigemY(), getLimiteY());
    }

    private boolean verificaIntervalo(int x, int y, int xMin, int yMin, int xMax, int yMax) {
        return x >= xMin && x <= xMax
                && y >= yMin && y <= yMax;
    }

    @Override
    public TransformadorPixel transforma(TransformacaoPixelRGB transformacaoRGB, int xMin, int xMax, int yMin, int yMax) {

        super.validaLimitesImagem(xMin, xMax, yMin, yMax);
        transformacaoRGB.inicializa(this);

        var imagemSaida = new Imagem(this.getTamanho());
        imagemSaida.setOrigemX(this.getOrigemX());
        imagemSaida.setOrigemY(this.getOrigemY());
        getStreamLargura().parallel().forEach(
                posX -> getStreamAltura().parallel().forEach(
                        posY -> {
                            int[] valPixelConvolucao =
                                    verificaIntervalo(posX, posY, xMin, yMin, xMax, yMax) ?
                                            transformacaoRGB.transformaRGB(this.getPixel(posX, posY), posX, posY, this) :
                                            this.getPixel(posX, posY);
                            valPixelConvolucao = imagemSaida.limitaValores(valPixelConvolucao);
                            imagemSaida.setPixel(posX, posY, valPixelConvolucao);
                        }));
        this.setImageBuffer(imagemSaida.getImageBuffer());
        transformacaoRGB.finaliza();
        return this;
    }
}
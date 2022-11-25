package br.com.spedison.biblioteca_pdi.transformacoes.pixel;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;

import java.awt.image.BufferedImage;

public class TransformadorPixelRecursivo extends Imagem implements TransformadorImagem<TransformadorPixelRecursivo> {

    public TransformadorPixelRecursivo(BufferedImage bi) {
        super(bi);
    }

    public TransformadorPixelRecursivo(String nomeArquivo) {
        super(nomeArquivo);
    }

    public TransformadorPixelRecursivo() {
    }

    public TransformadorPixelRecursivo(Imagem img) {
        super(img);
    }

    public TransformadorPixelRecursivo(int xLen, int yLen) {
        super(xLen, yLen);
    }

    public TransformadorPixelRecursivo(int xLen, int yLen, int type) {
        super(xLen, yLen, 0x000000, type);
    }

    public TransformadorPixelRecursivo(int[] size) {
        super(size);
    }

    public TransformadorPixelRecursivo transforma(TransformacaoPixelRGB transformacaoRGB) {
        return transforma(transformacaoRGB, getOrigemX(), getLimiteX(), getOrigemY(), getLimiteY());
    }

    public TransformadorPixelRecursivo transforma(TransformacaoPixelRGB transformacaoRGB, int xMin, int xMax, int yMin, int yMax) {

        super.validaLimitesImagem(xMin, xMax, yMin, yMax);

        var imagemSaida = new Imagem(this.getTamanho());
        getStreamLargura(xMin, xMax).parallel().forEach(
                posX -> {
                    getStreamAltura(yMin, yMax).parallel().forEach(
                            posY -> {
                                int[] valNewPixel = transformacaoRGB.transformaRGB(this.getPixel(posX, posY), posX, posY, this);
                                imagemSaida.setPixel(posX, posY, valNewPixel);
                            });
                });
        this.setImageBuffer(imagemSaida.getImageBuffer());
        return this;
    }
}

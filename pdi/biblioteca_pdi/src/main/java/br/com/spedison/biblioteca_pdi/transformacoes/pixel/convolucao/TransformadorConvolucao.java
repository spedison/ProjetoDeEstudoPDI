package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;

import java.awt.image.BufferedImage;

/****
 * Como a transformação de Pixel tem a mesma interface do Kernel (um extende do outro) podemos reaproveitar todo o
 * código de transformação a menos do fato que: queremos que a leitura das bordas sejam limitadas, assim temos
 * que reescrever as funções de leitura de pixel para se passar das bordas não de dê erro, mas repita os valores das
 * bordas.
 *
 */
public class TransformadorConvolucao extends TransformadorPixel {

    public TransformadorConvolucao(BufferedImage bi) {
        super(bi);
    }

    public TransformadorConvolucao(String nomeArquivo) {
        super(nomeArquivo);
    }

    public TransformadorConvolucao() {
    }

    public TransformadorConvolucao(Imagem img) {
        super(img);
    }

    public TransformadorConvolucao(int xLen, int yLen) {
        super(xLen, yLen);
    }

    public TransformadorConvolucao(int xLen, int yLen, int type) {
        super(xLen, yLen, type);
    }

    public TransformadorConvolucao(int[] size) {
        super(size);
    }

    @Override
    public int[] getPixel(int x, int y) {

        if (x < getOrigemX())
            x = getOrigemX();

        if (y < getOrigemY())
            y = getOrigemY();

        if (x > getLimiteX())
            x = getLimiteX();

        if (y >= getLimiteY())
            y = getLimiteY();

        return super.getPixel(x, y);
    }

}
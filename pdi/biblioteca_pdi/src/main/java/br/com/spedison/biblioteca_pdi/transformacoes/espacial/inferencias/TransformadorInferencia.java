package br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias;

import br.com.spedison.biblioteca_pdi.base.Imagem;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/***
 * Esta classe está indevida, pois ela pressupõe que a imagem inteira será inferida.
 * Deve-se repensar isso.
 * TODO: Apagar essa classe.
 * */
public class TransformadorInferencia extends Imagem {

    InferenciaPonto inferenciaPonto;

    public TransformadorInferencia(BufferedImage bi) {
        super(bi);
    }

    public TransformadorInferencia(String nomeArquivo) {
        super(nomeArquivo);
    }

    public TransformadorInferencia() {
    }

    public TransformadorInferencia(Imagem img) {
        super(img);
    }

    public TransformadorInferencia(int xLen, int yLen) {
        super(xLen, yLen);
    }

    public TransformadorInferencia(int xLen, int yLen, int rgbCorFundo) {
        super(xLen, yLen, rgbCorFundo);
    }

    public TransformadorInferencia(int xLen, int yLen, int rgbCorFundo, int type) {
        super(xLen, yLen, rgbCorFundo, type);
    }

    public TransformadorInferencia(int[] size) {
        super(size);
    }

    public InferenciaPonto getInferenciaPonto() {
        return inferenciaPonto;
    }

    public void setInferenciaPonto(InferenciaPonto inferenciaPonto) {
        this.inferenciaPonto = inferenciaPonto;
    }

    public void transforma() {
        transforma(0, getLargura(), 0, getAltura());
    }


    public void transforma(int xMin, int xMax, int yMin, int yMax) {

        // validaLimitesImagem(xMin, xMax, yMin, yMax);

        guardaOrigem();
        setOrigemX(0);
        setOrigemY(0);

        var imagemSaida = new Imagem(getTamanho());
        final ResultadoInferencia ri = new ResultadoInferencia();
        getStreamLargura(xMin, xMax).forEach(
                posX -> {
                    getStreamAltura(yMin, yMax).forEach(
                            posY -> {
                                if (isBorda(posX, posY))
                                    imagemSaida.setPixel(posX, posY, getPixel(posX, posY));
                                else {
                                    inferenciaPonto.inferePonto(posX, posY, ri);
                                    if (ri.isProcessado()) {
                                        imagemSaida.setPixel(posX, posY, ri.getArrayRGB());
                                        System.out.printf("Processado o ponto : (%d,%d) = %s\n",posX, posY, Arrays.toString(ri.getArrayRGB()));
                                    }else {
                                        imagemSaida.setPixel(posX, posY, getPixel(posX, posY));
                                    }
                                }
                            });
                });
        setImageBuffer(imagemSaida.getImageBuffer());
        restauraOrigem();
    }
}

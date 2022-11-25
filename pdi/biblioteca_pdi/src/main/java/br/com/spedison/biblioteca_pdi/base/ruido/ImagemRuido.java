package br.com.spedison.biblioteca_pdi.base.ruido;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ruido.interfaces.GeradorRuido;

public class ImagemRuido extends Imagem {
    private final GeradorRuido geradorRuido;

    public ImagemRuido(int xLen, int yLen, GeradorRuido geradorRuido) {
        super(xLen, yLen);
        this.geradorRuido = geradorRuido;
    }

    public ImagemRuido geraImagem() {
        getStreamLargura().parallel().forEach(posX -> {
            getStreamAltura().parallel().forEach(posY -> {
                int[] pixel = {geradorRuido.getProximoNumero(),
                        geradorRuido.getProximoNumero(),
                        geradorRuido.getProximoNumero()};
                setPixel(posX, posY, pixel);
            });
        });
        return this;
    }
}

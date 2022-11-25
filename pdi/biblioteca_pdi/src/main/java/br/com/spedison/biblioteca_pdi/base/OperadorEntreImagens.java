package br.com.spedison.biblioteca_pdi.base;

import br.com.spedison.biblioteca_pdi.base.operacoes_entre_imagens.*;

import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class OperadorEntreImagens extends Imagem {

    public OperadorEntreImagens(BufferedImage bi) {
        super(bi);
    }

    public OperadorEntreImagens(String nomeArquivo) {
        super(nomeArquivo);
    }

    public OperadorEntreImagens() {
        super();
    }

    public OperadorEntreImagens(Imagem img) {
        super(img);
    }

    public OperadorEntreImagens(int xLen, int yLen) {
        super(xLen, yLen);
    }

    public OperadorEntreImagens(int xLen, int yLen, int rgbCorFundo, int type) {
        super(xLen, yLen, rgbCorFundo, type);
    }

    public Imagem operacaoNaImagem(Imagem imagem, Operacao operacaoEntreImagens) {
        IntStream.range(0, Math.min(getLargura(), imagem.getLargura())).parallel().forEach(posX -> {
            IntStream.range(0, Math.min(getAltura(), imagem.getAltura())).parallel().forEach(posY -> {
                int[] meuPixel = getPixel(posX, posY);
                int[] parcelaPixel = imagem.getPixel(posX, posY);
                meuPixel = operacaoEntreImagens
                        .operaItemMatrix(meuPixel, parcelaPixel, meuPixel);
                setPixel(posX, posY, limitaValores(meuPixel));
            });
        });
        return this;
    }

    @Override
    public OperadorEntreImagens salva(String nome) {
        super.salva(nome);
        return this;
    }

    @Override
    public OperadorEntreImagens copiaImagem(Imagem img) {
        super.copiaImagem(img);
        return this;
    }

    @Override
    public OperadorEntreImagens clone() {
        var ret = new OperadorEntreImagens(this);
        return ret;
    }
}
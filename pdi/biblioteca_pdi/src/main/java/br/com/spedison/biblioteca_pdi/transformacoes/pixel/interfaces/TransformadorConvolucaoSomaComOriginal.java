package br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformadorConvolucao;

import java.awt.image.BufferedImage;

public class TransformadorConvolucaoSomaComOriginal extends TransformadorConvolucao {

    public TransformadorConvolucaoSomaComOriginal(BufferedImage bi) {
        super(bi);
    }

    public TransformadorConvolucaoSomaComOriginal(String nomeArquivo) {
        super(nomeArquivo);
    }

    public TransformadorConvolucaoSomaComOriginal() {
    }

    public TransformadorConvolucaoSomaComOriginal(Imagem img) {
        super(img);
    }

    public TransformadorConvolucaoSomaComOriginal(int xLen, int yLen) {
        super(xLen, yLen);
    }

    public TransformadorConvolucaoSomaComOriginal(int[] size) {
        super(size);
    }


    @Override
    public TransformadorConvolucaoSomaComOriginal transforma(TransformacaoPixelRGB transformacaoRGB, int xMin, int xMax, int yMin, int yMax) {

        // Valida os limites da imagem que irá aplicar a transformação.
        super.validaLimitesImagem(xMin, xMax, yMin, yMax);

        // Cria uma imagem
        var imagemSaida = new Imagem(this);

        // Inicializa o objeto de transformação usando a imagem
        transformacaoRGB.inicializa(this);

        // Processa a imagem
        getStreamLargura(xMin, xMax).parallel().forEach(
                posX -> {
                    getStreamAltura(yMin, yMax).parallel().forEach(
                            posY -> {
                                // Calcula a derivada (ou outra transformação) sem considerar o limite da imagem
                                int[] valTransformado = transformacaoRGB.transformaRGB(this.getPixel(posX, posY), posX, posY, this);
                                // Pega o valor original.
                                int [] valOriginal = imagemSaida.getPixel(posX,posY);
                                // Saida =  original + transformado (ou derivada, seja positiva ou negativa.)
                                int [] valNovoPixel = new int[3];
                                valNovoPixel[Imagem.Vermelho] = valOriginal[Imagem.Vermelho] + valTransformado[Imagem.Vermelho];
                                valNovoPixel[Imagem.Verde] = valOriginal[Imagem.Verde] + valTransformado[Imagem.Verde];
                                valNovoPixel[Imagem.Azul] =  valOriginal[Imagem.Azul] + valTransformado[Imagem.Azul];
                                valNovoPixel = imagemSaida.limitaValores(valNovoPixel);
                                // Aplica o novo valor na imagem de saida.
                                imagemSaida.setPixel(posX, posY, valNovoPixel);
                            });
                });
        // Define a imagem de saída como a atual e retoma o valor do limite mínimo.
        this.setImageBuffer(imagemSaida.getImageBuffer());

        transformacaoRGB.finaliza();

        return this;
    }
}
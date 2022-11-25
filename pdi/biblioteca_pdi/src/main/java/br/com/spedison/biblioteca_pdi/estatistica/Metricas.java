package br.com.spedison.biblioteca_pdi.estatistica;

import br.com.spedison.biblioteca_pdi.base.Imagem;

import java.util.stream.IntStream;

/***
 * Classe para tirar média, desvio-padrão da imatem ou de um trecho dela.
 */
public class Metricas {

    private double [] media = new double[3];
    private double [] desvio = new double[3];
    private int xMin = 0;
    private int xMax= 0;
    private int yMin= 0;
    private int yMax= 0;
    private Imagem imagem;

    public double [] getMedia() {
        return media;
    }

    public double [] getDesvio() {
        return desvio;
    }

    public void setxMin(int xMin) {
        this.xMin = xMin;
    }

    public void setxMax(int xMax) {
        this.xMax = xMax;
    }

    public void setyMin(int yMin) {
        this.yMin = yMin;
    }

    public void setyMax(int yMax) {
        this.yMax = yMax;
    }

    public void processaTamanhoImagem(){
        this.setyMin(0);
        this.setyMin(0);
        this.setxMax(imagem.getTamanho()[0]);
        this.setyMax(imagem.getTamanho()[1]);
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
        processaTamanhoImagem();
    }

    public void processaMedia() {

        var xImgMax = imagem.getTamanho()[0];
        var yImgMax = imagem.getTamanho()[1];

        media[0] = 0.0;
        media[1] = 0.0;
        media[2] = 0.0;

        var contagem = new long[] {0L};
        IntStream.range(Math.max(0,xMin), Math.min(xImgMax,xMax)).forEach(x ->
        {
            IntStream.range(Math.max(0,yMin), Math.min(yImgMax,yMax)).forEach(y ->
            {
                int[] px = imagem.getPixel(x, y);
                media[Imagem.Vermelho] += px[Imagem.Vermelho];
                media[Imagem.Verde] +=  px[Imagem.Verde];
                media[Imagem.Azul] += px[Imagem.Azul];
                contagem[0] ++;
            });
        });

        media[0] /= (double)contagem[0];
        media[1] /= (double)contagem[0];
        media[2] /= (double)contagem[0];

    }

    public void processaDesvio() {

        var xImgMax = imagem.getTamanho()[0];
        var yImgMax = imagem.getTamanho()[1];

        desvio[0] = 0.0;
        desvio[1] = 0.0;
        desvio[2] = 0.0;
        var contagem = new long[] {0L};
        IntStream.range(Math.max(0,xMin), Math.min(xImgMax,xMax)).forEach(x ->
        {
            IntStream.range(Math.max(0,yMin), Math.min(yImgMax,yMax)).forEach(y ->
            {
                int[] px = imagem.getPixel(x, y);
                desvio[Imagem.Vermelho] += Math.pow(px[Imagem.Vermelho] - media[Imagem.Vermelho],2.0);
                desvio[Imagem.Verde] +=  Math.pow(px[Imagem.Verde]- media[Imagem.Verde],2.0);
                desvio[Imagem.Azul] += Math.pow(px[Imagem.Azul]- media[Imagem.Azul],2.0);
                contagem[0] ++;
            });
        });

        if ( contagem[0] != (imagem.getTamanho()[0] * imagem.getTamanho()[1]) ) {
            contagem[0] --;
        }
        desvio[0] /= (double) contagem[0];
        desvio[1] /= (double) contagem[0];
        desvio[2] /= (double) contagem[0];
    }
}

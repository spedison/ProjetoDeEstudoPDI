package br.com.spedison.biblioteca_pdi.base;

import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class MatrizIntImagem extends Imagem {

    int[][][] imagem;

    public MatrizIntImagem(int[][][] matrixImagem) {
        carregaImagemDaMatrix(matrixImagem);
    }

    public MatrizIntImagem(BufferedImage bi) {
        super(bi);
        carregaMatrixIntDaImagem();
    }

    public MatrizIntImagem(String nomeArquivo) {
        super(nomeArquivo);
        carregaMatrixIntDaImagem();
    }

    public MatrizIntImagem(Imagem ai) {
        super(ai.getImageBuffer());
        carregaMatrixIntDaImagem();
    }


    public MatrizIntImagem leArquivo(String nomeArquivo) {
        super.leArquivo(nomeArquivo);
        carregaMatrixIntDaImagem();
        return this;
    }

    private void carregaMatrixIntDaImagem() {
        imagem = new int[3][getTamanho()[0]][getTamanho()[1]];
        guardaOrigem().setOrigemX(0).setOrigemY(0);
        getStreamLargura().parallel().forEach(x ->
        {
            getStreamAltura().parallel().forEach(y ->
            {
                int[] px = super.getPixel(x, y);
                imagem[Vermelho][x][y] = px[Vermelho];
                imagem[Verde][x][y] = px[Verde];
                imagem[Azul][x][y] = px[Azul];
            });
        });
        restauraOrigem();
    }

    public String imprimeRgb(int cor) {
        StringBuffer ret = new StringBuffer();
        getStreamAltura().forEach(y -> {
            getStreamLargura().forEach(x -> {
                ret.append(getPixel(x, y)[cor]);
                ret.append("\t");
            });
            ret.append("\n");
        });
        return ret.toString();
    }


    public boolean isImagemRgb() {
        return imagem.length == 3;
    }

    public MatrizIntImagem imprimeMatrix(int rgb) {

        var nome = imagem.length == 3 ? (rgb == Vermelho ? "Vermelho" : (rgb == Verde ? "Verde" : "Azul")) : "Monocromática";
        System.out.printf("--- Matrix %s da imagem com tamanho (%d x %d) ---\n", nome, imagem[0].length, imagem[0][0].length);

        getStreamAltura().forEach(y ->
        {
            System.out.print("|");
            getStreamLargura().forEach(x ->
            {
                System.out.printf("%d\t", getPixel(x, y)[rgb]);
            });
            System.out.println("|");
        });
        System.out.println("-------------------------------------------------------------------------------");
        return this;
    }

    private void carregaImagemDaMatrix(int[][][] imagem) {

        assert (imagem.length == 3 || imagem.length == 1) : "Estamos trabalhando com imagens RGB ou monocromática, deve-se ter 3 canais ou 1 canal.";

        this.imagem = imagem.clone(); // Realizei a cópia.
        novaImagem(this.imagem[0].length, this.imagem[0][0].length);
        guardaOrigem().setOrigemY(0).setOrigemX(0);

        getStreamLargura().parallel().forEach(x ->
        {
            getStreamAltura().parallel().forEach(y ->
            {
                int px[] =
                        imagem.length == 3 ?        // Tamanho 3 RGB, Tamanho 1 RRR
                                new int[]{imagem[Vermelho][x][y], imagem[Verde][x][y], imagem[Azul][x][y]}
                                :
                                new int[]{imagem[Vermelho][x][y], imagem[Vermelho][x][y], imagem[Vermelho][x][y]};
                setPixel(x, y, px);
            });
        });
        restauraOrigem();
    }
}

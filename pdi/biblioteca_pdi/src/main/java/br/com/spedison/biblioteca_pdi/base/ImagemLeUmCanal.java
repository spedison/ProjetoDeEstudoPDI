package br.com.spedison.biblioteca_pdi.base;

import java.awt.image.BufferedImage;

public class ImagemLeUmCanal extends Imagem {

    int canalUsado;

    public ImagemLeUmCanal(BufferedImage bi, int canalUsado) {
        super(bi);
        this.canalUsado = canalUsado;
        igualaCanais();
    }

    public ImagemLeUmCanal(String nomeArquivo, int canalUsado) {
        this.leArquivo(nomeArquivo);
        this.canalUsado = canalUsado;
        igualaCanais();
    }

    public ImagemLeUmCanal() {
        this.leArquivo();
    }

    public ImagemLeUmCanal(Imagem img, int canalUsado) {
        super(img.clone());
        this.canalUsado = canalUsado;
        igualaCanais();
    }

    @Override
    public Imagem leArquivo(String nomeArquivo) {
        super.leArquivo(nomeArquivo);
        igualaCanais();
        return this;
    }

    @Override
    public Imagem setImageBuffer(BufferedImage img) {
        super.setImageBuffer(img);
        igualaCanais();
        return this;
    }

    private int igualaCanalPixel(int pixel) {
        int filtro = 0x00;
        int deslocamento = 0;

        switch (canalUsado) {
            case Vermelho:
                filtro = 0x00FF0000;
                deslocamento = 16;
                break;

            case Verde:
                filtro = 0x0000FF00;
                deslocamento = 8;
                break;

            case Azul:
                filtro = 0x000000FF;
                deslocamento = 0;
                break;
        }
        int retorno = (pixel & filtro) >> deslocamento;
        retorno = retorno | (retorno << 8) | (retorno << 16);
        return retorno;
    }

    private void igualaCanais() {
        getStreamLargura().parallel().forEach(
                posX -> {
                    getStreamAltura().parallel().forEach(
                            posY -> {
                                int px = this.getRGB(posX, posY);
                                px = igualaCanalPixel(px);
                                this.setRGB(posX, posY, px);
                            });
                });
    }
}

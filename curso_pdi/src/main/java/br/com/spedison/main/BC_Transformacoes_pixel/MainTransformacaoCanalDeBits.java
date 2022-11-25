package br.com.spedison.main.BC_Transformacoes_pixel;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.TransformacaoBitsEDesloca;

import java.util.List;

public class MainTransformacaoCanalDeBits {
    public static void main(String[] args) {
        var c = SistemaArquivoImagens.getInstance();
        var imgInicial = new Imagem(c.getDirBaseImagensEntrada("cafe1.jpg"));
        TransformadorPixel img0 = new TransformadorPixel(imgInicial);
        TransformadorPixel img1 = new TransformadorPixel(imgInicial);
        TransformadorPixel img2 = new TransformadorPixel(imgInicial);
        TransformadorPixel img3 = new TransformadorPixel(imgInicial);
        TransformadorPixel img4 = new TransformadorPixel(imgInicial);
        TransformadorPixel img5 = new TransformadorPixel(imgInicial);
        TransformadorPixel img6 = new TransformadorPixel(imgInicial);
        TransformadorPixel img7 = new TransformadorPixel(imgInicial);

        List<TransformadorPixel> imgsTransformadas = List.of(img0, img1, img2, img3, img4, img4, img5, img6, img7);
        int[] mascara = {0xFF, 0xFF, 0xFF};
        int deslocamento = 0;
        for ( TransformadorPixel img : imgsTransformadas) {
            img.transforma(new TransformacaoBitsEDesloca(mascara, deslocamento));
            new ExibidorDeImagem("Mapa de Bits 0x" + Integer.toHexString(mascara[0]), new ImagemAjustadaTela(img,0.4), true);
            mascara[0] <<= 1;
            mascara[0] &= 0x000000FF;
            mascara[1] <<= 1;
            mascara[1] &= 0x000000FF;
            mascara[2] <<= 1;
            mascara[2] &= 0x000000FF;
            //deslocamento++;
        }
    }
}

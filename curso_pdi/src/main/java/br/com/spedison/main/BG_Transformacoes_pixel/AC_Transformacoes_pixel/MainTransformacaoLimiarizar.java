package br.com.spedison.main.BG_Transformacoes_pixel.AC_Transformacoes_pixel;

import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.TransformacaoLimiarizacao;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;

public class MainTransformacaoLimiarizar {
    public static void main(String[] args) {
        var c = SistemaArquivoImagens.getInstance();
        // Cargas da Imagens
        TransformadorPixel imgLimiarizada = new TransformadorPixel(c.getDirBaseImagensEntrada("doc","0.jpg"));
        TransformacaoLimiarizacao limiarizacao = new TransformacaoLimiarizacao(imgLimiarizada);
        limiarizacao.adicionaIntervalos(0,20,0);
        limiarizacao.adicionaIntervalos(31,imgLimiarizada.getValorMaximo(),imgLimiarizada.getValorMaximo());
        imgLimiarizada.transforma(limiarizacao);
        new ExibidorDeImagem("Imagem limiarizada", new ImagemAjustadaTela(imgLimiarizada), true);
    }
}

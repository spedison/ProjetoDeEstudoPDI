package br.com.spedison.main.BG_Transformacoes_pixel.AC_Transformacoes_pixel;

import br.com.spedison.biblioteca_pdi.base.HistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi_swing.gui.MostraHistogramaImagem;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.TransformacaoLinear;

public class MainTransformacaoLinear {
    public static void main(String[] args) {
        var c = SistemaArquivoImagens.getInstance();
        TransformadorPixel img = new TransformadorPixel(c.getDirBaseImagensEntrada("cafe1.jpg"));
        Imagem imgOriginal = img.clone();
        TransformacaoLinear transformacaoLinear = new TransformacaoLinear();
        transformacaoLinear.setSoma(-50.0);
        transformacaoLinear.setMultiplicador(1.3);

        img.transforma(transformacaoLinear);
        img.salva(c.getDirBaseImagensSaida("CAFE_TransformacaoLinear.jpg"));
        HistogramaImagem histogramaImagemTransformada = new HistogramaImagem();
        histogramaImagemTransformada.processaHistograma(img);

        HistogramaImagem histogramaImagemOriginal = new HistogramaImagem();
        histogramaImagemOriginal.processaHistograma(imgOriginal);

        MostraHistogramaImagem mostraHistogramaImagem = new MostraHistogramaImagem(histogramaImagemTransformada, "Café1 - " + transformacaoLinear.toString(), MostraHistogramaImagem.TipoGrafico.Contagem, 1000, 600);
        mostraHistogramaImagem.Show("Histograma Café Ajustado", false, true);

        MostraHistogramaImagem mostraHistogramaImagemOriginal = new MostraHistogramaImagem(histogramaImagemOriginal, "Café1 Original", MostraHistogramaImagem.TipoGrafico.Contagem, 1000, 600);
        mostraHistogramaImagemOriginal.Show("Histograma Café Original", false, true);

        new ExibidorDeImagem("Café Original", new ImagemAjustadaTela(imgOriginal),  true);
        new ExibidorDeImagem("Café transformado", new ImagemAjustadaTela(img),  true);
    }
}

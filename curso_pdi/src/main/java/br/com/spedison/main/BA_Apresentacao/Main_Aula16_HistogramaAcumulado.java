package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.HistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.TransformacaoHistogramaProbabilidadeAcumulada;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi_swing.gui.MostraHistogramaImagem;

public class Main_Aula16_HistogramaAcumulado {
    public static void main(String[] args) {

        SistemaArquivoImagens cp = SistemaArquivoImagens.getInstance();
        Imagem i = new Imagem(cp.getDirBaseImagensEntrada("cafe1.jpg"));
        HistogramaImagem hi = new HistogramaImagem();
        hi.processaHistograma(i);

        MostraHistogramaImagem mhi = new MostraHistogramaImagem(hi, "Cafe1 - Probabilidade Normal", MostraHistogramaImagem.TipoGrafico.Probabilidade);
        mhi.Show("Café", false, true);

        MostraHistogramaImagem mhi2 = new MostraHistogramaImagem(hi, "Cafe1 - Probabilidade Acumulado", MostraHistogramaImagem.TipoGrafico.Probabilidade);
        mhi2.Show("Cafe", true, true);


        TransformadorPixel tp = new TransformadorPixel(i);
        TransformacaoHistogramaProbabilidadeAcumulada tnh = new TransformacaoHistogramaProbabilidadeAcumulada();
        tp.transforma(tnh);
        hi.processaHistograma(tp);
        MostraHistogramaImagem mhi3 = new MostraHistogramaImagem(hi, "Cafe1 Normalizado - Probabilidade Acumulado", MostraHistogramaImagem.TipoGrafico.Probabilidade);
        mhi2.Show("Cafe Acc Normalizado", true, true);

        MostraHistogramaImagem mhi4 = new MostraHistogramaImagem(hi, "Cafe1 Normalizado - Probabilidade", MostraHistogramaImagem.TipoGrafico.Probabilidade);
        mhi2.Show("Cafe Normalizado", false, true);


        new ExibidorDeImagem("Imagem Normalizada", new ImagemAjustadaTela(tp,0.8), true);
        new ExibidorDeImagem("Imagem Original", new ImagemAjustadaTela(i,0.8), true);


    }
}

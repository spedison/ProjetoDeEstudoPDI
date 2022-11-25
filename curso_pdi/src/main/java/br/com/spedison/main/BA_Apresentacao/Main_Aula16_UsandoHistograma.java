package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.HistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi_swing.gui.MostraHistogramaImagem;

public class Main_Aula16_UsandoHistograma {
    public static void main(String[] args) {

        SistemaArquivoImagens cp = SistemaArquivoImagens.getInstance();
        Imagem i = new Imagem(cp.getDirBaseImagensEntrada("cafe1.jpg"));
        HistogramaImagem hi = new HistogramaImagem();
        hi.processaHistograma(i);

        MostraHistogramaImagem mhi = new MostraHistogramaImagem(hi, "Cafe1", MostraHistogramaImagem.TipoGrafico.Probabilidade);
        mhi.Show("Café", false, true);

        MostraHistogramaImagem mhi2 = new MostraHistogramaImagem(hi, "Cafe1");
        mhi2.Show("Cafe", false, true);

    }
}

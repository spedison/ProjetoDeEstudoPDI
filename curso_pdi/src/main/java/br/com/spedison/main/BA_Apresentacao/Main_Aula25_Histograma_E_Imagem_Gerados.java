package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi_swing.gui.MostraHistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.HistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;

public class Main_Aula25_Histograma_E_Imagem_Gerados {
    public static void main(String[] args) {
        var cp = SistemaArquivoImagens.getInstance();
        String nomeArquivo =
                cp.getInstance().
                        getDirBaseImagensEntrada("cafe1.jpg");
        System.out.println(nomeArquivo);
        Imagem i = new Imagem(cp.getDirBaseImagensEntrada("cafe1.jpg"));
        HistogramaImagem hi = new HistogramaImagem();
        hi.processaHistograma(i);

        MostraHistogramaImagem mhi = new MostraHistogramaImagem(hi, "Cafe1", MostraHistogramaImagem.TipoGrafico.Probabilidade);
        mhi.Show("Café1 -Probabilidade", false, true);

        MostraHistogramaImagem mhi2 = new MostraHistogramaImagem(hi, "Cafe1", MostraHistogramaImagem.TipoGrafico.Contagem);
        mhi2.Show("Café1 -Contagem", false, true);

        /*/HistogramaImagem hi2 = new HistogramaImagem("sai3.jpg");
        hi2.processaHistograma();

        MostraHistogramaImagem m = new MostraHistogramaImagem(hi2, "Sai3", MostraHistogramaImagem.TipoGrafico.Contagem);
        m.Show("Janela da Imagem sai3.jpg", true);

        MostraHistogramaImagem mhi3 = new MostraHistogramaImagem(hi2, "Olá", MostraHistogramaImagem.TipoGrafico.Probabilidade);
        mhi3.Show("Olá", true);
*/

    }
}
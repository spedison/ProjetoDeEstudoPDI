package br.com.spedison.main.BH_TransformacaoEspacial;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.FlipRodaImagem;
import br.com.spedison.biblioteca_pdi_javafx.gui.ExibidorListaDeImagens;

public class Main_Aula01_FlipSimples extends ExibidorListaDeImagens {
    public static void main(String[] args) {

        var sai = SistemaArquivoImagens.getInstance();
        Imagem img = new Imagem(sai.getDirBaseImagensEntrada("cafe1.jpg"));

        ExibidorListaDeImagens.adicionaImagem("Café 1", img);

        var fri = new FlipRodaImagem(img);
        fri.fliph();
        ExibidorListaDeImagens.adicionaImagem("Café 1 - FlipH", fri);

        fri.fliph();
        ExibidorListaDeImagens.adicionaImagem("Café 1 - FlipH - FlipH", fri);

        fri = new FlipRodaImagem(img);
        fri.flipv();
        ExibidorListaDeImagens.adicionaImagem("Café 1 - FlipV", fri);

        fri.flipv();
        ExibidorListaDeImagens.adicionaImagem("Café 1 - FlipV - FlipV", fri);

        fri = new FlipRodaImagem(img);
        fri.roda90gHorario();
        ExibidorListaDeImagens.adicionaImagem("Café 1 - Roda 90°", fri);

        fri.roda90gHorario();
        ExibidorListaDeImagens.adicionaImagem("Café 1 - Roda 180°", fri);

        fri.roda90gHorario();
        ExibidorListaDeImagens.adicionaImagem("Café 1 - Roda 270°", fri);

        fri.roda90gHorario();
        ExibidorListaDeImagens.adicionaImagem("Café 1 - Roda 360°", fri);

        ExibidorListaDeImagens.mostraJanela();
    }

}

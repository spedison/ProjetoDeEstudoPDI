package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.HistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.MatrizIntImagem;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.FlipRodaImagem;

import java.awt.*;

public class Main_Aula15_Histograma_Excel {
    public static void main(String[] args) {
        var c = SistemaArquivoImagens.getInstance();

        var nomeImagem = "texto_preto_branco_vermelho.jpg";
        var a = new Imagem();
        a.escreveTexto("Texto de Teste", 10, 10);
        a.setColor(new Color(255, 0, 0));
        a.escreveTexto("Vermelho", 10, 40);
        a.salva(c.getDirBaseImagensSaida(nomeImagem));

        var imprime = new MatrizIntImagem(a);
        imprime.imprimeMatrix(Imagem.Vermelho);
        imprime.imprimeMatrix(Imagem.Verde);
        imprime.imprimeMatrix(Imagem.Azul);


        var b = new Imagem(c.getDirBaseImagensSaida(nomeImagem));
        var hist = new HistogramaImagem();
        hist.processaHistograma(b);
        System.out.println("Inicio Histograma " + nomeImagem);
        hist.imprimeHistogramaTabular();
        System.out.println("Fim Histograma " + nomeImagem);

        var bFlipada = new FlipRodaImagem(b);
        bFlipada.flipv();
        bFlipada.salva(c.getDirBaseImagensSaida("flipv-" + nomeImagem));
        bFlipada.fliph();
        bFlipada.salva(c.getDirBaseImagensSaida("flipv-fliph-" + nomeImagem));

        bFlipada.copiaImagem(b);
        bFlipada.inverteXY();
        bFlipada.salva(c.getDirBaseImagensSaida("invertexy-" + nomeImagem));

        var docParaRodar90Graus = new FlipRodaImagem(c.getDirBaseImagensEntrada("documento2.jpg"));

        docParaRodar90Graus.
                salva(c.getDirBaseImagensSaida("documento2_original.jpg")).
                roda90gHorario().
                salva(c.getDirBaseImagensSaida("documento2_90g.jpg")).
                roda90gHorario().
                salva(c.getDirBaseImagensSaida("documento2_180g.jpg")).
                roda90gHorario().
                salva(c.getDirBaseImagensSaida("documento2_270g.jpg")).
                roda90gHorario().
                salva(c.getDirBaseImagensSaida("documento2_360g.jpg"));


        var docParaInverterXY = new FlipRodaImagem(docParaRodar90Graus);

        docParaRodar90Graus.roda90gHorario();
        docParaRodar90Graus.salva(c.getDirBaseImagensSaida("documento2-roda90horario.jpg"));

        docParaInverterXY.inverteXY();
        docParaInverterXY.salva(c.getDirBaseImagensSaida("documento2-invertexy.jpg"));


        var percentualDaTela = 0.8;

        new ExibidorDeImagem("Imagem Documento Rodado 90 Graus",
                new ImagemAjustadaTela(docParaRodar90Graus, percentualDaTela),
                true);
        new ExibidorDeImagem("Imagem Documento Rodado 180 Graus",
                new ImagemAjustadaTela(docParaRodar90Graus.roda90gHorario(), percentualDaTela),
                true);
        new ExibidorDeImagem("Imagem Documento Rodado 270 Graus",
                new ImagemAjustadaTela(docParaRodar90Graus.roda90gHorario(), percentualDaTela),
                true);
        new ExibidorDeImagem("Imagem Documento Rodado 360 Graus",
                new ImagemAjustadaTela(docParaRodar90Graus.roda90gHorario(), percentualDaTela),
                true);
        new ExibidorDeImagem("Imagem Original",
                new ImagemAjustadaTela(c.getDirBaseImagensEntrada("documento2.jpg"), percentualDaTela).ajustaATela(),
                true);
        new ExibidorDeImagem("Imagem Documento para inverter XY",
                new ImagemAjustadaTela(docParaInverterXY, percentualDaTela),
                true);

    }
}
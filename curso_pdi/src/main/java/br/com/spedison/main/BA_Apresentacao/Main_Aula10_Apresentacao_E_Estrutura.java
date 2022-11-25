package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.*;
import br.com.spedison.biblioteca_pdi.base.operacoes_entre_imagens.OperacaoEntreImagens;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;

import java.awt.*;


public class Main_Aula10_Apresentacao_E_Estrutura {
    public static void main(String[] args) {
        var c = SistemaArquivoImagens.getInstance();

        var a = new OperadorEntreImagens();
        a.setFonte(new Font("Arial", Font.ITALIC, 15));
        a.escreveTexto("Texto de Teste", 40, 40);
        a.salva(c.getDirBaseImagensSaida("arquivo_com_1_texto.jpg"));

        var b = new Imagem();
        b.setFonte(new Font("Arial", Font.BOLD, 15))
                .setColor(new Color(255, 0, 0))
                .escreveTexto("Outro Texto...", 10, 140)
                .escreveTexto("...de Teste", 10, 180);

        var d = new ImagemTamanhoAjustado(b, 1.65);
        a
                .operacaoNaImagem(d, OperacaoEntreImagens.Soma.getOperacao())
                .salva(
                        c.getDirBaseImagensSaida("arquivo_com_2_textos.jpg"));

        var imprime = new MatrizIntImagem(a);
        imprime.imprimeMatrix(Imagem.Vermelho).
                imprimeMatrix(Imagem.Verde).
                imprimeMatrix(Imagem.Azul);

        double fracaoDaTela = 0.96;
        double reducao = 0.3;
        double ampliacao = 1.4;

        new ExibidorDeImagem("Imagem Original", imprime, true);
        new ExibidorDeImagem("Ajustado a %.0f %% da Tela".formatted(fracaoDaTela * 100), new ImagemAjustadaTela(imprime, fracaoDaTela), true);
        new ExibidorDeImagem("Imagem reduzida de %.0f %% do Original".formatted((1.0 - reducao) * 100.0), new ImagemTamanhoAjustado(imprime, reducao), true);
        new ExibidorDeImagem("Imagem aumentada em %.0f %% do Original".formatted((ampliacao - 1.0) * 100.0), new ImagemTamanhoAjustado(imprime, ampliacao), true);
    }
}
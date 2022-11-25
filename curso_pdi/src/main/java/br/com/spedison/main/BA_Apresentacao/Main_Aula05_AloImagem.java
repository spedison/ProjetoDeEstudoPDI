package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.*;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;

import java.awt.*;
import java.nio.file.Paths;


public class Main_Aula05_AloImagem {
    public static void main(String[] args) {

        // Carrega o sistema de aquivos
        SistemaArquivoImagens c = SistemaArquivoImagens.getInstance();

        // Define o caminho completo do arquivo de saída.
        var nomeArquivo = c.getDirBaseImagensSaida("arquivo_com_1_texto.jpg");

        // Cria imagem vazia com o tamanho de 80 x 100
        Imagem a = new Imagem(80,100);
        // Coloca a cor azul no fundo
        a.pintaFundo(Color.BLUE.getRGB());
        // Define uma fonte "Arial" com tamanho de 9
        a.setFonte(new Font("Arial", Font.ITALIC, 9));
        // Escreve o texto
        a.escreveTexto("Texto de Teste", 0, 10);

        // Essa é uma outra forma de processar as imagens (sem repetir o nome das variáveis)

        a.setFonte(new Font("Arial", Font.BOLD, 9)) // Define uma fonte e tamamnho
                .setColor(new Color(255, 0, 30)) // Define uma outra cor (Vermelho)
                .escreveTexto("Outro Texto...", 0, 40) // Escreve 2 linhas de texto
                .escreveTexto("...de Teste", 0, 50);

        Paths.get(nomeArquivo).toFile().delete(); // Apaga arquivos previamente existente.
        a.salva(nomeArquivo); // Grava arquivo

        var imprime = new MatrizIntImagem(a); /// Cria uma matriz baseada na imagem
        imprime.imprimeMatrix(Imagem.Vermelho). // Imrime a matriz R - G - B.
                imprimeMatrix(Imagem.Verde).
                imprimeMatrix(Imagem.Azul);

        // Mas o melhor mesmo é ver a imagem da forma que é, na tela.
        new ExibidorDeImagem("Imagem Gerada", a, true);
        new ExibidorDeImagem("Imagem Gerada", new ImagemAjustadaTela(a, 0.70), true);
    }
}
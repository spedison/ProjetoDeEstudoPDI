package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.ArranjoEntreImagensMatriz;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;

import java.awt.*;
import java.util.Arrays;

public class Main_Aula40_GridImagem {
    public static void main(String[] args) {
        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance(); // Trabalha com sistema de arquivos de imagens.

        var arquivosF = sai.getListaArquivosImagensEntrada(SistemaArquivoImagens.extensaoImagens, "grid", "foto2"); // Lê a lista de arquivos do Diretório entrada/grid.
        int tamanhoDir = sai.getDirBaseImagensEntrada("grid", "foto2").length() + 1;  // Para remover o nome do diretório
        final int[] pos = {0}; // Contador.

        var imagemList = Arrays.stream(arquivosF). // Roda a lista de imagens.
                map(f -> f.toString()). // Pega o nome do arquivo.
                sorted().  // Ordena por nome de arquivo
                map(f -> new Imagem(f)). // Converte texto em Imagem
                map(img ->  // Processa: Imagem -> (Coloca um texto vermelho na imagem) -> Imagem
                img.
                        setFonte(new Font("Arial", Font.BOLD, 170)).
                        setColor(Color.RED).
                        escreveTexto("Arquivo [" + (++pos[0]) + "] : " + img.getNomeArquivo().substring(tamanhoDir), 100, 170)).
                toList(); // Converte em lista de imagens.

        ArranjoEntreImagensMatriz arranjoMatrixImagens = new ArranjoEntreImagensMatriz(imagemList); // Cria um arranjo com a lista de imagens.
        arranjoMatrixImagens.
                setLinhas(5).    // Linhas do Grid.
                setColunas(5).  // Colunas do Grid
                setEspacoX(20). // Posso Sobreescrever pedaços das imagens com Bordas negativas (Horizontalmente, Linhas verticais.)
                setEspacoY(20). // ou fazer um Espaço grosso nas Linhas (Verticalmente)
                setCorDeFundo(Color.cyan.getRGB()). // Define a cor de fundo
                processaImagens(); // Monta a imagem com todas as imagens na forma de Grid.

        new ExibidorDeImagem("Colocando Matrix %d x %d.".
                formatted(arranjoMatrixImagens.getLinhas(), arranjoMatrixImagens.getColunas()),
                new ImagemAjustadaTela(arranjoMatrixImagens, 0.97), true);


    }
}

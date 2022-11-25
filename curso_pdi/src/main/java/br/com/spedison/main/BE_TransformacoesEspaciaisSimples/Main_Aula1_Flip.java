package br.com.spedison.main.BE_TransformacoesEspaciaisSimples;

import br.com.spedison.biblioteca_pdi.base.*;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.FlipRodaImagem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main_Aula1_Flip {
    public static void main(String[] args) {
        var sai = SistemaArquivoImagens.getInstance();

        // Cria diretório de saida e define o nome base do Arquivo.
        var saidaAulaFlip = sai.getDirBaseImagensSaida("saida_aula_flip", "aula1");
        var nomeArquivoReferencia = "saida";
        Paths.get(saidaAulaFlip).toFile().mkdirs();
        sai.removeArquivosSaida("saida_aula_flip", "aula1"); // Se tiver arquivos ele remove.

        // Cria imagem Base.
        var a = new Imagem(300, 400, Color.BLUE.getRGB(), BufferedImage.TYPE_INT_RGB);
        a.setColor(Color.white);
        a.escreveTexto("Texto de Teste", 5, 10);
        a.setColor(Color.RED);
        a.setFonte(new Font("Arial", Font.BOLD, 18));
        a.escreveTexto("Outro texto...", 5, 50);
        a.escreveTexto("...Mais uma vez", 5, 70);
        a.escreveTexto("Final.", 5, 390);
        a.salva(sai.getDirBaseImagensSaida("saida_aula_flip", "aula1", nomeArquivoReferencia + ".jpg"));

        var c = new FlipRodaImagem(a);
        c.flipv();
        c.salva(sai.getDirBaseImagensSaida("saida_aula_flip", "aula1", nomeArquivoReferencia + "-flipv.jpg"));

        c = new FlipRodaImagem(a);
        c.fliph();
        c.salva(sai.getDirBaseImagensSaida("saida_aula_flip", "aula1", nomeArquivoReferencia + "-fliph.jpg"));

        c = new FlipRodaImagem(a);
        c.fliph();
        c.flipv();
        c.salva(sai.getDirBaseImagensSaida("saida_aula_flip", "aula1", nomeArquivoReferencia + "-fliph-flipv.jpg"));

        c = new FlipRodaImagem(a);
        c.flipv();
        c.fliph();
        c.salva(sai.getDirBaseImagensSaida("saida_aula_flip", "aula1", nomeArquivoReferencia + "-flipv-fliph.jpg"));

        c = new FlipRodaImagem(a);
        c.inverteXY();
        c.salva(sai.getDirBaseImagensSaida("saida_aula_flip", "aula1", nomeArquivoReferencia + "-inverte-xy.jpg"));

        var arquivosF = sai.getListaArquivosImagensSaida(SistemaArquivoImagens.extensaoImagens, "saida_aula_flip", "aula1"); // Lê a lista de arquivos do Diretório entrada/grid.
        int tamanhoDir = sai.getDirBaseImagensEntrada("saida_aula_flip", "aula1").length() - 1;  // Para remover o nome do diretório
        final int[] pos = {0}; // Contador.

        var imagemList = Arrays.stream(arquivosF). // Roda a lista de imagens.
                map(f -> f.toString()). // Pega o nome do arquivo.
                sorted().  // Ordena por nome de arquivo
                map(f -> new Imagem(f)). // Converte texto em Imagem
                map(img ->  // Processa: Imagem -> (Coloca um texto vermelho na imagem) -> Imagem
                img.
                        setFonte(new Font("Arial", Font.BOLD, 17)).
                        setColor(Color.RED).
                        escreveTexto("Arquivo [" + (++pos[0]) + "] : " + img.getNomeArquivo().substring(tamanhoDir).replace(".jpg",""), 10, img.getAltura()/2)).
                toList(); // Converte em lista de imagens.


        ArranjoEntreImagensMatriz arranjoMatrixImagens = new ArranjoEntreImagensMatriz(imagemList); // Cria um arranjo com a lista de imagens.
        arranjoMatrixImagens.
                setLinhas(1).    // Linhas do Grid.
                setColunas(6).  // Colunas do Grid
                setEspacoX(5). // Posso Sobreescrever pedaços das imagens com Bordas negativas (Horizontalmente, Linhas verticais.)
                setEspacoY(5). // ou fazer um Espaço grosso nas Linhas (Verticalmente)
                setCorDeFundo(Color.cyan.getRGB()). // Define a cor de fundo
                processaImagens(); // Monta a imagem com todas as imagens na forma de Grid.

        arranjoMatrixImagens.salva(sai.getDirBaseImagensSaida( "teste.jpg"));

        new ExibidorDeImagem("Colocando Matrix %d x %d.".
                formatted(arranjoMatrixImagens.getLinhas(), arranjoMatrixImagens.getColunas()),
                new ImagemAjustadaTela(arranjoMatrixImagens, 0.90), true);


    }
}

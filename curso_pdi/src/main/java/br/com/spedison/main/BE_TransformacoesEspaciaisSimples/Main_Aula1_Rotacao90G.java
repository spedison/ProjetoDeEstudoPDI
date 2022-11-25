package br.com.spedison.main.BE_TransformacoesEspaciaisSimples;

import br.com.spedison.biblioteca_pdi.base.ArranjoEntreImagensMatriz;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.FlipRodaImagem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main_Aula1_Rotacao90G {
    public static void main(String[] args) {
        var sai = SistemaArquivoImagens.getInstance();

        // Cria diretório de saida e define o nome base do Arquivo.
        var saidaAulaFlip = sai.getDirBaseImagensSaida("saida_aula_flip", "aula2");
        var nomeArquivoReferencia = "saida";
        Paths.get(saidaAulaFlip).toFile().mkdirs();
        sai.removeArquivosSaida("saida_aula_flip", "aula2"); // Se tiver arquivos ele remove.

        // Cria imagem Base.
        var a = new Imagem(300, 400, Color.BLUE.getRGB(), BufferedImage.TYPE_INT_RGB);
        a.setColor(Color.white);
        a.escreveTexto("Texto de Teste Rotação", 5, 10);
        a.setColor(Color.RED);
        a.setFonte(new Font("Arial", Font.BOLD, 18));
        a.escreveTexto("Outro texto...", 5, 50);
        a.escreveTexto("...Mais uma vez...", 5, 70);
        a.escreveTexto("...Rodando", 5, 100);
        a.salva(sai.getDirBaseImagensSaida("saida_aula_flip", "aula2", nomeArquivoReferencia + ".jpg"));

        var c = new FlipRodaImagem(a);
        c.roda90gHorario();
        c.salva(sai.getDirBaseImagensSaida("saida_aula_flip", "aula2", nomeArquivoReferencia + "gira-90g.jpg"));

        c = new FlipRodaImagem(a);
        c.roda90gHorario();
        c.roda90gHorario();
        c.salva(sai.getDirBaseImagensSaida("saida_aula_flip", "aula2", nomeArquivoReferencia + "gira-180g.jpg"));

        c = new FlipRodaImagem(a);
        c.roda90gHorario();
        c.roda90gHorario();
        c.roda90gHorario();
        c.salva(sai.getDirBaseImagensSaida("saida_aula_flip", "aula2", nomeArquivoReferencia + "gira-270g.jpg"));

        c = new FlipRodaImagem(a);
        c.roda90gHorario();
        c.roda90gHorario();
        c.roda90gHorario();
        c.roda90gHorario();
        c.salva(sai.getDirBaseImagensSaida("saida_aula_flip", "aula2", nomeArquivoReferencia + "gira-360g.jpg"));

        var arquivosF = sai.getListaArquivosImagensSaida(SistemaArquivoImagens.extensaoImagens, "saida_aula_flip", "aula2"); // Lê a lista de arquivos do Diretório entrada/grid.
        int tamanhoDir = sai.getDirBaseImagensEntrada("saida_aula_flip", "aula2").length() - 1;  // Para remover o nome do diretório
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
                setLinhas(2).    // Linhas do Grid.
                setColunas(5).  // Colunas do Grid
                setEspacoX(5). // Posso Sobreescrever pedaços das imagens com Bordas negativas (Horizontalmente, Linhas verticais.)
                setEspacoY(5). // ou fazer um Espaço grosso nas Linhas (Verticalmente)
                setCorDeFundo(Color.cyan.getRGB()). // Define a cor de fundo
                processaImagens(); // Monta a imagem com todas as imagens na forma de Grid.

        new ExibidorDeImagem("Colocando Matrix %d x %d.".
                formatted(arranjoMatrixImagens.getLinhas(), arranjoMatrixImagens.getColunas()),
                new ImagemAjustadaTela(arranjoMatrixImagens, 0.60), true);


    }
}

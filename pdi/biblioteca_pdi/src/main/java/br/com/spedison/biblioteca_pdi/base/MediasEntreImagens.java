package br.com.spedison.biblioteca_pdi.base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class MediasEntreImagens {

    private int xTam;
    private int yTam;

    private List<Imagem> listaImagensMedia;
    private Imagem resultado;

    public Imagem calculaMedia() {
        assert listaImagensMedia != null && listaImagensMedia.size() > 1 : "Lista de imagens deve ter mais que 1 elemento.";
        calculaDimensaoResultado();
        somaETiraMedia();
        return resultado;
    }

    private void calculaDimensaoResultado() {
        xTam = listaImagensMedia.stream().mapToInt(Imagem::getLargura).min().getAsInt();
        yTam = listaImagensMedia.stream().mapToInt(Imagem::getAltura).min().getAsInt();
    }

    private void somaETiraMedia() {
        // Soma todos os valores da Imagem
        final long[][][] acc = new long[3][xTam][yTam];
        listaImagensMedia.stream()
                .map(Imagem::guardaOrigem)
                .map(i -> i.setOrigemY(0).setOrigemY(0))
                .forEach(imagem -> {
                    IntStream.range(0, xTam)
                            .forEach(posX -> {
                                IntStream.range(0, yTam)
                                        .forEach(posY -> {
                                            int[] meuPixel = imagem.getPixel(posX, posY);
                                            acc[Imagem.Vermelho][posX][posY] += meuPixel[Imagem.Vermelho];
                                            acc[Imagem.Verde][posX][posY] += meuPixel[Imagem.Verde];
                                            acc[Imagem.Azul][posX][posY] += meuPixel[Imagem.Azul];
                                        });
                            });
                    imagem.restauraOrigem();
                });

        resultado = new Imagem(xTam, yTam);
        resultado
                .getStreamLargura()
                .parallel()
                .forEach(posX -> {
                    resultado
                            .getStreamAltura()
                            .parallel()
                            .forEach(posY -> {
                                int[] meuPixel = {
                                        resultado.limitaValores((int) (acc[Imagem.Vermelho][posX][posY] / listaImagensMedia.size())),
                                        resultado.limitaValores((int) (acc[Imagem.Verde][posX][posY] / listaImagensMedia.size())),
                                        resultado.limitaValores((int) (acc[Imagem.Azul][posX][posY] / listaImagensMedia.size()))
                                };
                                resultado.setPixel(posX, posY, meuPixel);
                            });
                });
    }

    public MediasEntreImagens(BufferedImage[] bi) {
        listaImagensMedia = Arrays.stream(bi).map(x -> new Imagem(x)).toList();
    }

    public MediasEntreImagens(String[] nomeArquivo) {
        listaImagensMedia = Arrays.stream(nomeArquivo).map(nome -> new Imagem(nome)).toList();
    }

    public MediasEntreImagens(String nomeDiretorio) {

        File dir = new File(nomeDiretorio);
        if (!(dir.exists() && dir.isDirectory() && dir.canRead())) {
            new RuntimeException("Diretório não existe ou não tem acesso de leitura");
            return;
        }

        String[] arquivos = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                final boolean ret[] = {false};
                Arrays.stream(SistemaArquivoImagens.extensaoImagens).
                        filter(x -> ret[0] == false).
                        forEach(ex -> ret[0] |= (name.endsWith(ex)));
                return ret[0];
            }
        });
        listaImagensMedia = Arrays.stream(arquivos).map(nome -> new Imagem(nome)).toList();
    }

    public MediasEntreImagens() {
        listaImagensMedia = new ArrayList<Imagem>();
    }

    public MediasEntreImagens(Imagem[] img) {
        listaImagensMedia = Arrays.asList(img);
    }

    public MediasEntreImagens(List<Imagem> img) {
        listaImagensMedia = img;
    }

    public MediasEntreImagens salvaResultado(String nome) {
        Objects.requireNonNull(resultado, "O resultado ainda não existe para ser calculado.");
        resultado.salva(nome);
        return this;
    }

    public Imagem getResultado() {
        return resultado;
    }
}
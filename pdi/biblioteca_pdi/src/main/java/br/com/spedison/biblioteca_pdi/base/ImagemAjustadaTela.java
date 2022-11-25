package br.com.spedison.biblioteca_pdi.base;

import br.com.spedison.biblioteca_pdi.base.enuns.Interpolacao;

import java.awt.*;

/***
 * Essa classe é um facilitador para exibi a imagem na tala.
 * Como os testes dependeriam da resolução do monitor, não realizaremos testes unitários com ela.
 * Os testes serão realizados com a classe mae.
 */
public class ImagemAjustadaTela extends ImagemTamanhoAjustado {

    private double percentualTela = 0.96;

    public ImagemAjustadaTela() {

    }

    public ImagemAjustadaTela(Interpolacao interpolacao) {
        super();
        this.interpolacao = interpolacao;
    }

    public ImagemAjustadaTela(String nomeArquivo, Interpolacao interpolacao, double percentual) {
        super(nomeArquivo, interpolacao, 1.0, 1.0);
        this.percentualTela = percentual;
        ajustaATela();
    }

    public ImagemAjustadaTela(String nomeArquivo, double percentual) {
        super(nomeArquivo, 1.0, 1.0);
        this.percentualTela = percentual;
        ajustaATela();
    }

    public ImagemAjustadaTela(Imagem imagem) {
        this(imagem, Interpolacao.Linear, 0.96);
    }

    public ImagemAjustadaTela(Imagem imagem, double percentualTela) {
        this(imagem, Interpolacao.Linear, percentualTela);
    }

    public ImagemAjustadaTela(Imagem imagem, Interpolacao interpolalacao, double percentualTela) {
        super(imagem);
        this.interpolacao = interpolalacao;
        this.percentualTela = percentualTela;
        ajustaATela();
    }

    public ImagemAjustadaTela(String nomeArquivo, Interpolacao intepolacao) {
        leArquivo(nomeArquivo);
        this.interpolacao = intepolacao;
        ajustaATela();
    }

    public ImagemAjustadaTela ajustaATela() {

        /**
         * Essa é a parte mais importante dessa classe, pois pega o tamanho da tela atual.
         */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        int screenX = (int) (screenDimension.getWidth() * percentualTela);
        int screenY = (int) (screenDimension.getHeight() * percentualTela);

        /**
         * Daqui para frente ele trata as dimensões para que qq tamanho de imagem caiba inteiramente na tela.
         */
        // Calcula a nova Proporção
        int currentImgX = getLargura();
        int currentImgY = getAltura();

        double p1 = ((double) screenX) / ((double) currentImgX);
        double p2 = ((double) screenY) / ((double) currentImgY);
        double p = 0.0;

        // Dimensões da futura imagem com P1.
        int newXP1 = (int) (p1 * currentImgX);
        int newYP1 = (int) (p1 * currentImgY);
        // Se está dentro do tamanho da tela
        if (newXP1 <= screenX && newYP1 <= screenY) {
            p = p1;
        } else {
            p = p2;
        }

        super.setPercentualX(p);
        super.setPercentualY(p);
        super.ajustaTamanho();
        return this;
    }
}
/* Outra forma de fazer o Resize de uma imagem
 * Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
 * BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
 * outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
 */

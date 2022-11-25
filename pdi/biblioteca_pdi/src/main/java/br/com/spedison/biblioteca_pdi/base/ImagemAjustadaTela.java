package br.com.spedison.biblioteca_pdi.base;

import br.com.spedison.biblioteca_pdi.base.enuns.Interpolacao;

import java.awt.*;

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

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        int screenX = (int) (screenDimension.getWidth() * percentualTela);
        int screenY = (int) (screenDimension.getHeight() * percentualTela);

        // Calcula a nova Proporção
        int currentImgX = getLargura();
        int currentImgY = getAltura();

        double p1 = ((double) screenX) / ((double) currentImgX);
        double p2 = ((double) screenY) / ((double) currentImgY);
        double p = 1.0;

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
/*** Outra forma de fazer o Resize de uma imagem
 * Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
 * BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
 * outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
 */

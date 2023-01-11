package br.com.spedison.biblioteca_pdi.base;


import br.com.spedison.biblioteca_pdi.base.enuns.Interpolacao;
import br.com.spedison.usogeral.Arredondador;

import java.awt.*;

/***
 * Pega uma imagem carregada e ajusta o tamanho usando a bilioteca java (Sem as transformações implementadas)
 */
public class ImagemTamanhoAjustado extends Imagem {

    private double percentualX = 0.5;
    private double percentualY = 0.5;
    Interpolacao interpolacao;


    public ImagemTamanhoAjustado() {
        this.interpolacao = Interpolacao.Linear;
    }

    public ImagemTamanhoAjustado(Interpolacao interpolacao) {
        super();
        this.interpolacao = interpolacao;
    }

    public ImagemTamanhoAjustado(Imagem imagem) {
        super(imagem);
    }


    public ImagemTamanhoAjustado(Imagem imagem, double percentual) {
        this(imagem, percentual, percentual);
    }

    public ImagemTamanhoAjustado(Imagem imagem, double percentualX, double percentualY) {
        this(imagem, Interpolacao.Linear, percentualX, percentualY);
    }

    public ImagemTamanhoAjustado(Imagem imagem, Interpolacao tipoInterpolalacao, double percentualX, double percentualY) {
        super(imagem.clone());
        this.interpolacao = tipoInterpolalacao;
        this.percentualX = percentualX;
        this.percentualY = percentualY;
        ajustaTamanho();
    }

    public ImagemTamanhoAjustado(String nomeArquivo, double percentualX, double percentualY) {
        this(nomeArquivo, Interpolacao.Linear, percentualX, percentualY);
    }

    public ImagemTamanhoAjustado(String nomeArquivo, Interpolacao intepolacao, double percentualX, double percentualY) {
        super(nomeArquivo);
        this.interpolacao = intepolacao;
        this.percentualX = percentualX;
        this.percentualY = percentualY;
        ajustaTamanho();
    }

    public double getPercentualX() {
        return percentualX;
    }

    public void setPercentualX(double percentualX) {
        this.percentualX = Math.abs(percentualX);
    }

    public double getPercentualY() {
        return percentualY;
    }

    public void setPercentualY(double percentualY) {
        this.percentualY = Math.abs(percentualY);
    }

    public Interpolacao getInterpolacao() {
        return interpolacao;
    }

    public void setInterpolacao(Interpolacao interpolacao) {
        this.interpolacao = interpolacao;
    }

    public void ajustaTamanho() {

        // BufferedImage newImage = new BufferedImage(newImgX,newImgY,BufferedImage.TYPE_INT_RGB);
        Imagem img = new Imagem(Arredondador.arredonda(getLargura() * percentualX), Arredondador.arredonda(getAltura() * percentualY));
        Graphics2D g = (Graphics2D) img.getGraphics();

        switch (interpolacao) {
            case Linear ->
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            case Cubica ->
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            case IterpolacaoVizinhosProximos ->
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        }

        g.drawImage(getImageBuffer(), 0, 0, img.getLargura(), img.getAltura(), null);
        g.dispose();
        setImageBuffer(img.getImageBuffer());
    }
}
/*** Outra forma de fazer o Resize de uma imagem
 * Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
 * BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
 * outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
 */

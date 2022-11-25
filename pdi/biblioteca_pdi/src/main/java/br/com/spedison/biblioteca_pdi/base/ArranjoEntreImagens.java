package br.com.spedison.biblioteca_pdi.base;

import br.com.spedison.biblioteca_pdi.base.enuns.TipoArranjo;

import java.awt.image.BufferedImage;

public class ArranjoEntreImagens extends Imagem {


    public ArranjoEntreImagens(BufferedImage bi) {
        super(bi);
    }

    public ArranjoEntreImagens(String nomeArquivo) {
        this.leArquivo(nomeArquivo);
    }

    /***
     * Adiciona uma simples imagem no posicionamento vertical ou Horizontal da imagem atual.
     * @param imagem
     * @param tipoArranjo
     * @return O mesmo objeto, contendo as imagens já ajustadas.
     */
    public ArranjoEntreImagens adicionarImagem(Imagem imagem, TipoArranjo tipoArranjo, int corFundo) {

        int novoX = tipoArranjo == TipoArranjo.Horizontal ? this.getLargura() + imagem.getLargura() : Math.max(this.getLargura(), imagem.getLargura());
        int novoY = tipoArranjo == TipoArranjo.Vertical ? this.getAltura() + imagem.getAltura() : Math.max(this.getAltura(), imagem.getAltura());

        Imagem imgRet = new Imagem(novoX, novoY, corFundo);

        int xPos = 0;
        int yPos = 0;

        // Coloquei a primeira imagem
        imgRet.copiaImagemDe(this, xPos, yPos);

        // Coloco a imagem anexa.
        switch (tipoArranjo) {
            case Vertical -> yPos += this.getAltura();
            case Horizontal -> xPos += this.getLargura();
        }
        imgRet.copiaImagemDe(imagem, xPos, yPos);
        this.setImageBuffer(imgRet.getImageBuffer());
        return this;
    }

    public ArranjoEntreImagens() {
        super(1,1);
    }

    public ArranjoEntreImagens(Imagem img) {
        copiaImagem(img);
    }
}

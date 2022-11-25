package br.com.spedison.biblioteca_pdi_swing.gui;

import br.com.spedison.biblioteca_pdi.base.Imagem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class JImagePanel extends JPanel {

    private Imagem imagem;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        var s = getParent().getSize();
        setSize(s);
        g.drawImage(imagem.getImageBuffer(), 0, 0, imagem.getTamanho()[0], imagem.getTamanho()[1], new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
    }


    public JImagePanel(Imagem imagem) {
        this.imagem = imagem;
        this.setBounds(0, 0, imagem.getTamanho()[0], imagem.getTamanho()[1]);
    }

}

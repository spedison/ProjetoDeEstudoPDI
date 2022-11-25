package br.com.spedison.biblioteca_pdi_swing.gui;


import br.com.spedison.biblioteca_pdi.base.Imagem;
//import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
//import br.com.spedison.biblioteca_pdi.base.enuns.Interpolacao;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class ExibidorDeImagem extends JFrame {

    private Imagem imagem;
    private boolean exitOnClose;

    //Instancia um novo JPanel
    JImagePanel panel;

    JLabel label;

    public ExibidorDeImagem(String title, Imagem imagem, boolean exitOnClose) throws HeadlessException {
        super(title);
        /*setUndecorated(false);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        try {
            var p = UIManager.getDefaults();
            Iterator i = p.keys().asIterator();
            while (i.hasNext())
            {
                var chave = i.next();
                var valor = p.get(chave);
                System.out.println("Chave = %s , Valor = %s".formatted(chave.toString(), valor.toString()));
            }

            UIManager.LookAndFeelInfo [] uis = UIManager.getInstalledLookAndFeels();
            UIManager.setLookAndFeel(uis[2].getName());//put( "Tree.font", new Font( "Helvetica", Font.BOLD, 24 ) );
        }catch(Exception e){

        }*/
        this.imagem = imagem;
        this.exitOnClose = exitOnClose;
        if (exitOnClose)
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        else
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setSize(imagem.getTamanho()[0], imagem.getTamanho()[1]);

        panel = new JImagePanel(imagem);
        Insets decoration = this.getInsets();
        panel.setSize(imagem.getLargura(),imagem.getAltura());
        this.setSize(imagem.getLargura(),imagem.getAltura() + 37); // TODO: Encontrar o tamanho do Title Bar e Trocar pelo 37.
        this.getContentPane().add(panel);
        setResizable(false);
        setVisible(true);
        panel.repaint();
    }



}

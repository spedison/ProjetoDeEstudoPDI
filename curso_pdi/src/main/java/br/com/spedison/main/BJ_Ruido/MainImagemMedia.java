package br.com.spedison.main.BJ_Ruido;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.MediasEntreImagens;
import br.com.spedison.biblioteca_pdi.base.OperadorEntreImagens;
import br.com.spedison.biblioteca_pdi.base.ruido.GeradorRuidoGaussinano;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.TransformacaoLog;

import java.util.Arrays;

public class MainImagemMedia {

    public static void main(String[] args) {

        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        GeradorRuidoGaussinano grg = new GeradorRuidoGaussinano();
        OperadorEntreImagens imgOpera = new OperadorEntreImagens(sai.getDirBaseImagensEntrada("barras_rgb.jpg"));
        imgOpera.setValorMaximo(100);
        imgOpera.setValorMinimo(0);
        /*ImagemRuido imgRuido = new ImagemRuido(imgOpera.getLargura(),imgOpera.getAltura(), grg);
        imgRuido.geraImagem();

        OperacoesEntreImagens cafe  = new OperacoesEntreImagens(sai.getDirBaseImagensEntrada("cafe1.jpg"));
        new MostraImagem("Café sem ruido", new ImagemAjustadaTela(cafe,0.6), true);
        new MostraImagem("Café com ruido", new ImagemAjustadaTela(cafe.somaImagem((new ImagemRuido(cafe.getLargura(), cafe.getAltura(),grg)).geraImagem()),0.6), true);

        new MostraImagem("Sem Ruido", imgOpera, true);
        new MostraImagem("Somado com Ruido", imgOpera.clone().somaImagem(imgRuido), true);

*/
        var listaArquivos = sai.listaArquivosDir(SistemaArquivoImagens.extensaoImagens, sai.getDirBaseImagensEntrada("medias","foto1") );
        String [] listaArquivosStr = Arrays.stream(listaArquivos).map(x->x.toString()).toArray(String[]::new);

        var mediasEntreImagens = new MediasEntreImagens(listaArquivosStr);
        mediasEntreImagens.calculaMedia();

        //new MostraImagem("Imagem escuro[0] - Original", new ImagemAjustadaTela(listaArquivos[0].toString(),0.5), true);
        //new MostraImagem("Imagem escuro - Media", new ImagemAjustadaTela(mediasEntreImagens.getResultado(),0.5), true);

        TransformacaoLog transformacaoLog = new TransformacaoLog(70, 30.0, 12.0);
        TransformadorPixel imgLogMedias = new TransformadorPixel(mediasEntreImagens.getResultado());
        new ExibidorDeImagem("Média Transformada  : " + transformacaoLog.toString(), new ImagemAjustadaTela(imgLogMedias.transforma(transformacaoLog).copiaImagem(250,250, 1000,1000),.8), true);

        TransformadorPixel imgLogOriginal = new TransformadorPixel(new Imagem(listaArquivosStr[0]));
        new ExibidorDeImagem("Original Transformada  : " + transformacaoLog.toString(), new ImagemAjustadaTela(imgLogOriginal.transforma(transformacaoLog).copiaImagem(250,250, 1000, 1000),.8), true);

        Arrays.stream(listaArquivos).forEach(System.out::println);

    }

}

package br.com.spedison.main.BC_Transformacoes_pixel;

import br.com.spedison.biblioteca_pdi.base.HistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi_swing.gui.MostraHistogramaImagem;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.TransformacaoLinear;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.TransformacaoLog;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.TransformacaoRaiz;

public class MainTransformacaoRaiz {
    public static void main(String[] args) {
        var c = SistemaArquivoImagens.getInstance();

        // Cargas da Imagens
        TransformadorPixel imgLinear = new TransformadorPixel(c.getDirBaseImagensEntrada("ESCURO_1.jpg"));
        Imagem imgOriginal = imgLinear.clone();
        TransformadorPixel imgRaiz = new TransformadorPixel(imgLinear);
        TransformadorPixel imgLog = new TransformadorPixel(imgLinear);
        //TransformadorPixel imgLogInv = new TransformadorPixel(imgLinear);

        // Transformações
        TransformacaoLinear transformacaoLinear = new TransformacaoLinear();
        transformacaoLinear.setSoma(0.0);
        transformacaoLinear.setMultiplicador(1.6);

        TransformacaoRaiz transformacaoRaiz = new TransformacaoRaiz();
        transformacaoRaiz.setSoma(-10.0);
        transformacaoRaiz.setMultiplicador(27.0);
        transformacaoRaiz.setRaiz(2.1);

        TransformacaoLog transformacaoLog = new TransformacaoLog();
        transformacaoLog.setSoma(-30.0);
        transformacaoLog.setMultiplicador(45.0);
        transformacaoLog.setBaseLog(Math.exp(1.0) * 0.8);

        /*TransformacaoLogInverso transformacaoLogInverso = new TransformacaoLogInverso();
        transformacaoLogInverso.setBaseLogInverso(Math.exp(1.0)*.4);
        transformacaoLogInverso.setMultiplicador(.35);
        */

        // Processa o Histograma e Mostra os dados Linear
        imgLinear.transforma(transformacaoLinear);
        HistogramaImagem histogramaImagemTransformada = new HistogramaImagem();
        histogramaImagemTransformada.processaHistograma(imgLinear);
        MostraHistogramaImagem mostraHistogramaImagem = new MostraHistogramaImagem(histogramaImagemTransformada,
                "Escuro - " + transformacaoLinear.toString(),
                MostraHistogramaImagem.TipoGrafico.Contagem, 1000, 600);
        mostraHistogramaImagem.Show("Histograma Escuro Ajustado", false,true);
        new ExibidorDeImagem("Escuro transformado Linear", new ImagemAjustadaTela(imgLinear), true);

        HistogramaImagem histogramaImgOriginal = new HistogramaImagem();
        histogramaImgOriginal.processaHistograma(imgOriginal);
        MostraHistogramaImagem mostraHistogramaImagemOriginal = new MostraHistogramaImagem(histogramaImgOriginal,
                "Escuro Original", MostraHistogramaImagem.TipoGrafico.Contagem, 1000, 600);
        mostraHistogramaImagemOriginal.Show("Histograma Escuro Original", false, true);
        new ExibidorDeImagem("Escuro Original", new ImagemAjustadaTela(imgOriginal), true);

        imgRaiz.transforma(transformacaoRaiz);
        HistogramaImagem histogramaImgRaiz = new HistogramaImagem();
        histogramaImgRaiz.processaHistograma(imgRaiz);
        MostraHistogramaImagem mostraHistogramaImgRaiz = new MostraHistogramaImagem(histogramaImgRaiz,
                "Escuro - " + transformacaoRaiz.toString(), MostraHistogramaImagem.TipoGrafico.Contagem, 1000, 600);
        mostraHistogramaImgRaiz.Show("Histograma Escuro Ajustado Raiz", false, true);
        new ExibidorDeImagem("Escuro transformado Raiz", new ImagemAjustadaTela(imgRaiz), true);

        imgLog.transforma(transformacaoLog);
        HistogramaImagem histogramaImgLog = new HistogramaImagem();
        histogramaImgLog.processaHistograma(imgLog);
        MostraHistogramaImagem mostraHistogramaImgLog = new MostraHistogramaImagem(histogramaImgLog,
                "Escuro - " + transformacaoLog.toString(), MostraHistogramaImagem.TipoGrafico.Contagem, 1000, 600);
        mostraHistogramaImgLog.Show("Histograma Escuro Ajustado Log", false, true);
        new ExibidorDeImagem("Escuro transformado Log", new ImagemAjustadaTela(imgLog), true);

        /*imgLogInv.transforma(transformacaoLogInverso);
        HistogramaImagem histogramaImgLogInv = new HistogramaImagem(imgLogInv);
        histogramaImgLogInv.processaHistograma();
        MostraHistogramaImagem mostraHistogramaImgLogInv = new MostraHistogramaImagem(histogramaImgLogInv,
                "Escuro - " + transformacaoLogInverso.toString(), MostraHistogramaImagem.TipoGrafico.Contagem, 1000, 600);
        mostraHistogramaImgLogInv.Show("Histograma Escuro Ajustado LogInverso", true);
        new MostraImagem("Escuro transformado LogInv", new ImagemAjustadaTela(imgLogInv), true);
        */
    }
}

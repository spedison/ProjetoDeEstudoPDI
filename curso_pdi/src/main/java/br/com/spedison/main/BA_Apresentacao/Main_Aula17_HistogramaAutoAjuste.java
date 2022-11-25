package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.HistogramaImagem;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoBasico;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoInterface;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoQuadratica;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformadorConvolucao;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels.*;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.TransformacaoHistogramaPropocionalLimites;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi_swing.gui.MostraHistogramaImagem;

public class Main_Aula17_HistogramaAutoAjuste {

    public static void main(String[] args) {
        var cafeEntrada = SistemaArquivoImagens.getInstance().getDirBaseImagensEntrada("cafe1.jpg");

        Imagem imgOriginal = new Imagem(cafeEntrada);

        TransformacaoHistogramaPropocionalLimites transformacaoAutoAjuste = new TransformacaoHistogramaPropocionalLimites(false, 1./300.);
        TransformadorPixel imgTransformadaComAutoAjuste = new TransformadorPixel(cafeEntrada);

        imgTransformadaComAutoAjuste.transforma(transformacaoAutoAjuste);

        HistogramaImagem histOriginal = new HistogramaImagem();
        histOriginal.processaHistograma(imgOriginal);

        HistogramaImagem histAjustado = new HistogramaImagem();
        histAjustado.processaHistograma(imgTransformadaComAutoAjuste);

        new ExibidorDeImagem("Café Original", new ImagemAjustadaTela(imgOriginal,.8), true );
        new ExibidorDeImagem("Café Ajustado", new ImagemAjustadaTela(imgTransformadaComAutoAjuste,.8), true );

        MostraHistogramaImagem mhi = new MostraHistogramaImagem(histOriginal,"Histograma Original", MostraHistogramaImagem.TipoGrafico.Probabilidade);
        mhi.Show("Original",false,true);

        MostraHistogramaImagem mha = new MostraHistogramaImagem(histAjustado,"Histograma Original", MostraHistogramaImagem.TipoGrafico.Probabilidade);
        mha.Show("Ajustado",false,true);

        TransformadorConvolucao imgConvOriginal = new TransformadorConvolucao(imgOriginal);
        TransformadorConvolucao imgConvAltoContraste = new TransformadorConvolucao(imgTransformadaComAutoAjuste);

        TransformacaoConvolucaoInterface k = new TransformacaoConvolucaoBasico(TipoKernelLaplacianoEnum.OitoPontosNegativo);
        imgConvOriginal.transforma(k);
        imgConvAltoContraste.transforma(k);

        new ExibidorDeImagem("Laplaciano 8 - Café Original", new ImagemAjustadaTela(imgConvOriginal,.8), true );
        new ExibidorDeImagem("Laplaciano 8 - Café Ajustado", new ImagemAjustadaTela(imgConvAltoContraste,.8), true );


        TransformacaoPixelRGB transformacaoRoberts = new TransformacaoConvolucaoQuadratica(TipoKernelQuadraticoEnum.Sobel,1.0,0.0);

        TransformadorConvolucao imagemOriginalRoberts = new TransformadorConvolucao(imgOriginal);
        imagemOriginalRoberts.transforma(transformacaoRoberts);
        new ExibidorDeImagem("Roberts - Café Original", new ImagemAjustadaTela(imagemOriginalRoberts,.8), true );

        TransformadorConvolucao imagemNormalizadaRoberts = new TransformadorConvolucao(imgOriginal);
        imagemNormalizadaRoberts.transforma(transformacaoRoberts);
        new ExibidorDeImagem("Roberts - Café Normalizado", new ImagemAjustadaTela(imagemOriginalRoberts,.8), true );


    }

}

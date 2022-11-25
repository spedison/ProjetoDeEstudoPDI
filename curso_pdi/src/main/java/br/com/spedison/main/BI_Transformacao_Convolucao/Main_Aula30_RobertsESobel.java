package br.com.spedison.main.BI_Transformacao_Convolucao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformadorConvolucaoSomaComOriginal;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformadorConvolucao;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoQuadratica;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels.TipoKernelQuadraticoEnum;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;

public class Main_Aula30_RobertsESobel {

    public static void main(String[] args) {

        // Carga de constantes do Projeto.
        var c = SistemaArquivoImagens.getInstance();

        // Kernels usados.
        TransformacaoConvolucaoQuadratica transformadorKernelSobel = new TransformacaoConvolucaoQuadratica(TipoKernelQuadraticoEnum.Sobel,1.0,0.0);
        TransformacaoConvolucaoQuadratica transformadorKernelRoberts = new TransformacaoConvolucaoQuadratica(TipoKernelQuadraticoEnum.Roberts,1.0,0.0);

        // Carga das Imagens.
        Imagem imgOriginal = new Imagem(c.getDirBaseImagensEntrada("cafeteira_vermelha.png"));
        //imgOriginal.transforma(tc);
        TransformadorPixel imgSobel = new TransformadorConvolucaoSomaComOriginal(imgOriginal);
        // Definição e execução da Covolução
        imgSobel.transforma(transformadorKernelSobel);


        TransformadorPixel imgRobets = new TransformadorConvolucaoSomaComOriginal(imgOriginal);
        // Definição e execução da Covolução
        imgRobets.transforma(transformadorKernelRoberts);

        TransformadorPixel imgBordaSobel = new TransformadorConvolucao(imgOriginal);
        TransformadorPixel imgBordaRoberts = new TransformadorConvolucao(imgOriginal);

        imgBordaRoberts.transforma(transformadorKernelRoberts);
        imgBordaSobel.transforma(transformadorKernelSobel);

        //Mostra a Imagenm
        new ExibidorDeImagem("Café Original", imgOriginal, true);

        new ExibidorDeImagem("Imagem Realçada com Sobel", imgSobel, true);
        new ExibidorDeImagem("Imagem Realçada com Roberts", imgRobets, true);

        new ExibidorDeImagem("Imagem Bordas de Sobel", imgBordaSobel, true);
        new ExibidorDeImagem("Imagem Bordas de Roberts", imgBordaRoberts, true);
    }

}

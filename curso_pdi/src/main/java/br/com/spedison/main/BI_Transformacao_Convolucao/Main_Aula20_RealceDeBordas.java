package br.com.spedison.main.BI_Transformacao_Convolucao;

import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformadorConvolucaoSomaComOriginal;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoBasico;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels.TipoKernelLaplacianoEnum;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.TransformacaoCinza;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;

public class Main_Aula20_RealceDeBordas {

    public static void main(String[] args) {

        // Carga de constantes do Projeto.
        var c = SistemaArquivoImagens.getInstance();

        // Kernels usados.
        TransformacaoConvolucaoBasico kernel8Positivo = new TransformacaoConvolucaoBasico(TipoKernelLaplacianoEnum.OitoPontosPositivo);
        TransformacaoConvolucaoBasico kernel8Negativo = new TransformacaoConvolucaoBasico(TipoKernelLaplacianoEnum.OitoPontosNegativo);
        TransformacaoConvolucaoBasico kernel4Positivo = new TransformacaoConvolucaoBasico(TipoKernelLaplacianoEnum.QuatroPontosCruzPositivo);
        TransformacaoConvolucaoBasico kernel4Negativo = new TransformacaoConvolucaoBasico(TipoKernelLaplacianoEnum.QuatroPontosCruzNegativo);
        TransformacaoConvolucaoBasico kernelLinhasH = new TransformacaoConvolucaoBasico(TipoKernelLaplacianoEnum.DetectaLinhasHorizontais);
        TransformacaoConvolucaoBasico kernelLinhasV = new TransformacaoConvolucaoBasico(TipoKernelLaplacianoEnum.DetectaLinhasVerticiais);
        kernel4Positivo.setMultiplicador(2.0);
        kernel4Negativo.setMultiplicador(2.0);

        TransformacaoCinza tc = new TransformacaoCinza();

        // Carga das Imagens.
        TransformadorPixel imgOriginal = new TransformadorPixel(c.getDirBaseImagensEntrada("cafeteira_vermelha.png"));
        //imgOriginal.transforma(tc);
        TransformadorPixel img8p = new TransformadorConvolucaoSomaComOriginal(imgOriginal);
        TransformadorPixel img8n = new TransformadorConvolucaoSomaComOriginal(imgOriginal);
        TransformadorPixel img4p = new TransformadorConvolucaoSomaComOriginal(imgOriginal);
        TransformadorPixel img4n = new TransformadorConvolucaoSomaComOriginal(imgOriginal);
        TransformadorPixel imgLH = new TransformadorConvolucaoSomaComOriginal(imgOriginal);
        TransformadorPixel imgLV = new TransformadorConvolucaoSomaComOriginal(imgOriginal);

        // Definição e execução da Covolução
        img8p.transforma(kernel8Positivo);
        img8n.transforma(kernel8Negativo);
        img4p.transforma(kernel4Positivo);
        img4n.transforma(kernel4Negativo);
        imgLH.transforma(kernelLinhasH);
        imgLV.transforma(kernelLinhasV);

        //Mostra a Imagenm
        new ExibidorDeImagem("Café Original", imgOriginal, true);
        new ExibidorDeImagem("Laplaciano 4 +", img4p, true);
        new ExibidorDeImagem("Laplaciano 4 -", img4n, true);
        new ExibidorDeImagem("Laplaciano 8 +", img8p, true);
        new ExibidorDeImagem("Laplaciano 8 -", img8n, true);
        new ExibidorDeImagem("Laplaciano Linhas Horizontais", imgLH, true);
        new ExibidorDeImagem("Laplaciano Linhas Verticias", imgLV, true);
    }

}

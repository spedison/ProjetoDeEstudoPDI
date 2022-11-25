package br.com.spedison.main.BI_Transformacao_Convolucao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformadorConvolucao;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels.TipoKernelLaplacianoEnum;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoBasico;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;

public class Main_Aula10_Bordas {

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

        // Carga das Imagens.
        Imagem imgOriginal = new Imagem(c.getDirBaseImagensEntrada("cafeteira_vermelha.png"));
        TransformadorConvolucao img8p = new TransformadorConvolucao(imgOriginal);
        TransformadorConvolucao img8n = new TransformadorConvolucao(imgOriginal);
        TransformadorConvolucao img4p = new TransformadorConvolucao(imgOriginal);
        TransformadorConvolucao img4n = new TransformadorConvolucao(imgOriginal);
        TransformadorConvolucao imgLH = new TransformadorConvolucao(imgOriginal);
        TransformadorConvolucao imgLV = new TransformadorConvolucao(imgOriginal);

        // Definição e execução da Covolução
        img8p.transforma(kernel8Positivo);
        img8n.transforma(kernel8Negativo);
        img4p.transforma(kernel4Positivo);
        img4n.transforma(kernel4Negativo);
        imgLH.transforma(kernelLinhasH);
        imgLV.transforma(kernelLinhasV);

        //Mostra a Imagenm
        new ExibidorDeImagem("Café Original", new ImagemAjustadaTela(imgOriginal,0.6),true);
        new ExibidorDeImagem("Laplaciano 4 +", new ImagemAjustadaTela(img4p, 0.6),true);
        new ExibidorDeImagem("Laplaciano 4 -", new ImagemAjustadaTela(img4n,0.6),true);
        new ExibidorDeImagem("Laplaciano 8 +", new ImagemAjustadaTela(img8p,0.6),true);
        new ExibidorDeImagem("Laplaciano 8 -", new ImagemAjustadaTela(img8n,0.6),true);
        new ExibidorDeImagem("Laplaciano Linhas Horizontais", new ImagemAjustadaTela(imgLH,0.6),true);
        new ExibidorDeImagem("Laplaciano Linhas Verticias", new ImagemAjustadaTela(imgLV,0.6),true);
    }

}

package br.com.spedison.main.BH_TransformacaoEspacial;

import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.FlipRodaImagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.*;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.*;

public class Main_Aula_50_Projeto_Limpando_Documento {

    public static void main(String[] args) {

        // Inverte a imagem fazendo que a letra fique branca e o fundo escuro
        TransformacaoInverte tranformacaoInversao = new TransformacaoInverte();
        TransformacaoCinza transformacaoPB =  new TransformacaoCinza();
        TransformacaoIdentidade transformacaoIdentidade = new TransformacaoIdentidade();
        TransformacaoConvolucaoInterface kernelBorrar = new TransformacaoConvolucaoEstatistica(11, TipoOperacao.TipoOperacaoMedia);


        // Carrega a imagem Original e Roda ela 180G
        String docImagem = SistemaArquivoImagens.getInstance().getDirBaseImagensEntrada("doc", "0.jpg");
        FlipRodaImagem imagemOriginal = new FlipRodaImagem(docImagem);
        imagemOriginal.roda90gHorario().roda90gHorario(); // Roda a imagem 180 Graus.
        TransformadorPixel imagemOriginalPb = new TransformadorPixel(imagemOriginal);
        imagemOriginalPb.transforma(transformacaoPB);
        imagemOriginalPb.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("DocumentoPB.bmp"));

        TransformadorConvolucao fc = new TransformadorConvolucao();

        TransformadorPixel tranformadorImagem = new TransformadorPixel(imagemOriginal);
        tranformadorImagem.transforma(transformacaoPB);
        tranformadorImagem.transforma(tranformacaoInversao);
        fc.setImageBuffer(tranformadorImagem.getImageBuffer());
        fc.transforma(kernelBorrar);
        tranformadorImagem.setImageBuffer(fc.getImageBuffer());

        fc.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("DocumentoPBINVERTIDO.bmp"));

        TransformacaoLimiarizacao transformacaoLimiarizacao = new TransformacaoLimiarizacao(fc);
        transformacaoLimiarizacao.adicionaIntervalos(0,14,0);
        tranformadorImagem.transforma(transformacaoLimiarizacao);
        //tranformadorImagem.transforma(tranformacaoInversao);
        tranformadorImagem.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("DocumentoLimarizado.bmp"));

        TransformacaoAplicaMascara tam = new TransformacaoAplicaMascara(tranformadorImagem.clone(),new int[]{0xFF,0xFF,0xFF});
        tranformadorImagem.setImageBuffer(imagemOriginal.getImageBuffer());
        tranformadorImagem.transforma(tam);

        tranformadorImagem.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("DocumentoLimpo.bmp"));

//        OperacoesEntreImagens operacoesEntreImagens = new OperacoesEntreImagens(imagemOriginal);
//        operacoesEntreImagens.xorImagem(tranformadorImagem);
//        operacoesEntreImagens.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("DocumentoLimpo.bmp"));


        //TODO: Depois da máscara, Aplicar um AND na imagem inicial.
        //TODO: Mostrar a imagem original Antes e Depois do Processamento.







        //fc.transforma();

        //new ExibidorDeImagem("Rodado e Invertido", new ImagemAjustadaTela(fc, 0.8), true);


    }

}

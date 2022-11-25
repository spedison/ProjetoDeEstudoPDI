package br.com.spedison.main.imagem_padrao;

import br.com.spedison.biblioteca_pdi.base.ImagemGeradorPadrao;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoBasico;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoInterface;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformadorConvolucao;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels.TipoKernelGaussiano;

public class MainCirculosVariacaoRaio {

    public static void main(String[] args) {

        int yLenImagem = 651;
        int xLenImagem = 1501;

        ImagemGeradorPadrao imagemPadraoOriginal = new ImagemGeradorPadrao(xLenImagem, yLenImagem);
        imagemPadraoOriginal.setOrigemX(-imagemPadraoOriginal.getLimiteX() / 2);
        imagemPadraoOriginal.setOrigemY(-imagemPadraoOriginal.getLimiteY() / 2);

        final int[] pontoBranco = new int[]{0xFF, 0xFF, 0xFF};
        final int[] pontoVermelho = new int[]{0xFF, 0x00, 0x00};
        final int[] pontoVerde = new int[]{0x00, 0xFF, 0x00};
        final Circulo circulo = new Circulo(yLenImagem * .4);

        imagemPadraoOriginal.processa(
                (p, rgb) -> {
                    if (circulo.estaDentro(p.x, p.y))
                        return pontoVermelho;
                    return pontoBranco;
                });

        imagemPadraoOriginal.processa(
                (p, rgb) -> {
                    if (circulo.estaDentro(p.x, p.y)) {

                        var r = circulo.calculaRaio(p.x, p.y);
                        var a = Math.abs(circulo.calculaAngulo(p.x, p.y));
                        r %= 20.0;
                        a %= 10.0;
                        if (r < 10.) {
                            return pontoBranco;
                        } else {
                            return pontoVermelho;
                            /*if (a < 5.)
                                return pontoVermelho;
                            else
                                return pontoVerde;*/
                        }
                    }
                    return pontoBranco;
                });

        var kernelGaussiano = new TipoKernelGaussiano(0, 0, 2, 7);
        kernelGaussiano.criaDadosKernel(1.2, 0.);
        TransformacaoConvolucaoInterface transformadorConvolucaoGaussiano = new TransformacaoConvolucaoBasico(kernelGaussiano);
        TransformadorConvolucao imagemConvolucionada = new TransformadorConvolucao(imagemPadraoOriginal);
        imagemConvolucionada.transforma(transformadorConvolucaoGaussiano);

        imagemConvolucionada.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_conv.bmp"));
        imagemPadraoOriginal.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_orig.bmp"));

        //new ExibidorDeImagem("Mostrando Padrao Convolucionado", imagemConvolucionada, true);

        //new ExibidorDeImagem("Mostrando Padrao Original", imagemPadraoOriginal, true);

    }
}

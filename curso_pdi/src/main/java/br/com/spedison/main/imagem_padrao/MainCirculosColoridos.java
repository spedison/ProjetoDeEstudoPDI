package br.com.spedison.main.imagem_padrao;

import br.com.spedison.biblioteca_pdi.base.ImagemGeradorPadrao;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;

public class MainCirculosColoridos {

    public static void main(String[] args) {

        int yLenImagem = 651;
        int xLenImagem = 1501;

        ImagemGeradorPadrao imagemPadraoOriginal = new ImagemGeradorPadrao(xLenImagem, yLenImagem);
        imagemPadraoOriginal.setOrigemX(-imagemPadraoOriginal.getLimiteX() / 2);
        imagemPadraoOriginal.setOrigemY(-imagemPadraoOriginal.getLimiteY() / 2);

        final int[] pontoBranco = new int[]{0xFF, 0xFF, 0xFF};
        final int[] pontoVermelho = new int[]{0xFF, 0x00, 0x00};
        final int[] pontoVerde = new int[]{0x00, 0xFF, 0x00};
        final int[] pontoAmarelo = new int[]{0xFF, 0xFF, 0x00};
        final int[] pontoRosa = new int[]{0xFF, 0x00, 0xFF};
        final int[] pontoRoxo = new int[]{0x8A, 0x2B, 0xE2};
        final int[] pontoAzulCyam = new int[]{0x00, 0xFF, 0xFF};

        final Circulo circuloDireita = new Circulo(yLenImagem * .4, xLenImagem / 5.5, 0);
        final Circulo circuloCentro = new Circulo(yLenImagem * .4);
        final Circulo circuloEsquerda = new Circulo(yLenImagem * .4, -xLenImagem / 5.5, 0);
        final Circulo pequenoCirculoAcima = new Circulo(yLenImagem * .05, 0., imagemPadraoOriginal.getLimiteY() / 3.);
        final Circulo pequenoCirculoAbaixo = new Circulo(yLenImagem * .05, 0., -imagemPadraoOriginal.getLimiteY() / 3.);

        imagemPadraoOriginal.processa(
                (p, rgb) -> {
                    if (circuloCentro.estaDentro(p.x, p.y)) {
                        // Se está no circulo central e no da Direita retorna Roxo.
                        if (circuloDireita.estaDentro(p.x, p.y)) {
                            return pontoRoxo;
                            // Se está na central na da esquerda pinta de Rosa.
                        } else if (circuloEsquerda.estaDentro(p.x, p.y)) {
                            return pontoRosa;
                            // Caso esteja na central e está dentro de algum dos circulos menores, pinta de Azul Cyam.
                        } else if (pequenoCirculoAbaixo.estaDentro(p.x, p.y) ||
                                pequenoCirculoAcima.estaDentro(p.x, p.y)) {
                            return pontoAzulCyam;
                            // Caso contrário é vermelho ferrari
                        } else {
                            return pontoVermelho;
                        }
                        // Se estiver somente no círculo da Direita, Amarelo
                    } else if (circuloDireita.estaDentro(p.x, p.y)) {
                        return pontoAmarelo;
                        // Se tiver do lado esquerno da central Verde.
                    } else if (circuloEsquerda.estaDentro(p.x, p.y)) {
                        return pontoVerde;
                    } else {
                        // Fora dos 3 círculos, é branco.
                        return pontoBranco;
                    }
                });
/*
        var kernelGaussiano = new TipoKernelGaussiano(0, 0, 2, 7);
        kernelGaussiano.criaDadosKernel(1.2, 0.);
        TransformacaoConvolucaoInterface transformadorConvolucaoGaussiano = new TransformacaoConvolucaoBasico(kernelGaussiano);
        TransformadorConvolucao imagemConvolucionada = new TransformadorConvolucao(imagemPadraoOriginal);
        imagemConvolucionada.transforma(transformadorConvolucaoGaussiano);
        imagemConvolucionada.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_conv.bmp"));
        imagemPadraoOriginal.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("padrao_orig.bmp"));
        //new ExibidorDeImagem("Mostrando Padrao Convolucionado", imagemConvolucionada, true);*/

        new ExibidorDeImagem("Mostrando Padrao Original", imagemPadraoOriginal, true);

    }
}

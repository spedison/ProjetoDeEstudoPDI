package br.com.spedison.main.imagem_padrao;

import br.com.spedison.biblioteca_pdi.base.ImagemGeradorPadrao;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoBasico;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoInterface;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformadorConvolucao;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels.TipoKernelGaussiano;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;

public class MainBandeiraJapao {

    public static void main(String[] args) {

        int yLenImagem = 651;
        int xLenImagem = 1501;

        ImagemGeradorPadrao imagemPadraoOriginal = new ImagemGeradorPadrao(xLenImagem, yLenImagem);
        imagemPadraoOriginal.setOrigemX(-imagemPadraoOriginal.getLimiteX() / 2);
        imagemPadraoOriginal.setOrigemY(-imagemPadraoOriginal.getLimiteY() / 2);

        final int[] pontoBranco = new int[]{0xFF, 0xFF, 0xFF};
        final int[] pontoVermelho = new int[]{0xFF, 0x00, 0x00};
        final Circulo circulo = new Circulo(yLenImagem * .4);

        imagemPadraoOriginal.processa(
                (p, rgb) -> {
                    if (circulo.estaDentro(p.x, p.y))
                        return pontoVermelho;
                    return pontoBranco;
                });

        new ExibidorDeImagem("Mostrando Padrao Original", imagemPadraoOriginal, true);
    }
}

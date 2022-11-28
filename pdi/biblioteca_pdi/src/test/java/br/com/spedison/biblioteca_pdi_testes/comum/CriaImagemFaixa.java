package br.com.spedison.biblioteca_pdi_testes.comum;

import br.com.spedison.biblioteca_pdi.base.Imagem;

import java.awt.*;

public class CriaImagemFaixa {

    public static Imagem criaFaixaVertical(int tamanho, int largura) {//, int largura, int [] corSim, int [] corNao){
        return criaFaixaVertical(tamanho, largura, largura, new int[]{255, 0, 0}, new int[]{255, 255, 255});
    }

    public static Imagem criaFaixaVertical(int tamanho, int larguraSim, int larguraNao) {//, int largura, int [] corSim, int [] corNao){
        return criaFaixaVertical(tamanho, larguraSim, larguraNao, new int[]{255, 0, 0}, new int[]{255, 255, 255});
    }

    public static Imagem criaFaixaVertical(int tamanho, int larguraSim, int larguraNao, int[] corSim, int[] corNao) {

        Imagem ret = new Imagem(tamanho, tamanho);
        ret.getStreamLargura().
                parallel().forEach(
                        xPos -> {
                            ret.getStreamAltura().parallel().forEach(
                                    yPos -> {
                                        int cor = xPos % (larguraSim + larguraNao);
                                        if (cor < larguraSim)
                                            ret.setPixel(xPos, yPos, corSim);
                                        else
                                            ret.setPixel(xPos, yPos, corNao);
                                    });
                        });

        return ret;
    }
}

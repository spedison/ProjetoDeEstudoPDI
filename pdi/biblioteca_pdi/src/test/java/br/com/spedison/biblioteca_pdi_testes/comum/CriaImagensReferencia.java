package br.com.spedison.biblioteca_pdi_testes.comum;

import br.com.spedison.biblioteca_pdi.base.Imagem;

public class CriaImagensReferencia {

    public static Imagem cria10por10LinhasPB() {

        Imagem ret = new Imagem(10, 10);
        for (int linha = 1; linha < 10; linha += 2) {
            for (int coluna = 0; coluna < 10; coluna++) {
                ret.setPixel(coluna, linha, new int[]{255, 255, 255});
            }
        }
        return ret;
    }

    public static Imagem cria10por10DeslocadoVertical5ColunasPB() {

        Imagem ret = new Imagem(10, 10);
        for (int linha = 1; linha < 10; linha += 2) {
            for (int coluna = 5; coluna < 10; coluna++) {
                ret.setPixel(coluna, linha, new int[]{255, 255, 255});
            }
        }
        return ret;
    }


    public static Imagem criaImagemLinear(int tamanhoX, int tamanhoY) {
        var ret = new Imagem(tamanhoX, tamanhoY);
        ret.getStreamLargura().forEach(x -> {
            ret.getStreamAltura().forEach(y -> {
                int val = x * 10 + y * 10 + 10;
                ret.setPixel(x, y, new int[]{val, val, val});
            });
        });
        return ret;
    }
}

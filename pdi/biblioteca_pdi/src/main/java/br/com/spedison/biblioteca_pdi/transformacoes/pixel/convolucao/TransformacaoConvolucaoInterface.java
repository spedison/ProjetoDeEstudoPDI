package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.projeto.ValidacaoArraysException;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;

public interface TransformacaoConvolucaoInterface<R> extends TransformacaoPixelRGB<R> {
    double[][][] getData1();

    R setData1(double[][][] data);

    default double[][][] getData2() {
        return getData1();
    }

    default R setData2(double[][][] data) {
        return setData1(data);
    }

    default void validaTamanho2Kernels() {
        ValidacaoArraysException.validar(getData1() == null, "Dados1 não devem ser null");
        ValidacaoArraysException.validar(getData1().length != 3, "kernel 1: Deve ter 3 kernels. 1 para cada canal");
        ValidacaoArraysException.validar(getData2().length != 3, "kernel 2: Deve ter 3 kernels. 1 para cada canal");

        ValidacaoArraysException.validar(getData1()[0].length % 2 == 0, "O Kernel 1 deve ter dimensão X impar");
        ValidacaoArraysException.validar(getData2()[0].length % 2 == 0, "O Kernel 2 deve ter dimensão X impar");
        ValidacaoArraysException.validar(getData1()[0].length != getData2()[0].length, "O Kernel 2 deve ter dimensão mesma dimensão Y do Kernel 2");

        ValidacaoArraysException.validar(getData1()[0][0].length != getData1()[0].length, "O Kernel 1: Dimensão X deve ter dimensão Y impar (Quadrado)");
        ValidacaoArraysException.validar(getData2()[0][0].length != getData2()[0].length, "O Kernel 2: Dimensão X deve ter dimensão Y impar (Quadrado)");

        ValidacaoArraysException.validar(getData1()[0].length != getData1()[1].length || getData1()[0].length != getData1()[2].length, "O Kernel 1 deve ter dimensão X igual em RGB");
        ValidacaoArraysException.validar(getData2()[0].length != getData2()[1].length || getData2()[0].length != getData2()[2].length, "O Kernel 2 deve ter dimensão X igual em RGB");

        ValidacaoArraysException.validar(getData1()[0][0].length != getData1()[1][0].length || getData1()[0][0].length != getData1()[2][0].length, "O Kernel 1 deve ter dimensão Y igual em RGB");
        ValidacaoArraysException.validar(getData2()[0][0].length != getData2()[1][0].length || getData2()[0][0].length != getData2()[2][0].length, "O Kernel 2 deve ter dimensão Y igual em RGB");
    }

    @Override
    default R inicializa(Imagem imagem) {
        validaTamanho2Kernels();
        return TransformacaoPixelRGB.super.inicializa(imagem);
    }
}
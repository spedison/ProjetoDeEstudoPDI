package br.com.spedison.biblioteca_pdi.auxiliar;

import java.util.stream.IntStream;

public class ImprimeArray {

    static public void imprimeInt2D(int[][] dado) {
        IntStream.range(0, dado.length).forEach(
                posX -> {
                    IntStream.range(0, dado[posX].length).forEach(posY -> {
                        System.out.printf("%d\t", dado[posX][posY]);
                    });
                    System.out.println("");
                }
        );
    }

    static public void imprimeDouble2D(double[][] dado) {
        imprimeDouble2D(dado, 10, 3);
    }

    static public void imprimeDouble2D(double[][] dado, int tamanho, int precisao) {
        String strFormat = String.format("%%%d.%df\t", tamanho, precisao);
        IntStream.range(0, dado.length).forEach(
                posX -> {
                    IntStream.range(0, dado[posX].length).forEach(posY -> {
                        System.out.printf(strFormat, dado[posX][posY]);
                    });
                    System.out.println("");
                }
        );
    }

}

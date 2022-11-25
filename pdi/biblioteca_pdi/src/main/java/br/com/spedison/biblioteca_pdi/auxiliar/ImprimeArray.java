package br.com.spedison.biblioteca_pdi.auxiliar;

import java.util.stream.IntStream;

public class ImprimeArray {

    static public void imprimeInt2D(int[][] dado) {
        System.out.print(strInt2D(dado));
    }

    static public void imprimeDouble2D(double[][] dado, int tamanho, int precisao) {
        System.out.print(strDouble2D(dado, tamanho, precisao));
    }

    static public void imprimeDouble2D(double[][] dado) {
        System.out.print(strDouble2D(dado));
    }

    static public String strInt2D(int[][] dado) {
        StringBuffer ret = new StringBuffer();
        IntStream.range(0, dado.length).forEach(
                posX -> {
                    IntStream.range(0, dado[posX].length).forEach(posY -> {
                        ret.append(String.format("%d\t", dado[posX][posY]));
                    });
                    ret.append("\n");
                }
        );
        return ret.toString();
    }

    static public String strDouble2D(double[][] dado) {
        return strDouble2D(dado, 10, 3);
    }

    static public String strDouble2D(double[][] dado, int tamanho, int precisao) {
        String strFormat = String.format("%%%d.%df\t", tamanho, precisao);
        final StringBuffer ret = new StringBuffer();
        IntStream.range(0, dado.length).forEach(
                posX -> {
                    IntStream.range(0, dado[posX].length).forEach(posY -> {
                        ret.append(String.format(strFormat, dado[posX][posY]));
                    });
                    ret.append("\n");
                }
        );
        return ret.toString();
    }
}

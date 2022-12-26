package br.com.spedison.usogeral.auxiliar;

public class MatrizAuxiliar {

    public static double[][] transpor(double[][] varOriginal) {
        double[][] retorno = new double[varOriginal[0].length][varOriginal.length];
        for (int l = 0; l < retorno.length; l++) {
            for (int c = 0; c < retorno[l].length; c++) {
                retorno[l][c] = varOriginal[c][l];
            }
        }
        return retorno;
    }

    public static float[][] transpor(float[][] varOriginal) {
        float[][] retorno = new float[varOriginal[0].length][varOriginal.length];
        for (int l = 0; l < retorno.length; l++) {
            for (int c = 0; c < retorno[l].length; c++) {
                retorno[l][c] = varOriginal[c][l];
            }
        }
        return retorno;
    }

    public static int[][] transpor(int[][] varOriginal) {
        int[][] retorno = new int[varOriginal[0].length][varOriginal.length];
        for (int l = 0; l < retorno.length; l++) {
            for (int c = 0; c < retorno[l].length; c++) {
                retorno[l][c] = varOriginal[c][l];
            }
        }
        return retorno;
    }

    public static long[][] transpor(long[][] varOriginal) {
        long[][] retorno = new long[varOriginal[0].length][varOriginal.length];
        for (int l = 0; l < retorno.length; l++) {
            for (int c = 0; c < retorno[l].length; c++) {
                retorno[l][c] = varOriginal[c][l];
            }
        }
        return retorno;
    }

    public static double[][] transporParaDouble(float[][] varOriginal) {
        double[][] retorno = new double[varOriginal[0].length][varOriginal.length];
        for (int l = 0; l < retorno.length; l++) {
            for (int c = 0; c < retorno[l].length; c++) {
                retorno[l][c] = varOriginal[c][l];
            }
        }
        return retorno;
    }

}
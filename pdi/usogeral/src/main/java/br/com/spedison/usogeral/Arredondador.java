package br.com.spedison.usogeral;

public class Arredondador {
    /***
     * Arredonda um número levando em considera
     * @param valor
     * @return
     */
    public static int arredonda(double valor) {
        if (Math.signum(valor) >= 0) {
            return (int) (valor + .5);
        } else {
            return (int) (valor - .5);
        }
    }

    public static int proximoMaiorNumeroEmModulo(double valor) {
        if (Math.signum(valor) >= 0) {
            return (int) (valor - parteFracionaria(valor)) + 1;
        } else {
            return (int) (valor + parteFracionaria(valor)) - 1;
        }
    }

    public static int proximoMenorNumeroEmModulo(double valor) {
        if (valor >= 0.0) {
            return ((int) (valor - parteFracionaria(valor)) - 1);
        } else {
            return ((int) (valor + parteFracionaria(valor)) + 1);
        }
    }

    public static int menorNumeroEmModulo(double valor) {
        if (Math.signum(valor) >= 0) {
            return (int) Math.floor(valor);
        } else {
            return (int) Math.ceil(valor);
        }
    }

    public static int proximoNumeroInteiroEmModulo(double valor) {
        if (Math.signum(valor) < 0) {
            return (int) Math.floor(valor);
        } else {
            return (int) Math.ceil(valor);
        }
    }

    public static double parteFracionaria(double valor) {
        return valor - ((int) valor);
    }

    public static double complemento(double valor) {
        return complemento(valor, 1.0);
    }

    public static double complemento(double valor, double valorComplemento) {
        if (Math.signum(valor) > 0) {
            return Math.abs(valorComplemento) - valor;
        } else {
            return -Math.abs(valorComplemento) - valor;
        }
    }

    public static int menorNumeroProximoInt(double num) {
        if (Math.signum(num) > 0) {
            return (int) num;
        } else {
            return ((int) num) - 1;
        }
    }

    public static int maiorNumeroProximoInt(double num) {
        if (Math.signum(num) > 0) {
            return ((int) num) + 1;
        } else {
            return ((int) num);
        }
    }
}

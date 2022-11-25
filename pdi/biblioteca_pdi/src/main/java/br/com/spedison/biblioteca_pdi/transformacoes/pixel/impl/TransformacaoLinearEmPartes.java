package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.Funcao;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes.FuncaoLinear;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;

import java.util.ArrayList;

/**
 * Aplicação de transformação de função linear definida em partes.
 * Gonzalez - Figura 3.10 (a)
 * Podemos extender esse modelo para atender a figura
 * Gonzalez - Figura 3.11 (a e b)
 */
public class TransformacaoLinearEmPartes implements TransformacaoPixelRGB<TransformacaoLinearEmPartes> {

    /*** Conjunto de 3 funções definidas no intervalo entre A e B para R,G,B.
     * 1o Nível (Qual é o Canal RGB ou seja 0, 1 ou 2.
     * 2o Nível - intervalo A, B ou C ... ou ainda 0, 1 ou 2.
     */
    ArrayList<ArrayList<Funcao>> funcoes;

    // Intervalor definido para os 3 canais.
    int[] intevaloA;
    int[] intevaloB;

    public TransformacaoLinearEmPartes(ArrayList<ArrayList<Funcao>> funcoes, int[] intevaloA, int[] intevaloB) {
        assert intevaloA.length == 3 && intevaloB.length == 3 : "Arrays devem ter tamanho 3.";
        this.funcoes = funcoes;
        this.intevaloA = intevaloA;
        this.intevaloB = intevaloB;
        ajustaIntervalos();
    }

    /***
     * Cria as constantes e multiplicadores aplicados no intervalor A,B,C. Em espaços R,G,B.
     * @param constante - Constante aplicada no Modelo linear o 1o ínidice é o Canal e Segundo índice é o intervalo
     * @param multiplicador - Multiplicador do Modelo linear o 1o ínidice é o Canal e Segundo índice é o intervalo
     * @param intevaloA - Qual é o intervalo aplicado para os canais que inciam em Zero e vão até A. (diferente para os 3 canais)
     * @param intevaloB - Qual o valor do intervalo B. O segundo intervalo fica entre o A e B. Do B até 255 é o terceiro intervalo. (diferente para os 3 canais)
     */
    public TransformacaoLinearEmPartes(double[][] multiplicador, double[][] constante, int[] intevaloA, int[] intevaloB) {
        assert intevaloA.length == 3 && intevaloB.length == 3 : "Arrays devem ter tamanho 3.";
        this.intevaloA = intevaloA;
        this.intevaloB = intevaloB;
        ajustaIntervalos();

        funcoes = new ArrayList<ArrayList<Funcao>>(3);
        ArrayList<Funcao> fr = new ArrayList<>(3);
        fr.add(new FuncaoLinear(multiplicador[0][0], constante[0][0]));
        fr.add(new FuncaoLinear(multiplicador[0][1], constante[0][1]));
        fr.add(new FuncaoLinear(multiplicador[0][2], constante[0][2]));
        funcoes.add(fr);

        ArrayList<Funcao> fg = new ArrayList<>(3);
        fg.add(new FuncaoLinear(multiplicador[1][0], constante[1][0]));
        fg.add(new FuncaoLinear(multiplicador[1][1], constante[1][1]));
        fg.add(new FuncaoLinear(multiplicador[1][2], constante[1][2]));
        funcoes.add(fg);

        ArrayList<Funcao> fb = new ArrayList<>(3);
        fb.add(new FuncaoLinear(multiplicador[2][0], constante[2][0]));
        fb.add(new FuncaoLinear(multiplicador[2][1], constante[2][1]));
        fb.add(new FuncaoLinear(multiplicador[2][2], constante[2][2]));
        funcoes.add(fb);

    }

    /***
     * Cria as constantes e multiplicadores aplicados no intervalor A,B,C. Em espaços R,G,B.
     * @param constante - Constante aplicada no Modelo linear o índice é o intervalo
     * @param multiplicador - Multiplicador do Modelo linear o índice é o intervalo
     * @param intevaloA - Qual é o intervalo aplicado para os canais que inciam em Zero e vão até A (igual para os 3 canais).
     * @param intevaloB - Qual o valor do intervalo B. O segundo intervalo fica entre o A e B. Do B até 255 é o terceiro intervalo (igual para os 3 canais).
     */
    public TransformacaoLinearEmPartes(double[] multiplicador, double[] constante, int intevaloA, int intevaloB) {

        this.intevaloA = new int[]{intevaloA, intevaloA, intevaloA};
        this.intevaloB = new int[]{intevaloB, intevaloB, intevaloB};
        ajustaIntervalos();

        funcoes = new ArrayList<ArrayList<Funcao>>(3); // Funçoes para 3 canais.

        ArrayList<Funcao> fr = new ArrayList<>(3); // Funçoes usadas para 3 intervalos (0 até A, A até B, B até 255)
        fr.add(new FuncaoLinear(multiplicador[0], constante[0]));
        fr.add(new FuncaoLinear(multiplicador[1], constante[1]));
        fr.add(new FuncaoLinear(multiplicador[2], constante[2]));
        funcoes.add(fr);

        ArrayList<Funcao> fg = new ArrayList<>(3);
        fg.add(new FuncaoLinear(multiplicador[0], constante[0]));
        fg.add(new FuncaoLinear(multiplicador[1], constante[1]));
        fg.add(new FuncaoLinear(multiplicador[2], constante[2]));
        funcoes.add(fg);

        ArrayList<Funcao> fb = new ArrayList<>(3);
        fb.add(new FuncaoLinear(multiplicador[0], constante[0]));
        fb.add(new FuncaoLinear(multiplicador[1], constante[1]));
        fb.add(new FuncaoLinear(multiplicador[2], constante[2]));
        funcoes.add(fb);
    }


    private void swap(int[] arra, int[] arrb, int pos) {
        int tmp = arra[pos];
        arrb[pos] = arra[pos];
        arra[pos] = tmp;
    }

    private void ajustaIntervalos() {
        for (int i = 0; i < Math.min(intevaloA.length, intevaloB.length); i++) {
            if (intevaloA[i] > intevaloB[i])
                swap(intevaloA, intevaloB, i);
        }
    }

    private int transforma(int x, int y, int valor, int rgb) {
        // Se está dentro do Intervalo pela a segunda função
        if (valor >= intevaloA[rgb] && valor <= intevaloB[rgb]) {
            return funcoes.get(rgb).get(1).transforma(x, y, valor);
            // Se for maior que o intervalo B, aplica a 3 função
        } else if (valor > intevaloB[rgb] && valor > intevaloA[rgb]) {
            return funcoes.get(rgb).get(2).transforma(x, y, valor);
            // Caso contrário só pode aplicar a 1o função.
        } else
            return funcoes.get(rgb).get(0).transforma(x, y, valor);
    }


    @Override
    public int[] transformaRGB(int[] valorPixel, int x, int y, Imagem imagem) {
        return new int[]{
                transforma(x, y, valorPixel[Imagem.Vermelho], Imagem.Vermelho),
                transforma(x, y, valorPixel[Imagem.Verde], Imagem.Verde),
                transforma(x, y, valorPixel[Imagem.Azul], Imagem.Azul)
        };
    }

    private String formataModelo(int intervaloInicio, int intervaloFim, String modelo) {
        return "Entre o intervalo [%d e %d] aplicamos o modelo : %s".
                formatted(
                        intervaloInicio,
                        intervaloFim,
                        modelo
                );
    }

    @Override
    public String toString() {
        return "Classe : %s \n--- Para o Canal R --- :: \n-  %s\n-  %s\n-  %s\n--- Para o Canal G --- :: \n-  %s\n-  %s\n-  %s\n--- Para o Canal B --- :: \n-  %s\n-  %s\n-  %s\n".
                formatted(
                        this.getClass().getSimpleName(),

                        formataModelo(0, intevaloA[0], funcoes.get(0).get(0).toString()),
                        formataModelo(intevaloA[0], intevaloB[0], funcoes.get(0).get(1).toString()),
                        formataModelo(intevaloB[0], 255, funcoes.get(0).get(2).toString()),

                        formataModelo(0, intevaloA[1], funcoes.get(1).get(0).toString()),
                        formataModelo(intevaloA[1], intevaloB[1], funcoes.get(1).get(1).toString()),
                        formataModelo(intevaloB[1], 255, funcoes.get(1).get(2).toString()),

                        formataModelo(0, intevaloA[2], funcoes.get(2).get(0).toString()),
                        formataModelo(intevaloA[2], intevaloB[2], funcoes.get(2).get(1).toString()),
                        formataModelo(intevaloB[2], 255, funcoes.get(2).get(2).toString())
                );
    }

}

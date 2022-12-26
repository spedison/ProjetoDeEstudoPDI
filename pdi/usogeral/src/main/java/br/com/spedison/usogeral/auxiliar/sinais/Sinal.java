package br.com.spedison.usogeral.auxiliar.sinais;

import java.util.stream.IntStream;

/***
 * Essa interface tem o objetivo de representar os sinais gerados para processamentos de convoluções e filtros de fft.
 */
public interface Sinal {

    /***
     * Esse método calcula a valor do sinal no tempo(t), aqui não é uma simles f(x), pois, como é um sinal, normalmente cíclico,
     * tem um cálculo de módulo e uma forma sutilmente difernete para valores t < 0.
     * @param t
     * @return
     */
    double calculaAmplitude(double t);

    /***
     * O Sinal é basicamente uma função no espaço do Real 1 -> Real 1.
     * Esse método chama, de forma geral, o método implmentado de calculaAmplitude gerando um array bi-dimensional
     * Com dados em 0 - da variável independente X e 1 do array temos a amplitude do sinal.
     * @param inicio - Valor inicial da variável independente.
     * @param fim - Valor final da variável independente.
     * @param passo - Qual o passo intervalo utiliza entre os itens da matriz de saida na forma : f(x) e f(x+delta)
     * @return - array bi-dimensional real, onde o 1o índice sáo x e y. X na posição 0 e y na posição 1.
     * O segundo índice (i) tem o resultado desse processamento em X e Y na forma x[i] e f(x[i]).
     */
    default double[][] geraArrayDadosParaProcessamento(double inicio, double fim, double passo) {
        double[][] dadosSaida = new double[2][(int) ((fim - inicio) / passo) + 1];
        IntStream
                .range(0, dadosSaida[0].length)
                .parallel()
                .forEach(pos -> {
                            double posX = (pos * passo) + inicio;
                            dadosSaida[0][pos] = posX;
                            dadosSaida[1][pos] = calculaAmplitude(posX);
                        }
                );
        return dadosSaida;
    }
}
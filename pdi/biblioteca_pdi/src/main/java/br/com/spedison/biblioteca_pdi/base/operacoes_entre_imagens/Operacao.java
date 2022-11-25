package br.com.spedison.biblioteca_pdi.base.operacoes_entre_imagens;

@FunctionalInterface
public interface Operacao {
    int[] operaItemMatrix(int[] p1, int[] p2, int[] resultado);
}

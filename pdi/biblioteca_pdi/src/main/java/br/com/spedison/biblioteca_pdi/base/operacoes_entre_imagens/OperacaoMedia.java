package br.com.spedison.biblioteca_pdi.base.operacoes_entre_imagens;

public class OperacaoMedia implements Operacao {
    @Override
    public int[] operaItemMatrix(int[] p1, int[] p2, int[] resultado) {
        assert p1.length == p2.length && p1.length == resultado.length : "Tamanho do arrays devem ser iguais.";
        for (int i = 0; i < p1.length; i++) {
            resultado[i] = (p1[i] + p2[i]) / 2;
        }
        return resultado;
    }
}

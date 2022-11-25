package br.com.spedison.biblioteca_pdi.base.operacoes_entre_imagens;

public class OperacaoMinimo implements Operacao {

    @Override
    public int[] operaItemMatrix(int[] p1, int[] p2, int[] resultado) {
        assert p1.length == p2.length && p1.length == resultado.length : "Tamanho do arrays devem ser iguais.";
        for (int i = 0; i < p1.length; i++) {
            resultado[i] = Math.min(p1[i], p2[i]);
        }
        return resultado;
    }
}

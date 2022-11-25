package br.com.spedison.biblioteca_pdi.base.operacoes_entre_imagens;

public enum OperacaoEntreImagens {
    Soma(new OperacaoSoma()),
    Subtracao(new OperacaoSubtracao()),
    ModuloSubtracao(new OperacaoModuloSubtracao()),
    Xor(new OperacaoXorBit()),
    And(new OperacaoAndBit()),
    Or(new OperacaoOrBit()),
    Minimo(new OperacaoMinimo()),
    Maximo(new OperacaoMaximo()),
    Multiplica(new OperacaoMultiplica()),
    Divide(new OperacaoDivide()),
    Media(new OperacaoMedia());

    OperacaoEntreImagens(Operacao op) {
        this.op = op;
    }

    private Operacao op;

    public Operacao getOperacao() {
        return op;
    }
}

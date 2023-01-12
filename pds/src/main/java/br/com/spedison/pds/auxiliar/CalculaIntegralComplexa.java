package br.com.spedison.pds.auxiliar;

import java.util.stream.IntStream;

public class CalculaIntegralComplexa {

    double passo;

    Funcao1Complexa funcaoComplexa;

    public void setPasso(double passo) {
        this.passo = passo;
    }

    public void setFuncaoComplexa(Funcao1Complexa funcaoComplexa) {
        this.funcaoComplexa = funcaoComplexa;
    }

    @FunctionalInterface
    public interface Funcao1Complexa {
        Complexo fx(double real);
    }

    public Complexo getIntegral(double inicio, double fim) {
        final int passos = (int) Math.ceil((fim - inicio) / passo) ;
        return
                IntStream
                        .range(0, passos)
                        .parallel()
                        .mapToDouble(i -> (i * passo) + inicio)
                        .mapToObj(x -> new TrapezioRetangulo(passo, funcaoComplexa.fx(x), funcaoComplexa.fx(x + passo)))
                        .map(TrapezioRetangulo::getArea)
                        .reduce(Complexo.getZero(), Complexo::soma);
    }
}

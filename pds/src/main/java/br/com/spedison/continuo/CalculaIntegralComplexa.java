package br.com.spedison.continuo;

import br.com.spedison.fourier.Complexo;

import java.util.stream.IntStream;

public class CalculaIntegralComplexa {

    @FunctionalInterface
    public static interface Funcao1Complexa {
        Complexo fx(double imaginario);
    }


    public Complexo getIntegral(double inicio, double fim, double passo, Funcao1Complexa f) {
        final int passos = (int) Math.ceil((fim - inicio) / passo) + 1;
        Complexo ret =
                IntStream
                        .rangeClosed(0, passos)
                        // .parallel()
                        .mapToDouble(i -> (i * passo) + inicio)
                        .map(i -> i <= fim ? i : fim)
                        .mapToObj(x -> new TrapezioRetangulo(passo, f.fx(x), f.fx(x + passo)))
                        //.map(TrapezioRetangulo::toString)
                        //.forEach(System.out::println);
                        //return 0.;
                        .map(TrapezioRetangulo::getArea)
                        .reduce(Complexo.getIdentidade(), Complexo::soma);
        return ret;
    }
}

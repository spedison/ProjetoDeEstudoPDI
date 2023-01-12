package br.com.spedison.usogeral.sinais;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SinalSomaDeSinais implements Sinal {

    List<Sinal> listaDeSinais = new LinkedList<>();
    public SinalSomaDeSinais() {
    }

    public SinalSomaDeSinais(List<Sinal> sinais) {
        listaDeSinais.addAll(sinais);
    }

    public SinalSomaDeSinais(Sinal...sinais) {
        Arrays.stream(sinais).forEach(listaDeSinais::add);
    }

    @Override
    public double calculaAmplitude(double t) {
        double soma = .0D;
        for (Sinal a : listaDeSinais) {
            soma += a.calculaAmplitude(t);
        }
        return soma;
    }

    @Override
    public String getNome() {
        return "Soma de sinais";
    }
}
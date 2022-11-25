package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.Funcao;

import java.util.Collections;
import java.util.LinkedList;

public class FuncaoLimiarizar implements Funcao<FuncaoLimiarizar> {

    private class Intervalo implements Comparable<Intervalo> {
        int intervaloInicial;
        int intervaloFinal;
        int valor;

        public Intervalo(int intervaloInicial, int intervaloFinal, int valor) {
            this.intervaloInicial = intervaloInicial;
            this.intervaloFinal = intervaloFinal;
            ajustaIntervalos();
            this.valor = valor;
        }

        @Override
        public int compareTo(Intervalo o) {
            return this.intervaloInicial - o.intervaloInicial;
        }

        @Override
        public String toString() {
            return "IntervaloIncluso{ De % 6d a % 6d retorna % 6d }".
                    formatted(intervaloInicial, intervaloFinal, valor);
        }

        private void ajustaIntervalos() {
            if (intervaloFinal < intervaloInicial) {
                int tmp = intervaloInicial;
                intervaloInicial = intervaloFinal;
                intervaloFinal = tmp;
            }
        }
    }

    LinkedList<Intervalo> intervalos = new LinkedList<Intervalo>();
    LinkedList<Intervalo> intervalosPadrao = new LinkedList<Intervalo>();
    int minimo = 0;
    int maximo = 255;



    public FuncaoLimiarizar() {
        carregaListaPadrao();
    }

    public FuncaoLimiarizar(Imagem imagem) {
        carregaListaPadrao();
        minimo = imagem.getValorMinimo();
        maximo = imagem.getValorMaximo();
    }

    public FuncaoLimiarizar(int minimo, int maximo) {
        carregaListaPadrao();
        this.minimo = minimo;
        this.maximo = maximo;
    }

    public int getMinimo() {
        return minimo;
    }

    public int getMaximo() {
        return maximo;
    }

    public FuncaoLimiarizar setMinimoMaximo(int minimo, int maximo){
        this.maximo = maximo;
        this.minimo = minimo;
        return this;
    }

    private FuncaoLimiarizar carregaListaPadrao() {
        intervalosPadrao.add(new Intervalo(minimo, (maximo - minimo) / 2, minimo));
        intervalosPadrao.add(new Intervalo(1 + ((maximo - minimo) / 2), maximo, maximo));
        return this;
    }

    public FuncaoLimiarizar limpaIntervalos() {
        intervalos.clear();
        return this;
    }

    public FuncaoLimiarizar addLimite(int intervaloInicial, int intervaloFinal, int valor) {
        intervalos.add(new Intervalo(intervaloInicial, intervaloFinal, valor));
        Collections.sort(intervalos);
        return this;
    }

    @Override
    public int transforma(int x, int y, int val, int rgb) {

        LinkedList<Intervalo> listaUsada =
                intervalos.size() == 0 ? intervalosPadrao : intervalos;

        if (val < listaUsada.getFirst().intervaloInicial) {
            return minimo;
        }

        if (val > listaUsada.getLast().intervaloFinal) {
            return maximo;
        }

        return listaUsada
                .stream()
                .filter(f -> val >= f.intervaloInicial && val <= f.intervaloFinal) // Aplica o intervalo fechado nas duas pontas.
                .map(i -> i.valor)// Converte em um Stream de inteiro e não de objetos.
                .findFirst() // Pega o primeiro elemento encontrado. (Podendo ter 0,1 ou + elementos.)
                .orElse(minimo); // Se tiver algum intervalo não definido então definimos que será o valor mínimo aceitável.
    }


    @Override
    public void setSoma(double x) {
        // NAO FAZ NADA :-(
    }

    @Override
    public void setMultiplicador(double x) {
        // NAO FAZ NADA :-(
    }

    @Override
    public FuncaoLimiarizar inicializa(Imagem imagem) {
        LinkedList<Intervalo> listaUsada =
                intervalos.size() == 0 ? intervalosPadrao : intervalos;
        Collections.sort(listaUsada);
        return this;
    }

    @Override
    public String toString() {

        StringBuffer saida = new StringBuffer();
        LinkedList<Intervalo> intervaloUsado = intervalos.size() == 0 ? intervalosPadrao : intervalos;

        saida.append("Intervalos nos limites:\n");
        if (intervaloUsado.getFirst().intervaloInicial > minimo) {
            saida.append((new Intervalo(minimo, intervaloUsado.getFirst().intervaloInicial - 1, minimo).toString()));
        }

        intervaloUsado.forEach(i -> {
            saida.append(i.toString() + "\n");
        });

        if (intervaloUsado.getLast().intervaloFinal < maximo) {
            saida.append((new Intervalo(intervalos.getLast().intervaloFinal + 1, maximo, maximo).toString() + "\n"));
        }

        saida.append("\nValor Mínimo = %d - Valor Máximo = %d".
                formatted(minimo, maximo));

        return saida.toString();
    }

    @Override
    public String getNome() {
        return "Limiarização :: F(X) = Regras(r = 0..N) | Rr (X) = { Se RrInicio <= X <= RrFim RrConstante Senão Mínimo ou Máximo }";
    }
}
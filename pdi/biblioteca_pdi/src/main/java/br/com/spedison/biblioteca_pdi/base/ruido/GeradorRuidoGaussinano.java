package br.com.spedison.biblioteca_pdi.base.ruido;

import br.com.spedison.usogeral.auxiliar.Arredondador;
import br.com.spedison.biblioteca_pdi.base.ruido.interfaces.GeradorRuido;

import java.util.Random;

public class GeradorRuidoGaussinano implements GeradorRuido {
    private int maximo;
    private int minimo;

    Random r = new Random();

    @Override
    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    @Override
    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    @Override
    public int getProximoNumero() {
        var a = Math.abs(r.nextGaussian());
        a *= (maximo - minimo);
        a += minimo;
        return Arredondador.arredonda(a);
    }
}

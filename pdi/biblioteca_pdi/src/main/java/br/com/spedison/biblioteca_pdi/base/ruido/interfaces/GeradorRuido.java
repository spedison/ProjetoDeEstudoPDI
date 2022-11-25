package br.com.spedison.biblioteca_pdi.base.ruido.interfaces;

public interface GeradorRuido {
    void setMaximo(int maximo);
    void setMinimo(int minimo);
    int getProximoNumero();
}

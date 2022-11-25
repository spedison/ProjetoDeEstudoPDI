package br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces;

public interface Funcao<R> extends Transformacao<R> {

    void setSoma(double x);
    void setMultiplicador(double x);

    default String getNome(){
        return "";
    }

}

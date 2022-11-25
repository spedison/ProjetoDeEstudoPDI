package br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;

/***
 * Modelo da figura 3.11 (b) Pag. 76 do Gonzalez.
 * F(X) = {
 *          Se X < A retorna X
 *          Se X >= A E X <= B retorna C
 *          Se X > A retorna X
 *        }
 */
public class TransformacaFatiamentoNivelIntensidade implements TransformacaoPixelRGB {

    int[] corteInicio;
    int[] corteFim;
    int retornoDoIntervalo;

    public TransformacaFatiamentoNivelIntensidade(int[] cortesA, int[] cortesB, int retornoDoIntervalo) {
        this.corteInicio = cortesA;
        this.corteFim = cortesB;
        this.retornoDoIntervalo = retornoDoIntervalo;

    }

    public TransformacaFatiamentoNivelIntensidade() {
        corteInicio = new int[]{100, 100, 100};
        corteFim = new int[]{150, 150, 150};
        retornoDoIntervalo = 255;
    }

    public void setCorteInicio(int[] corteInicio) {
        this.corteInicio = corteInicio;
    }

    public void setCorteFim(int[] corteFim) {
        this.corteFim = corteFim;
    }

    public void setIntervalo(int inicio, int fim, int rgb) {
        corteInicio[rgb] = inicio;
        corteFim[rgb] = fim;
    }

    public void setRetornoDoIntervalo(int retornoDoIntervalo) {
        this.retornoDoIntervalo = retornoDoIntervalo;
    }

    private int transforma(int valor, int rgb) {
        return valor >= corteInicio[rgb] && valor <= corteFim[rgb] ?
                retornoDoIntervalo :
                valor;
    }

    @Override
    public int[] transformaRGB(int[] valorPixel, int x, int y, Imagem imagem) {
        return new int[]{
                transforma(valorPixel[Imagem.Vermelho], Imagem.Vermelho),
                transforma(valorPixel[Imagem.Verde], Imagem.Verde),
                transforma(valorPixel[Imagem.Azul], Imagem.Azul)
        };
    }

    @Override
    public String toString() {
        //TODO: Fazer as considerações.
        return "Classe : %s ".
                formatted(
                        this.getClass().getSimpleName()
                );
    }

    @Override
    public String getNomeModelo() {
        return "Transformação Identidade para fora do Intervalo [Inicio,Fim] e uma constante dentro do intervalo. Gonzalez. Pag 76. Figura 3.11(b)";
    }
}
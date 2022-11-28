package br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.InferenciaPonto;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.ResultadoInferencia;

import static br.com.spedison.usogeral.auxiliar.Arredondador.*;

/***
 */
public class InferenciaPontoMaisProximo implements InferenciaPonto {

    Imagem imagem;

    public InferenciaPontoMaisProximo() {
    }

    public void setImagem(Imagem imagemOrigem) {
        this.imagem = imagemOrigem;
    }

    public void inferePonto(double pontoImagemX, double pontoImagemY, ResultadoInferencia resultado) {

        if (imagem.isPontoInvalidoInferencia(pontoImagemX, pontoImagemY)) {
            resultado.setProcessado(false);
            return;
        }

        resultado.setPonto(pontoImagemX, pontoImagemY);
        resultado.setProcessado(true);
        int x = arredonda(pontoImagemX);
        int y = arredonda(pontoImagemY);
        resultado.setValorVermelho(imagem.getPixel(x, y)[Imagem.Vermelho]);
        resultado.setValorVerde(imagem.getPixel(x, y)[Imagem.Verde]);
        resultado.setValorAzul(imagem.getPixel(x, y)[Imagem.Azul]);
    }

    @Override
    public String getNome() {
        return "InferenciaPontoMaisProximo";
    }
}
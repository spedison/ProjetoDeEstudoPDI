package br.com.spedison.biblioteca_pdi.transformacoes.pixel;

import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;

public interface TransformadorImagem<R> {
    public R transforma(TransformacaoPixelRGB transformacaoRGB);
    public R transforma(TransformacaoPixelRGB transformacaoRGB, int xMin, int xMax, int yMin, int yMax);

}

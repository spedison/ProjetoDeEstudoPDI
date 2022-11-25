package br.com.spedison.biblioteca_pdi.transformacoes.pixel;

import br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces.TransformacaoPixelRGB;

//TODO: Como processar todos os pontos de uma imagem e registrar o evento da maneira mais simples possível para que não entre em loop.
public interface TransformacaoPixelRecursivoRGB extends TransformacaoPixelRGB {
    boolean hasData();

}

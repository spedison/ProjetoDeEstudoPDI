package br.com.spedison.main.BC_Transformacoes_pixel;

import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.TransformadorPixel;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl.TransformacaoIdentidade;

public class MainTransformacaoIdentidade {
    public static void main(String[] args) {
        var c = SistemaArquivoImagens.getInstance();
        TransformadorPixel img = new TransformadorPixel(c.getDirBaseImagensEntrada("cafe1.jpg"));
        img.transforma(new TransformacaoIdentidade());
        img.salva(c.getDirBaseImagensSaida("CAFE_Identidade.jpg"));
    }
}

package br.com.spedison.biblioteca_pdi;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.padroes.ImagemPadraoFaixaDupla;
import br.com.spedison.biblioteca_pdi.padroes.ImagemPadraoLinear;

public class GeradorImagensParaTeste {

    public static Imagem criaImagemPadraoInferencia1() {
        ImagemPadraoFaixaDupla i = new ImagemPadraoFaixaDupla(11, 11);
        i.setTipoFaixa(ImagemPadraoFaixaDupla.TipoFaixa.Vertical);
        i.setCorFaixa1(new int[]{2, 2, 2});
        i.setCorFaixa2(new int[]{255, 255, 255});
        i.setLarguraFaixa1(2);
        i.setLarguraFaixa2(2);
        i.geraPadrao();
        i.setOrigemX(-5);
        i.setOrigemY(-5);
        return i;
    }

    public static Imagem criaImagemPadraoInferencia2() {
        ImagemPadraoLinear i = new ImagemPadraoLinear(11, 11);
        i.geraPadrao();
        i.setOrigemX(-5);
        i.setOrigemY(-5);
        return i;
    }



}

package br.com.spedison.biblioteca_pdi.padroes;

public class ImagemPadraoFaixaDupla extends ImagemPadrao {

    public static enum TipoFaixa {
        Vertical,
        Horizontal,
        Xadrez;
    }

    private int[] corFaixa1 = new int[]{0, 0, 0};
    private int[] corFaixa2 = new int[]{255, 255, 255};
    private int larguraFaixa1 = 50;
    private int larguraFaixa2 = 50;
    private TipoFaixa tipoFaixa = TipoFaixa.Vertical;

    public ImagemPadraoFaixaDupla(int xLen, int yLen) {
        super(xLen, yLen);
    }

    public void setCorFaixa1(int[] corFaixa1) {
        this.corFaixa1 = corFaixa1;
    }

    public void setCorFaixa2(int[] corFaixa2) {
        this.corFaixa2 = corFaixa2;
    }

    public void setLarguraFaixa1(int larguraFaixa1) {
        this.larguraFaixa1 = larguraFaixa1;
    }

    public void setLarguraFaixa2(int larguraFaixa2) {
        this.larguraFaixa2 = larguraFaixa2;
    }

    public void setTipoFaixa(TipoFaixa tipoFaixa) {
        this.tipoFaixa = tipoFaixa;
    }

    public int getLarguraTotal(){
        return larguraFaixa1 + larguraFaixa2;
    }

    @Override
    public void geraPadrao() {
        switch (tipoFaixa) {
            case Vertical -> geraPadraoVertical();
            case Horizontal -> geraPadraoHorizontal();
            case Xadrez -> geraPadraoXadrez();
        }
    }

    private void geraPadraoXadrez() {
        getStreamLargura().forEach(x -> {
            getStreamAltura().forEach(y -> {
                var posCor = x % getLarguraTotal();
                var posTroca = (y % getLarguraTotal()) < larguraFaixa1;
                var c1 = posTroca ? corFaixa2 : corFaixa1;
                var c2 = posTroca ? corFaixa1 : corFaixa2;
                if (posCor < larguraFaixa1)
                    setPixel(x, y, c1);
                else
                    setPixel(x, y, c2);
            });
        });
    }

    private void geraPadraoHorizontal() {
        getStreamLargura().forEach(x -> {
            getStreamAltura().forEach(y -> {
                var posicaoDaCor = y % getLarguraTotal();
                if (posicaoDaCor < larguraFaixa1)
                    setPixel(x, y, corFaixa1);
                else
                    setPixel(x, y, corFaixa2);
            });
        });
    }

    private void geraPadraoVertical() {
        getStreamLargura().forEach(x -> {
            getStreamAltura().forEach(y -> {
                var posCor = x % getLarguraTotal();
                if (posCor < larguraFaixa1)
                    setPixel(x, y, corFaixa1);
                else
                    setPixel(x, y, corFaixa2);
            });
        });
    }
}

package br.com.spedison.main.BJ_Ruido;

import br.com.spedison.biblioteca_pdi.base.ImagemAjustadaTela;
import br.com.spedison.biblioteca_pdi.base.OperadorEntreImagens;
import br.com.spedison.biblioteca_pdi.base.operacoes_entre_imagens.OperacaoEntreImagens;
import br.com.spedison.biblioteca_pdi.base.ruido.GeradorRuidoGaussinano;
import br.com.spedison.biblioteca_pdi.base.ruido.ImagemRuido;
import br.com.spedison.biblioteca_pdi_swing.gui.ExibidorDeImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;

public class MainGeraImagemRuido {

    public static void main(String[] args) {

        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();
        GeradorRuidoGaussinano grg = new GeradorRuidoGaussinano();
        OperadorEntreImagens imgOpera = new OperadorEntreImagens(sai.getDirBaseImagensEntrada("cafe1.jpg"));
        grg.setMaximo(50);
        grg.setMinimo(0);
        ImagemRuido imgRuido = new ImagemRuido(imgOpera.getLargura(),imgOpera.getAltura(), grg);
        imgRuido.geraImagem();

        imgOpera.operacaoNaImagem(imgRuido, OperacaoEntreImagens.Subtracao.getOperacao()    );
        new ExibidorDeImagem("Imagem com Ruido", new ImagemAjustadaTela (imgOpera, 0.7), true);


    }

}

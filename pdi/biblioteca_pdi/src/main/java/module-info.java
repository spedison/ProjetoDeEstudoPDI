module br.com.spedison.biblioteca_pdi {
    exports br.com.spedison.biblioteca_pdi.base;
    exports br.com.spedison.biblioteca_pdi.base.ruido;
    exports br.com.spedison.biblioteca_pdi.base.ruido.interfaces;
    exports br.com.spedison.biblioteca_pdi.estatistica;
    exports br.com.spedison.biblioteca_pdi.projeto;
    exports br.com.spedison.biblioteca_pdi.transformacoes.espacial;
    exports br.com.spedison.biblioteca_pdi.transformacoes.pixel;
    exports br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;
    exports br.com.spedison.biblioteca_pdi.transformacoes.pixel.impl;
    exports br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels;
    exports br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao;
    exports br.com.spedison.biblioteca_pdi.base.enuns;
    exports br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao;
    exports br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes;
    exports br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias;
    exports br.com.spedison.biblioteca_pdi.base.operacoes_entre_imagens;
    exports br.com.spedison.biblioteca_pdi.transformacoes.pixel.interfaces;
    exports br.com.spedison.biblioteca_pdi.padroes;
    requires java.desktop;
    requires commons.io;
    requires commons.math3;
    requires org.apache.commons.lang3;
    requires br.com.spedison.usogeral;
}
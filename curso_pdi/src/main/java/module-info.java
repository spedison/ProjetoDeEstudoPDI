module br.com.spedison.curso_pdi {
    requires br.com.spedison.biblioteca_pdi;
    requires br.com.spedison.biblioteca_pdi_javafx;
    requires br.com.spedison.biblioteca_pdi_swing;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.controls;
    requires jcodec;
    exports br.com.spedison.main.AA_Janelas;
    exports br.com.spedison.main.BA_Apresentacao;
    exports br.com.spedison.main.BH_TransformacaoEspacial;
}
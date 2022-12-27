module br.com.spedison.pds_testes {
    requires java.desktop;
    //requires commons.io;
    //requires commons.math3;
    //requires org.apache.commons.lang3;
    requires br.com.spedison.usogeral;
    //requires br.com.spedison.biblioteca_pdi;
    requires junit;
    requires br.com.spedison.pds;
    opens br.com.spedison.pds_test.ferramentas;
}
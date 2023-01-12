module br.com.spedison.pds {
    requires transitive java.desktop;
    requires br.com.spedison.usogeral;
    requires org.knowm.xchart;
    exports br.com.spedison.pds.auxiliar;
    exports br.com.spedison.pds;
    exports br.com.spedison.pds.convolucao;
    exports br.com.spedison.pds.mostragraficos;
}
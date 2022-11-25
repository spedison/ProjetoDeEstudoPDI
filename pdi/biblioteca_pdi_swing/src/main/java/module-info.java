module br.com.spedison.biblioteca_pdi_swing {
    requires transitive java.desktop;
    requires org.jfree.jfreechart;
    requires br.com.spedison.biblioteca_pdi;
    exports br.com.spedison.biblioteca_pdi_swing.gui;
}
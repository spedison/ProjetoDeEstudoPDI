package br.com.spedison.biblioteca_pdi.padroes;

import java.awt.*;

@FunctionalInterface
public interface ProcessaPadrao {
    int[] processaPadrao(Point p, int[] rgb);
}

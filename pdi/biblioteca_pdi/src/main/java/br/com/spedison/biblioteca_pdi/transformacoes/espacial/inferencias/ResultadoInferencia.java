package br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias;

import java.awt.*;

public class ResultadoInferencia {
    int valorVermelho;
    int valorVerde;
    int valorAzul;

    double valorVermelhoDouble;
    double valorVerdeDouble;
    double valorAzulDouble;
    boolean processado;
    double pontoX;
    double pontoY;

    Point [] pontosUsados;

    String msgError;

    public int getValorVermelho() {
        return valorVermelho;
    }

    public void setValorVermelho(int valorVermelho) {
        this.valorVermelho = valorVermelho;
    }

    public int getValorVerde() {
        return valorVerde;
    }

    public int[] getArrayRGB() {
        return new int[]{valorVermelho, valorVerde, valorAzul};
    }


    public void setValorVerde(int valorVerde) {
        this.valorVerde = valorVerde;
    }

    public int getValorAzul() {
        return valorAzul;
    }

    public void setValorAzul(int valorAzul) {
        this.valorAzul = valorAzul;
    }

    public boolean isProcessado() {
        return processado;
    }

    public void setProcessado(boolean processado) {
        this.processado = processado;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public double[] getPonto() {
        return new double[]{pontoX, pontoY};
    }

    public double getPontoX() {
        return pontoX;
    }

    public double getPontoY() {
        return pontoY;
    }

    public void setPonto(double pontoX, double pontoY) {
        this.pontoX = pontoX;
        this.pontoY = pontoY;
    }

    public double getValorVermelhoDouble() {
        return valorVermelhoDouble;
    }

    public void setValorVermelhoDouble(double valorVermelhoDouble) {
        this.valorVermelhoDouble = valorVermelhoDouble;
    }

    public double getValorVerdeDouble() {
        return valorVerdeDouble;
    }

    public void setValorVerdeDouble(double valorVerdeDouble) {
        this.valorVerdeDouble = valorVerdeDouble;
    }

    public double getValorAzulDouble() {
        return valorAzulDouble;
    }

    public void setValorAzulDouble(double valorAzulDouble) {
        this.valorAzulDouble = valorAzulDouble;
    }

    public Point[] getPontosUsados() {
        return pontosUsados;
    }

    public void setPontosUsados(Point[] pontosUsados) {
        this.pontosUsados = pontosUsados;
    }

    @Override
    public String toString() {
        return "ResultadoInferencia{" +
                "valorVermelho=" + valorVermelho +
                ", valorVerde=" + valorVerde +
                ", valorAzul=" + valorAzul +
                ", valorVermelhoDouble=" + valorVermelhoDouble +
                ", valorVerdeDouble=" + valorVerdeDouble +
                ", valorAzulDouble=" + valorAzulDouble +
                ", processado=" + processado +
                ", pontoX=" + pontoX +
                ", pontoY=" + pontoY +
                ", msgError='" + msgError + '\'' +
                '}';
    }
}

package br.com.spedison.biblioteca_pdi.base;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

public class Imagem implements Cloneable {
    private BufferedImage bi;

    static public final int Vermelho = 0;
    static public final int Verde = 1;
    static public final int Azul = 2;
    public static final int Cinza = 3;
    public static final int Todos = 4;


    private int valorMaximo = 255;
    private int valorMinimo = 0;

    private int origemX = 0;
    private int origemY = 0;

    Graphics grafico = null;
    private String nomeArquivo;


    public int limitaValores(int valor) {
        int ret = 0;
        if (valor > valorMaximo)
            ret = valorMaximo;
        else if (valor > 0)
            ret = valor;
        return ret;
    }

    public int[] limitaValores(int[] valor) {
        for (int i = 0; i < valor.length; i++) {
            valor[i] = limitaValores(valor[i]);
        }
        return valor;
    }


    public int getValorMaximo() {
        return valorMaximo;
    }

    public Imagem setValorMaximo(int valorMaximo) {
        this.valorMaximo = valorMaximo;
        return this;
    }

    public int getValorMinimo() {
        return valorMinimo;
    }

    public Imagem setValorMinimo(int valorMinimo) {
        this.valorMinimo = valorMinimo;
        return this;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public int getOrigemX() {
        return origemX;
    }

    public Imagem setOrigemX(int origemX) {
        this.origemX = origemX;
        return this;
    }

    public int getOrigemY() {
        return origemY;
    }

    public Imagem setOrigemY(int origemY) {
        this.origemY = origemY;
        return this;
    }

    public Imagem(BufferedImage bi) {
        this.bi = bi;
    }

    public Imagem(String nomeArquivo) {
        this.leArquivo(nomeArquivo);
    }

    public Imagem() {
        this.leArquivo();
    }

    public Imagem(Imagem img) {
        copiaImagem(img);
    }

    public Imagem copiaImagem(Imagem img) {
        this.bi = img.cloneBI().getImageBuffer(); // Separa Buffer de imagem dos 2 objetos.
        this.valorMaximo = img.valorMaximo;
        this.valorMinimo = img.valorMinimo;
        this.nomeArquivo = img.nomeArquivo;
        this.origemX = img.origemX;
        this.origemY = img.origemY;
        return this;
    }

    public Imagem(int xLen, int yLen) {
        novaImagem(xLen, yLen);
    }

    public Imagem(int xLen, int yLen, int rgbCorFundo) {
        this(xLen, yLen, rgbCorFundo, BufferedImage.TYPE_INT_RGB);
    }

    public Imagem(int xLen, int yLen, int rgbCorFundo, int type) {
        novaImagem(xLen, yLen, type);
        pintaFundo(rgbCorFundo);
    }

    public void pintaFundo(int rgbCorFundo) {
        //if (rgbCorFundo != 0x000000) {
        Graphics g = bi.getGraphics();
        g.setColor(new Color(rgbCorFundo));
        g.fillRect(0, 0, getLargura(), getAltura());
        g.dispose();
        //}
    }

    public void pintaLinha(int linha, int largura, int rgbCor) {
        //if (rgbCorFundo != 0x000000) {
        Graphics g = bi.getGraphics();
        g.setColor(new Color(rgbCor));
        g.fillRect(0, linha, getLargura(), largura);
        g.dispose();
        //}
    }

    public void pintaColuna(int coluna, int largura, int rgbCor) {
        //if (rgbCorFundo != 0x000000) {
        Graphics g = bi.getGraphics();
        g.setColor(new Color(rgbCor));
        g.fillRect(coluna, 0, largura, getAltura());
        g.dispose();
        //}
    }


    public Imagem novaImagem(int xLen, int yLen, int type) {
        bi = new BufferedImage(xLen, yLen, type);
        return this;
    }

    public Imagem novaImagem(int xLen, int yLen) {
        return novaImagem(xLen, yLen, BufferedImage.TYPE_INT_RGB);
    }

    public Imagem(int[] size) {
        assert size.length == 2 : "Tamanho do array deve ser 2. Dimensões X e Y.";
        novaImagem(size[0], size[1]);
    }

    public int[] getTamanho() {
        return new int[]{bi.getWidth(), bi.getHeight()};
    }

    public int getLargura() {
        return bi.getWidth();
    }

    public int getAltura() {
        return bi.getHeight();
    }

    public int getTipo() {
        return bi.getType();
    }


    public Imagem leArquivo() {
        return leArquivo(null);
    }

    public Imagem leArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        if (nomeArquivo == null) bi = new BufferedImage(200, 500, BufferedImage.TYPE_INT_RGB);
        else {
            try {
                bi = ImageIO.read(new File(nomeArquivo));
                return this;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public Imagem setColor(Color color) {
        Graphics g = getGraphics();
        g.setColor(color);
        return this;
    }

    public Imagem setFonte(Font fonte) {
        Graphics g = getGraphics();
        g.setFont(fonte);
        return this;
    }

    public Imagem escreveTexto(String nome, int x, int y) {
        Graphics g = getGraphics();
        g.drawString(nome, x - origemX, y - origemY);
        return this;
    }

    public Graphics getGraphics() {
        if (grafico == null)
            grafico = this.bi.createGraphics();
        return grafico;
    }

    public int[] getPixel(int x, int y) {
        return getPixel(x, y, null);
    }

    public int[] getPixel(int x, int y, int arrRet[]) {
        // 0x00RRGGBB
        var ret = arrRet == null ? new int[3] : arrRet;
        var pxRGB = bi.getRGB(x - origemX, y - origemY);
        ret[Vermelho] = (pxRGB & 0x00FF0000) >> 16;
        ret[Verde] = (pxRGB & 0x0000FF00) >> 8;
        ret[Azul] = (pxRGB & 0x000000FF);
        return ret;
    }

    public Imagem setPixel(int x, int y, int arrPx[]) {
        // 0x00RRGGBB
        int px = (arrPx[Azul] & 0x000000FF) | ((arrPx[Verde] << 8) & 0x0000FF00) | ((arrPx[Vermelho] << 16) & 0x00FF0000);
        bi.setRGB(x - origemX, y - origemY, px);
        return this;
    }

    public Imagem setRGB(int x, int y, int rgb) {
        bi.setRGB(x - origemX, y - origemY, rgb);
        return this;
    }

    public int getRGB(int x, int y) {
        return bi.getRGB(x - origemX, y - origemY);
    }

    public Imagem salva(String fileName) {
        var ext = FilenameUtils.getExtension(fileName).toLowerCase();
        try {
            ImageIO.write(bi, ext, new File(fileName));
            return this;
        } catch (IOException e) {
            System.out.println("Erro ao escrever arquivo : " + fileName);
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getImageBuffer() {
        return bi;
    }

    public Imagem setImageBuffer(BufferedImage img) {
        this.bi = img;
        return this;
    }

    public Imagem copiaImagem(int xInicio, int yInicio, int xFim, int yFim) {
        Imagem ret = new Imagem(xFim - xInicio, yFim - yInicio);

        IntStream.range(xInicio, Math.min(getTamanho()[0], xInicio + ret.getTamanho()[0])).parallel().forEach(
                xPos -> {
                    IntStream.range(yInicio, Math.min(getTamanho()[1], yInicio + ret.getTamanho()[1])).parallel().forEach(
                            yPos -> {
                                ret.setRGB(xPos - xInicio, yPos - yInicio, this.getRGB(xPos, yInicio));
                            });
                });

        return ret;
    }

    public Imagem copiaImagem(int xFIm, int yFim) {
        return copiaImagem(0, 0, xFIm, yFim);
    }

    public Imagem copiaImagemDe(Imagem fonte, int xInicio, int yInicio) {
        IntStream.range(xInicio, Math.min(getTamanho()[0], xInicio + fonte.getTamanho()[0])).parallel().forEach(
                xPos -> {
                    IntStream.range(yInicio, Math.min(getTamanho()[1], yInicio + fonte.getTamanho()[1])).parallel().forEach(
                            yPos -> {
                                setRGB(xPos, yPos, fonte.getRGB(xPos - xInicio, yPos - yInicio));
                            });
                });
        return this;
    }

    public void copiaImagemPara(Imagem destino, int xInicio, int yInicio) {
        IntStream.range(xInicio, Math.min(getTamanho()[0], xInicio + destino.getTamanho()[0])).parallel().forEach(
                xPos -> {
                    IntStream.range(yInicio, Math.min(getTamanho()[1], yInicio + destino.getTamanho()[1])).parallel().forEach(
                            yPos -> {
                                destino.setRGB(xPos - xInicio, yPos - yInicio, getRGB(xPos, yPos));
                            });
                });
    }

    public int getLimiteX() {
        return origemX + getLargura() - 1;
    }

    public int getLimiteY() {
        return origemY + getAltura() - 1;
    }

    public void validaLimitesImagem(int xMin, int xMax, int yMin, int yMax) {
        assert !isPontoInvalido(xMin, yMin) : "Ponto (" + xMin + "," + yMin + ") Mínimo não pode ser menor que (" + getOrigemX() + "," + getOrigemY() + ")";
        assert !isPontoInvalido(xMax, yMax) : "Ponto (" + xMax + "," + yMax + ") Maximo não pode ser maior que (" + getLimiteX() + "," + getLimiteY() + ")";
        assert xMax >= xMin && yMax >= yMin : "Ponto Mínimo <= Ponto Máximo";
    }

    public Imagem cloneBI() {
        Imagem retorno = new Imagem(getTamanho());
        Graphics g = retorno.getImageBuffer().getGraphics();
        g.drawImage(getImageBuffer(), 0, 0, null);
        g.dispose();
        return retorno;
    }

    @Override
    public Imagem clone() {
        return new Imagem(this);
    }


    public IntStream getStreamLargura() {
        return IntStream.range(origemX, getLimiteX() + 1);
    }

    public IntStream getStreamLargura(int xMin, int xMax) {
        return getStreamLargura().filter(x -> x >= xMin && x <= xMax);
    }

    public boolean isBorda(int x, int y) {
        return (x == origemX) ||
                (y == origemY) ||
                (x == (origemX + getLargura() - 1)) ||
                (y == (origemY + getAltura()) - 1);
    }

    public IntStream getStreamAltura() {
        return IntStream.range(getOrigemY(), getLimiteY() + 1);
    }

    public IntStream getStreamAltura(int yMin, int yMax) {
        return getStreamAltura().filter(y -> y >= yMin && y <= yMax);
    }

    Stack<Point> guardaPontosOrigem = new Stack<>();

    protected Imagem guardaOrigem() {
        Point p = new Point(origemX, origemX);
        guardaPontosOrigem.push(p);
        return this;
    }

    protected Imagem restauraOrigem() {
        Point p = guardaPontosOrigem.pop();
        this.origemX = p.x;
        this.origemY = p.y;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Imagem)) return false;

        Imagem imagem = (Imagem) o;

        if (this.getAltura() != imagem.getAltura()) return false;
        if (this.getLargura() != imagem.getLargura()) return false;
        boolean[] igual = {true};
        IntStream.range(0, getAltura()).filter(v -> igual[0]).forEach(
                posY -> {
                    IntStream.range(0, getLargura()).filter(v -> igual[0]).forEach(
                            posX -> {
                                igual[0] &= getRGB(posX, posY) == imagem.getRGB(posX, posY);
                            }
                    );
                }
        );
        return igual[0];
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37).append(bi).toHashCode();
    }

    public boolean isPontoInvalido(int x, int y) {
        return (x < getOrigemX()) ||
                (y < getOrigemY()) ||
                (x > getLimiteX()) ||
                (y > getLimiteY());
    }

    public boolean isPontoValidoInferencia(double x, double y) {
        return (x >= getOrigemX()) &&
                (y >= getOrigemY()) &&
                (x <= getLimiteX()) &&
                (y <= getLimiteY());
    }

    public boolean isPontoInvalidoInferencia(double x, double y) {
        return !(
                isPontoValidoInferencia(x, y)
        );
    }


    @Override
    public String toString() {
        return "Imagem{" +
                "bi = " + bi.toString() +
                ", Tamanho = (" + bi.getWidth() + " x " + bi.getHeight() + ")" +
                ", valorMaximo=" + valorMaximo +
                ", valorMinimo=" + valorMinimo +
                ", origemX=" + origemX +
                ", origemY=" + origemY +
                ", limiteX=" + getLimiteX() +
                ", limiteY=" + getLimiteY() +
                // ", grafico=" + grafico +
                ", nomeArquivo='" + nomeArquivo + '\'' +
                ", guardaPontosOrigem=" + guardaPontosOrigem.toString() +
                '}';
    }
}
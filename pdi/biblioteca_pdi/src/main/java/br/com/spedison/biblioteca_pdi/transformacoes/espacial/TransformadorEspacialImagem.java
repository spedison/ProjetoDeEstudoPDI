package br.com.spedison.biblioteca_pdi.transformacoes.espacial;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.InferenciaPonto;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.ResultadoInferencia;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.InferenciaPontoMaisProximo;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoEspacial;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class TransformadorEspacialImagem extends Imagem {

    int corPadrao = 0x00FFFFFF;

    public TransformadorEspacialImagem(BufferedImage bi) {
        super(bi);
    }

    public TransformadorEspacialImagem(String nomeArquivo) {
        super(nomeArquivo);
    }

    public TransformadorEspacialImagem() {
    }

    public TransformadorEspacialImagem(Imagem img) {
        super(img);
    }

    public TransformadorEspacialImagem(int xLen, int yLen) {
        super(xLen, yLen);
    }

    public TransformadorEspacialImagem(int xLen, int yLen, int rgbCorFundo) {
        super(xLen, yLen, rgbCorFundo);
    }

    public TransformadorEspacialImagem(int xLen, int yLen, int rgbCorFundo, int type) {
        super(xLen, yLen, rgbCorFundo, type);
    }

    public TransformadorEspacialImagem(int[] size) {
        super(size);
    }

    public void setCorPadrao(int corPadrao) {
        this.corPadrao = corPadrao;
    }

    @Override
    public int getRGB(int x, int y) {
        if (isPontoInvalido(x, y))
            return this.corPadrao;

        return super.getRGB(x, y);
    }

    class ResutadoProcessamentoLocal {
        double[] ponto;
        String msg;

        public ResutadoProcessamentoLocal(double[] ponto, String msg) {
            this.ponto = ponto;
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "ResutadoProcessamentoLocal{" +
                    "ponto=" + Arrays.toString(ponto) +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }

    public TransformadorEspacialImagem aplicaTransformacao(MatrizTransformacaoEspacial matrix, double multiplicadorX, double multiplicadorY,
                                                           int origemOrigX, int origemOrigY,
                                                           int origemDestX, int origemDestY) {

        return aplicaTransformacao(matrix, multiplicadorX, multiplicadorY, origemOrigX, origemOrigY, origemDestX, origemDestY, new InferenciaPontoMaisProximo());
    }

    public TransformadorEspacialImagem aplicaTransformacao(MatrizTransformacaoEspacial matrix, double multiplicadorX, double multiplicadorY,
                                                           int origemOrigX, int origemOrigY,
                                                           int origemDestX, int origemDestY, InferenciaPonto inferenciaPonto) {

        Imagem destino = new Imagem((int) (getLargura() * multiplicadorX), (int) (getAltura() * multiplicadorY));
        destino.setOrigemX(origemDestX);
        destino.setOrigemY(origemDestY);

        this.guardaOrigem();
        this.setOrigemX(origemOrigX);
        this.setOrigemY(origemOrigY);
        inferenciaPonto.setImagem(this);

        List<ResutadoProcessamentoLocal> lr = new Vector<>();

        destino
                .getStreamAltura()
                .parallel()
                .forEach(
                        posYDestino -> {
                            destino
                                    .getStreamLargura()
                                    .parallel()
                                    .forEach(
                                            posXDestino -> {
                                                double[] pontoNaOrigem = matrix.calculaPontoInverso(posXDestino, posYDestino);
                                                ResultadoInferencia r = new ResultadoInferencia();
                                                inferenciaPonto.inferePonto(pontoNaOrigem[0], pontoNaOrigem[1], r);
                                                if (r.isProcessado())
                                                    destino.setPixel(posXDestino, posYDestino, r.getArrayRGB());
                                                else
                                                    destino.setRGB(posXDestino, posYDestino, corPadrao);

                                                if (!r.getMsgError().equals("Ponto Processado com Sucesso."))
                                                    lr.add(new ResutadoProcessamentoLocal(r.getPonto(), r.getMsgError()));
                                            }
                                    );
                        }
                );

//        Path p = Path.of("/tmp/log.log");
//        try {
//            //FileOutputStream osw  = new FileOutputStream(p.toString());
//            FileWriter fw = new FileWriter(p.toFile());
//            BufferedWriter bw = new BufferedWriter(fw);
//            lr
//                    .stream()
//                    .map(ResutadoProcessamentoLocal::toString)
//                    .forEach(s -> {
//                        try {
//                            bw.write(s);
//                            bw.newLine();
//                        } catch (IOException ioe) {
//                        }
//                    });
//            bw.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        this.restauraOrigem();
        this.setImageBuffer(destino.getImageBuffer());
        return this;
    }

}

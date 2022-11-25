package br.com.spedison.biblioteca_pdi.inferencias;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.TransformadorInferencia;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.InferenciaPontoBilinearV1;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.stream.IntStream;

public class TransformadorInferenciaTest {

    @Test
    public void testTransformaUmaCor() {

        Imagem imgEntrada = new Imagem(10, 10);
        imgEntrada.pintaFundo(Color.BLUE.getRGB());
        Imagem imgComparacao = new Imagem(imgEntrada);

        InferenciaPontoBilinearV1 ipbl = new InferenciaPontoBilinearV1();
        TransformadorInferencia ti = new TransformadorInferencia(imgEntrada);
        ti.setInferenciaPonto(ipbl);
        ti.transforma();

        Assert.assertEquals(imgEntrada, imgComparacao);
    }

    @Test
    public void testTransformaZebrado() {

        Imagem imgEntrada = new Imagem(10, 10);
        imgEntrada.pintaFundo(Color.BLUE.getRGB());

        IntStream.range(0, imgEntrada.getLargura()).filter(i -> i % 2 == 0).forEach( i ->
                imgEntrada.pintaLinha(i,1,Color.WHITE.getRGB())
        );

        Imagem imgComparacao = new Imagem(imgEntrada);
        imgComparacao.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("Linhas.bmp"));

        InferenciaPontoBilinearV1 ipbl = new InferenciaPontoBilinearV1();
        TransformadorInferencia ti = new TransformadorInferencia(imgEntrada);
        ti.setInferenciaPonto(ipbl);
        ti.transforma();
        ti.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("Linhas-Transformada.bmp"));

    }

}
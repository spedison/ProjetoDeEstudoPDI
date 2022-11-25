package br.com.spedison.main.BH_TransformacaoEspacial;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.TransformadorEspacialImagem;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoRotacao;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.InferenciaPonto;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.*;

import java.awt.*;
import java.io.IOException;
import java.util.Date;

public class Main_Aula41_RodandoImagens {


    public static void main(String[] args) throws IOException {

        // InferenciaPonto ip = new InferePontoCubica(); // InferenciaPontoBilinearV2();

        InferenciaPonto ip1 = new InferenciaPontoCubica();
        InferenciaPonto ip2 = new InferenciaPontoBilinearV1();
        InferenciaPonto ip3 = new InferenciaPontoBilinearV2();
        InferenciaPonto ip4 = new InferenciaPontoBilinearV3();
        InferenciaPonto ip5 = new InferenciaPontoMaisProximo();

        InferenciaPonto[] ips = new InferenciaPonto[]{ip1}; //, ip2, ip4, ip5};


        String nomeArquivoVideoSaida = "rotacoes_InferenciaCubica_V5";

        var sai = SistemaArquivoImagens.getInstance();
        // Imagem imgOriginal = new Imagem(sai.getDirBaseImagensEntrada("cafe1.jpg")); //  new ImagemAjustadaTela(sai.getDirBaseImagensEntrada("cafe1.jpg"), 1.5);
        Imagem imgOriginal = new Imagem(sai.getDirBaseImagensEntrada("cafe1.jpg")); //  new ImagemAjustadaTela(sai.getDirBaseImagensEntrada("cafe1.jpg"), 1.5);
        // Imagem imgOriginal = new ImagemAjustadaTela(sai.getDirBaseImagensEntrada("cafe1.jpg"), 0.53);


        final double multiplicador = 1.05;
        MatrizTransformacaoRotacao mtr = new MatrizTransformacaoRotacao();
        System.out.printf("%s - Iniciando processamento.\n", new Date());

        var anguloRotacaoGraus = 4.2;
        {

            for (InferenciaPonto ip : ips) {
                System.out.printf("%s - Processando ângulo %f - Com método %s\n", new Date(), anguloRotacaoGraus, ip.getNome());
                TransformadorEspacialImagem tei = new TransformadorEspacialImagem(imgOriginal);
                int tamFonte = (int) (.03 * tei.getAltura());
                tei.aplicaTransformacao(
                                mtr.setAnguloGraus(anguloRotacaoGraus),
                                multiplicador, multiplicador,
                                (-imgOriginal.getLargura() / 2), (-imgOriginal.getAltura() / 2),
                                (int) (-imgOriginal.getLargura() * (multiplicador / 2.0)), (int) (-imgOriginal.getAltura() * (multiplicador / 2.0)), ip)
                        .setColor(Color.RED)
                        .setFonte(new Font("Arial", Font.BOLD, tamFonte))
                        .escreveTexto("Angulo = %.3f".formatted(anguloRotacaoGraus), 10, tamFonte+5);

                tei.salva(sai.getDirBaseImagensSaida("%s_%s.bmp".formatted(nomeArquivoVideoSaida, ip.getNome())));
            }
        }

    }

}

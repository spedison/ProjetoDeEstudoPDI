package br.com.spedison.biblioteca_pdi_testes.transformacoes.espacial;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.TransformadorEspacialImagem;
import br.com.spedison.biblioteca_pdi_testes.comum.CriaImagemFaixa;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.inferencias.implementacoes.InferenciaPontoBilinearV1;
import br.com.spedison.biblioteca_pdi.transformacoes.espacial.implementacao.MatrizTransformacaoRotacao;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformacaoConvolucaoBasico;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.TransformadorConvolucao;
import br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels.TipoKernelGaussiano;
import org.junit.Test;

public class TransformadorEspacialImagemRotacaoAliasing {

    @Test
    public void criaImagemAliasing() {
        final var multiplicador = 1.0;
        final var angulo = 5.;
        final var tamanho = 500;
        // Cria um padrão de imagem com faixas.
        Imagem img = CriaImagemFaixa.criaFaixaVertical(tamanho, 2, 3);

        // Cria um processador de convolução com Kernel Gaussiano
        TipoKernelGaussiano tkg = new TipoKernelGaussiano(0, 0, 1, 5);
        tkg.criaDadosKernel(1., 0.);
        tkg.normalizaSomaUnitaria(1.);

        TransformadorConvolucao conv = new TransformadorConvolucao();
        TransformacaoConvolucaoBasico tcb = new TransformacaoConvolucaoBasico(tkg);

        // Rotaciona a imagem do padrão em 21 Graus para horário
        TransformadorEspacialImagem tei1 = new TransformadorEspacialImagem(img);
        MatrizTransformacaoRotacao mtr = new MatrizTransformacaoRotacao(angulo);
        tei1.setCorPadrao(0x000000);
        tei1.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste_original.bmp"));
        tei1.aplicaTransformacao(mtr, multiplicador, multiplicador,
                -tamanho/2, -tamanho/2, -tamanho/2, -tamanho/2,new InferenciaPontoBilinearV1());

        tei1.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("teste_rot.bmp"));
        return;
        /*
        // Rotaciona a imagem do padrão em 21 Graus para anti - horário
        var tei2 = new TransformadorEspacialImagem(img);
        mtr.setAnguloGraus(-angulo);
        tei2.aplicaTransformacao(mtr, multiplicador, multiplicador,
                -250, -250, -250, -250, new InferePontoBilinear());

        OperadorEntreImagens oei = new OperadorEntreImagens(tei1);
        oei.operacaoNaImagem(tei2, (p1, p2, resultado) -> {
                    if  (
                            (p1[Imagem.Vermelho] > 240 && p1[Imagem.Verde] < 20 && p1[Imagem.Azul] < 20 ) ||
                            (p2[Imagem.Vermelho] > 240 && p2[Imagem.Verde] < 20 && p2[Imagem.Azul] < 20 )
                    ) {
                        resultado[Imagem.Vermelho] = 0xFF;
                        resultado[Imagem.Verde] = 0x0;
                        resultado[Imagem.Azul] = 0x0;
                    } else  {
                        resultado[Imagem.Vermelho] = 0xFF;
                        resultado[Imagem.Verde] = 0xFF;
                        resultado[Imagem.Azul] = 0xFF;
                    }
                    return resultado;
                }
        );

        oei.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("faixa_rotacionada_" + angulo + ".bmp")); */

/*        for (int angulo = 1800; angulo < 3010; angulo++) {
            tei1.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("faixa_rotacionada_" + angulo + ".bmp"));
            conv.setImageBuffer(tei1.getImageBuffer());
            conv.transforma(tcb);
            conv.salva(SistemaArquivoImagens.getInstance().getDirBaseImagensSaida("CONVOLUCAO__faixa_rotacionada_" + angulo + ".bmp"));
        }*/
    }
}

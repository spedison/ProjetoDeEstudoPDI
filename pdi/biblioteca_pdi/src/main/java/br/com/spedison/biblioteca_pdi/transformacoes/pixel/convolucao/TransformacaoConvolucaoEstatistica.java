package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.enuns.CanalProcessado;

import java.util.*;
import java.util.stream.IntStream;


/***
 * Usar um kernel básico com 1 em todos os elementos da matrix e realizer operaçoes estatísticas : mediana, média, min, max, moda.
 * Baseado na Figura 3.35 Pagina 103. Cap. 3.6
 */
public class TransformacaoConvolucaoEstatistica implements TransformacaoConvolucaoInterface<TransformacaoConvolucaoEstatistica> {

    private TipoOperacao tipoOperacao = TipoOperacao.TipoOperacaoMedia;

    private double[][][] data;

    double soma = 0.;
    double multiplica = 1.;

    private CanalProcessado[] canaisProcessados = {CanalProcessado.Vermelho, CanalProcessado.Verde, CanalProcessado.Azul};

    // Construtores :: INICIO
    public TransformacaoConvolucaoEstatistica(TipoOperacao tipoOperacao) {
        this(3, tipoOperacao);
    }

    public TransformacaoConvolucaoEstatistica(TipoOperacao tipoOperacao, int tamanhoKernel, double soma, double multiplica) {
        this(tamanhoKernel, tipoOperacao);
        this.soma = soma;
        this.multiplica = multiplica;
    }

    public TransformacaoConvolucaoEstatistica(double[][][] data, TipoOperacao tipoOperacao) {
        setData1(data);
    }

    public TransformacaoConvolucaoEstatistica(int tamanho, TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
        double[] on = new double[tamanho];
        Arrays.fill(on, 1.0);
        double[][] onn = new double[tamanho][tamanho];
        Arrays.fill(onn, on);
        data = new double[][][]{onn, onn, onn};
    }

    // Construtores :: FIM

    // Setters :: INICIO

    public TransformacaoConvolucaoEstatistica setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
        return this;
    }

    public TransformacaoConvolucaoEstatistica setData(double[][][] data) {
        this.data = data;
        return this;
    }

    public TransformacaoConvolucaoEstatistica setSoma(double soma) {
        this.soma = soma;
        return this;
    }

    public TransformacaoConvolucaoEstatistica setMultiplica(double multiplica) {
        this.multiplica = multiplica;
        return this;
    }

    public TransformacaoConvolucaoEstatistica setCanaisProcessados(CanalProcessado[] canaisProcessados) {
        this.canaisProcessados = canaisProcessados;
        return this;
    }

    // Setters :: FIM

    // Aqui estão as operações estatísticas básicas. INICIO
    private double calculaMax(List<Double> dados) {
        return dados.stream().max(Double::compareTo).get();
    }

    private double calculaMin(List<Double> dados) {
        return dados.stream().min(Double::compareTo).get();
    }

    private double calculaMedia(List<Double> dados) {
        return dados.stream().reduce((v1, v2) -> v1 + v2).get() / dados.size();
    }

    private double calculaMediana(List<Double> dados) {
        double ret = 0.;
        dados.sort(Double::compareTo);

        if (dados.size() > 0) {
            // Se for par.
            if (dados.size() % 2 == 0) {
                double ret1 = dados.get((dados.size() - 1) / 2);
                double ret2 = dados.get((dados.size() + 1) / 2);
                ret = (ret1 + ret2) / 2.;
            } else { // Se for ímpar simplesmente pega o central.
                ret = dados.get(dados.size() / 2);
            }
        }
        return ret;
    }

    private double calculaModa(List<Double> dadosDouble) {
        final double fator = 1000000.;
        List<Long> dadosLong = dadosDouble.stream().map(i -> Long.valueOf((long)(i * fator))).toList();

        // Resume os dados em um map.
        Map<Long, Integer> resultado = new HashMap<>(); // Número(K) e Contagem(V)
        dadosLong.forEach(i -> {
            int conta = 1;
            if (resultado.containsKey(i)) {
                conta = resultado.get(i) + 1;
            }
            resultado.put(i, conta);
        });
        //
        int[] maxValor = {-1};
        long[] maxPosicao = {-1L};
        resultado.forEach((k, v) -> {
            if (v > maxValor[0]) {
                maxValor[0] = v;
                maxPosicao[0] = k;
            }
        });

        return ((double) maxPosicao[0]) / fator;
    }
    /***
     * Função para integrar as operações estatísticas básicas, realizar o arredondadomento, limitar e retornar como inteiro
     * @param dados Lista dos pixels convolucionados.
     * @param img Imagem (usada para limitar o range)
     * @return resultado da operação estatística
     */
    private int aplicaOperacaoEstatistica(List<Double> dados, Imagem img) {

        if (dados.isEmpty()) return 0;

        double ret = switch (tipoOperacao) {
            case TipoOperacaoMax -> calculaMax(dados);
            case TipoOperacaoMin -> calculaMin(dados);
            case TipoOperacaoMedia -> calculaMedia(dados);
            case TipoOperacaoMediana -> calculaMediana(dados);
            case TipoOperacaoModa -> calculaModa(dados);
        };
        return img.limitaValores((int) (ret + 0.5));
    }

    // Aqui estão as operações estatísticas básicas. FIM

    /***
     * Retorna True ou False se o canal será processado.
     * @param canalProcessado
     * @return True: Canal Será processado, False: O Canal não será processado, retorno = valor original.
     */
    private boolean isProcessaCor(CanalProcessado canalProcessado) {
        final boolean[] ret = {false};
        Arrays.stream(canaisProcessados).
                filter(i -> !ret[0]).
                forEach(canal -> ret[0] |= canalProcessado == canal);
        return ret[0];
    }


    // Operações de Get e Set Sobreescrevidas do Kernel.
    @Override
    public double[][][] getData1() {
        return data;
    }

    @Override
    public TransformacaoConvolucaoEstatistica setData1(double[][][] data) {
        this.data = data;
        return this;
    }


    @Override
    public int[] transformaRGB(int[] valorPixel, int x, int y, Imagem imagem) {
        final int xOffset = -(data[0].length / 2);
        final int yOffset = -(data[0][0].length / 2);

        List<Double> valoresParaConvolucionarVermelho = new ArrayList<>();
        List<Double> valoresParaConvolucionarVerde = new ArrayList<>();
        List<Double> valoresParaConvolucionarAzul = new ArrayList<>();

        IntStream.range(0, getData1().length).forEach(xPos -> {
            IntStream.range(0, getData1()[0].length).forEach(yPos -> {
                Arrays.stream(canaisProcessados).forEach(canalProcessado -> {
                    int[] px = imagem.getPixel(x + xPos + xOffset, y + yPos + yOffset);
                    switch (canalProcessado) {
                        case Vermelho -> {
                            valoresParaConvolucionarVermelho.add(px[Imagem.Vermelho] * getData1()[Imagem.Vermelho][xPos][yPos]);
                        }
                        case Verde -> {
                            valoresParaConvolucionarVerde.add(px[Imagem.Verde] * getData1()[Imagem.Verde][xPos][yPos]);
                        }
                        case Azul -> {
                            valoresParaConvolucionarAzul.add(px[Imagem.Azul] * getData1()[Imagem.Azul][xPos][yPos]);
                        }
                    }
                });
            });
        });

        int retPreFinal[] = {0, 0, 0};
        int retFinal[] = {0, 0, 0};

        retPreFinal[Imagem.Vermelho] = aplicaOperacaoEstatistica(valoresParaConvolucionarVermelho, imagem);
        retPreFinal[Imagem.Verde] = aplicaOperacaoEstatistica(valoresParaConvolucionarVerde, imagem);
        retPreFinal[Imagem.Azul] = aplicaOperacaoEstatistica(valoresParaConvolucionarAzul, imagem);

        retFinal[Imagem.Vermelho] = isProcessaCor(CanalProcessado.Vermelho) ? retPreFinal[Imagem.Vermelho] : valorPixel[Imagem.Vermelho];
        retFinal[Imagem.Verde] = isProcessaCor(CanalProcessado.Verde) ? retPreFinal[Imagem.Verde] : valorPixel[Imagem.Verde];
        retFinal[Imagem.Azul] = isProcessaCor(CanalProcessado.Azul) ? retPreFinal[Imagem.Azul] : valorPixel[Imagem.Azul];

        return retFinal;
    }
}

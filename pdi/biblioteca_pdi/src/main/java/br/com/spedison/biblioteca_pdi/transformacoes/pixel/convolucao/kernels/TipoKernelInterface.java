package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels;

public interface TipoKernelInterface {
    double[][][] getData1();

    default double[][][] getData2() {
        return getData1();
    }

}

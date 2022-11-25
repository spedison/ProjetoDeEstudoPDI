package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels;

public enum TipoKernelPassaBaixaEnum implements TipoKernelInterface {

    //Ref.: http://www.dpi.inpe.br/~carlos/Academicos/Cursos/Pdi/pdi_filtros.htm
    Media(new double[][]{{1. / 9., 1. / 9., 1. / 9.}, {1. / 9., 1. / 9., 1. / 9.}, {1. / 9., 1. / 9., 1. / 9.}}),
    MediaQuartoAoRedor(new double[][]{{1. / 4., 1. / 4., 0}, {1. / 4., 0, 1. / 4.}, {0, 1. / 4., 0}}),
    MediaDecimosCentral(new double[][]{{1. / 10., 1. / 10., 1. / 10.}, {1. / 10., 1. / 5., 1. / 10.}, {1. / 10., 1. / 10., 1. / 10.}});

    public double[][] baseKernel;

    TipoKernelPassaBaixaEnum(double[][] baseKernel) {
        this.baseKernel = baseKernel;
    }

    @Override
    public double[][][] getData1() {
        return new double[][][]{baseKernel, baseKernel, baseKernel};
    }
}
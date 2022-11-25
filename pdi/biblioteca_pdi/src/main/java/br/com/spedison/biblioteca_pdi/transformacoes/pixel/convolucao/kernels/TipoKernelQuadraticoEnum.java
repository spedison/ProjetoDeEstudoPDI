package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels;

public enum TipoKernelQuadraticoEnum implements TipoKernelInterface {
    // Ref: http://www.dpi.inpe.br/~carlos/Academicos/Cursos/Pdi/pdi_filtros.htm
    Roberts(new double[][]{{0., 0., -1.}, {0., 1., 0.}, {0., 0., 0.}}, new double[][]{{-1., 0., 0.}, {0., 1., 0.}, {0., 0., 0.}}),
    Sobel(new double[][]{{1., 0., -1.}, {2., 0., -2.}, {1., 0., -1.}}, new double[][]{{1., 2., 1.}, {0., 0., 0.}, {-1., -2., -1.}});

    public double[][] baseKernel1;
    public double[][] baseKernel2;

    TipoKernelQuadraticoEnum(double[][] baseKernel1, double[][] baseKernel2) {
        this.baseKernel1 = baseKernel1;
        this.baseKernel2 = baseKernel2;
    }

    @Override
    public double[][][] getData1() {
        return new double[][][]{baseKernel1, baseKernel1, baseKernel1};
    }

    @Override
    public double[][][] getData2() {
        return new double[][][]{baseKernel2, baseKernel2, baseKernel2};
    }
}
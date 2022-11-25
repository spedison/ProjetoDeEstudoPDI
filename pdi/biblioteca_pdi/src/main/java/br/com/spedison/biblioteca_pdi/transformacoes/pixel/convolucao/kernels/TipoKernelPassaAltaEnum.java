package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels;

public enum TipoKernelPassaAltaEnum implements TipoKernelInterface {

    //Ref.: http://www.dpi.inpe.br/~carlos/Academicos/Cursos/Pdi/pdi_filtros.htm
    OitoComOitoCentral(new double[][]{{-1., -1., -1.}, {-1., 8, -1.}, {-1., -1., -1.}}),
    QuatroComQuatroCentral(new double[][]{{0, -1., 0}, {-1, 4., -1}, {0, -1., 0}}),
    OitoComQuatroCentral(new double[][]{{1., -2., 1.}, {-2., 4, -2.}, {1., -2., 1.}});

    public double[][] baseKernel;

    TipoKernelPassaAltaEnum(double[][] baseKernel) {
        this.baseKernel = baseKernel;
    }

    @Override
    public double[][][] getData1() {
        return new double[][][]{baseKernel, baseKernel, baseKernel};
    }
}
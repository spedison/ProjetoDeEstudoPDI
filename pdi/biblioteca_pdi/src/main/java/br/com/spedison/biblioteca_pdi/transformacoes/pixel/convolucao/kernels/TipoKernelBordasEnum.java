package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels;

public enum TipoKernelBordasEnum implements TipoKernelInterface {

    //Ref: http://www.dpi.inpe.br/~carlos/Academicos/Cursos/Pdi/pdi_filtros.htm
    DetectaLinhasVerticiais(new double[][]{{-1.0, 2.0, -1.0}, {-1.0, 2.0, -1.0}, {-1.0, 2.0, -1.0}}),
    DetectaLinhasHorizontais(new double[][]{{-1.0, -1.0, -1.0}, {2.0, 2.0, 2.0}, {-1.0, -1.0, -1.0}}),
    DetectaDiagonalPrincipal(new double[][]{{2.0, -1.0, -1.0}, {-1.0, 2.0, -1.0}, {-1.0, -1.0, 2.0}}),
    DetectaDiagonalSecundaria(new double[][]{{-1.0, -1.0, 2.0}, {-1.0, 2.0, -1.0}, {2.0, -1.0, -1.0}});

    public double[][] baseKernel;

    TipoKernelBordasEnum(double[][] baseKernel) {
        this.baseKernel = baseKernel;
    }

    @Override
    public double[][][] getData1() {
        return new double[][][]{baseKernel, baseKernel, baseKernel};
    }
}
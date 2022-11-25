package br.com.spedison.biblioteca_pdi.transformacoes.pixel.convolucao.kernels;

public enum TipoKernelLaplacianoEnum implements TipoKernelInterface {
    //Ref: Pag 106 Figura 3.37 Gonzalez
    // Ref: https://www.facom.ufu.br/~backes/gsi058/Aula06-FiltragemEspacial.pdf
    // Centro Positivo : Remove Bordas Interiores
    // Centro Negativo : Remove Bordas Exteriores
    QuatroPontosCruzPositivo(new double[][]{{0.0, -1, 0, 0.0}, {-1.0, 4.0, -1.0}, {0.0, -1.0, 0.0}}),
    QuatroPontosCruzNegativo(new double[][]{{0.0, 1, 0, 0.0}, {1.0, -4.0, 1.0}, {0.0, 1.0, 0.0}}),
    OitoPontosPositivo(new double[][]{{-1.0, -1.0, -1.0}, {-1.0, 8.0, -1.0}, {-1.0, -1.0, -1.0}}),
    OitoPontosNegativo(new double[][]{{1.0, 1.0, 1.0}, {1.0, -8.0, 1.0}, {1.0, 1.0, 1.0}}),

    //Ref: http://www.dpi.inpe.br/~carlos/Academicos/Cursos/Pdi/pdi_filtros.htm
    DetectaLinhasVerticiais(new double[][]{{-1.0, 2.0, -1.0}, {-1.0, 2.0, -1.0}, {-1.0, 2.0, -1.0}}),
    DetectaLinhasHorizontais(new double[][]{{-1.0, -1.0, -1.0}, {2.0, 2.0, 2.0}, {-1.0, -1.0, -1.0}}),
    DetectaDiagonalPrincipal(new double[][]{{2.0, -1.0, -1.0}, {-1.0, 2.0, -1.0}, {-1.0, -1.0, 2.0}}),
    DetectaDiagonalSecundaria(new double[][]{{-1.0, -1.0, 2.0}, {-1.0, 2.0, -1.0}, {2.0, -1.0, -1.0}});

    public double[][] baseKernel;

    TipoKernelLaplacianoEnum(double[][] baseKernel) {
        this.baseKernel = baseKernel;
    }

    @Override
    public double[][][] getData1() {
        return new double[][][]{baseKernel, baseKernel, baseKernel};
    }
}
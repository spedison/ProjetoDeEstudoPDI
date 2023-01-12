package br.com.spedison.pds.fourier.continuo;

import br.com.spedison.usogeral.sinais.Sinal;
import br.com.spedison.usogeral.sinais.SinalDenteDeSerra;

public interface CaracteristicasSinal {
    double AmplitudeBase = 20.D; // Amplitude base do sinal.

    double frequanciaBase = 1000.; // Quando se definir a frequencia base muda-se aqui. Em Hertz. As Variáveis abaixo não é necessário mudar.
    double tempoBase = 1. / frequanciaBase;

    double tempoBaseAnalise = tempoBase / 2.;

    // Sinal baseado em soma de senos
    //public static Sinal sinal = new SinalSomaSenos(new double[]{AmplitudeBase, AmplitudeBase / 2., AmplitudeBase / 3.}, new double[]{frequanciaBase, frequanciaBase * 5., frequanciaBase * 9.});
    // Sinal baseado em somente 1 Seno.
    //public static SinalSomaSenos sinal = new SinalSomaSenos(new double[]{AmplitudeBase}, new double[]{frequanciaBase});
    // Sinal baseado em uma onda Quadrada
    // static public Sinal sinal = new SinalRetangular(tempoBase, 0.5, 0., AmplitudeBase);
    // Sinal de dente de serra.
    Sinal sinal = new SinalDenteDeSerra(tempoBase, (AmplitudeBase * frequanciaBase), 0.);

}

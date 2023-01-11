package br.com.spedison.pds.continuo;

import br.com.spedison.usogeral.sinais.Sinal;
import br.com.spedison.usogeral.sinais.SinalDenteDeSerra;
import br.com.spedison.usogeral.sinais.SinalRetangular;
import br.com.spedison.usogeral.sinais.SinalSomaSenos;

public interface CaracteristicasSinal {
    static double AmplitudeBase = 20.D; // Amplitude base do sinal.

    static final double frequanciaBase = 900.; // Quando se definir a frequencia base muda-se aqui. Em Hertz. As Variáveis abaixo não é necessário mudar.
    static public final double tempoBase = 1. / frequanciaBase;

    static public final double tempoBaseAnalise = tempoBase / 2.;

    // Sinal baseado em soma de senos
    //public static Sinal sinal = new SinalSomaSenos(new double[]{AmplitudeBase, AmplitudeBase / 2., AmplitudeBase / 3.}, new double[]{frequanciaBase, frequanciaBase * 5., frequanciaBase * 9.});
    // Sinal baseado em somente 1 Seno.
    //public static SinalSomaSenos sinal = new SinalSomaSenos(new double[]{AmplitudeBase}, new double[]{frequanciaBase});
    // Sinal baseado em uma onda Quadrada
    // static public Sinal sinal = new SinalRetangular(tempoBase, 0.5, 0., AmplitudeBase);
    // Sinal de dente de serra.
    static public Sinal sinal = new SinalDenteDeSerra(tempoBase, (AmplitudeBase*frequanciaBase), 0.);

}

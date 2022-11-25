package br.com.spedison.biblioteca_pdi.transformacoes.pixel.funcoes;

public class FuncaoLog extends FuncaoBase {
    double baseLog;


    public FuncaoLog() {
        super();
        baseLog = 10.0;
    }

    public FuncaoLog(double multiplica, double soma, double baseLog) {
        super(multiplica, soma);
        this.baseLog = baseLog;
    }

    public void setBaseLog(double baseLog) {
        this.baseLog = baseLog;
    }

    @Override
    public int transforma(int x, int y, int val, int rgb) {
        double resultado = 0;

        val += 1;
        if (Math.abs(baseLog - 10.0) < .1) {
            resultado = Math.log10(Math.pow(val, multiplica)); // Base 10
        } else if (Math.abs(baseLog - Math.exp(1)) < .1) {
            resultado = Math.log(Math.pow(val, multiplica)); // Base e (ln)
        } else {
            resultado = (Math.log(Math.pow(val, multiplica)) / Math.log(baseLog)); // Qualquer outra base.
        }

        resultado += soma;
        return (int) (resultado + .5);
    }

    @Override
    public String toString() {
        return "Funcão : F(X) = %f * Log%f(X) + %f".
                formatted(
                        multiplica,
                        baseLog,
                        soma
                );
    }

    @Override
    public String getNome() {
        return "Log" + baseLog + "(X)";
    }
}

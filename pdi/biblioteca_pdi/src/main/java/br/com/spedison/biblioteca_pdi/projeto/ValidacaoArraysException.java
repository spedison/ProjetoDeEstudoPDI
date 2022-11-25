package br.com.spedison.biblioteca_pdi.projeto;

public class ValidacaoArraysException extends RuntimeException{

    public ValidacaoArraysException(String message) {
        super(message);
    }

    static public void validar(boolean condicaoParaErro, String mensagem){
        if (condicaoParaErro) {
            throw new ValidacaoArraysException(mensagem);
        }
    }
}

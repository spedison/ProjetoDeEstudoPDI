package br.com.spedison.usogeral_testes.sinais;

import org.junit.Test;

public class TestSignalMath {
    @Test
    public void testSignal(){
        System.out.println("Signal" + Math.signum(10.));
        System.out.println("Signal" + Math.signum(-10.));
    }
}

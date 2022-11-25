package br.com.spedison.biblioteca_pdi.auxiliar;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ByteDataTest {

    @Test
    public void testConvertToHexString() {
        byte converte = 0x77;
        String valor = ByteData.convertToHexString(converte);
        String resultado = "77";
        assertEquals(valor, resultado);
    }

    @Test
    public void testTestConvertToHexString() {
        byte[] converte = {0x77, (byte) 0xEF, (byte) 0xBB, (byte) 0xA1};
        String valor = ByteData.convertToHexString(converte);
        String resultado = "77EFBBA1";
        assertEquals(valor, resultado);
    }

    @Test
    public void testTestConvertToHexString1() {
        byte[] converte = {0x77, (byte) 0xEF, (byte) 0xBB, (byte) 0xA1};
        String valor = ByteData.convertToHexString(converte, "::");
        String resultado = "77::EF::BB::A1";
        assertEquals(valor, resultado);
    }
}
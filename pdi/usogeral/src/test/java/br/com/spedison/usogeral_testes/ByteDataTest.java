package br.com.spedison.usogeral_testes;


import br.com.spedison.usogeral.ByteData;
import org.junit.Assert;
import org.junit.Test;

public class ByteDataTest {

    @Test
    public void testConvertToHexString() {
        byte converte = 0x77;
        String valor = ByteData.convertToHexString(converte);
        String resultado = "77";
        Assert.assertEquals(valor, resultado);
    }

    @Test
    public void testTestConvertToHexString() {
        byte[] converte = {0x77, (byte) 0xEF, (byte) 0xBB, (byte) 0xA1};
        String valor = ByteData.convertToHexString(converte);
        String resultado = "77EFBBA1";
        Assert.assertEquals(valor, resultado);
    }

    @Test
    public void testTestConvertToHexString1() {
        byte[] converte = {0x77, (byte) 0xEF, (byte) 0xBB, (byte) 0xA1};
        String valor = ByteData.convertToHexString(converte, "::");
        String resultado = "77::EF::BB::A1";
        Assert.assertEquals(valor, resultado);
    }
}
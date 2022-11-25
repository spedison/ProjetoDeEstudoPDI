package br.com.spedison.biblioteca_pdi.auxiliar;

import java.util.Arrays;

public class ByteData {

    static public String convertToHexString(byte val) {
        return "%02X".formatted(val);
    }

    static public String convertToHexString(byte[] val) {
        final StringBuffer ret = new StringBuffer();
        for (byte item : val) {
            ret.append("%02X".formatted(item));
        }
        return ret.toString();
    }

}

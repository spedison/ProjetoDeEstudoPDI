package br.com.spedison.usogeral.auxiliar;

public class ByteData {

    static public String convertToHexString(byte val) {
        return "%02X".formatted(val);
    }

    static public String convertToHexString(byte[] val) {
        return convertToHexString(val, "");
    }

    static public String convertToHexString(byte[] val, final String sep) {
        final StringBuilder ret = new StringBuilder();

        for (byte aByte : val) {
            if (ret.length() != 0) {
                ret.append(sep);
            }
            ret.append("%02X".formatted(aByte));
        }
        return ret.toString();
    }

}

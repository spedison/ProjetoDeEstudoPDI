package br.com.spedison.biblioteca_pdi.auxiliar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static String calculaMD5Arquivo(String arquivo) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
            return "";
        }
        try {
            Path path = Paths.get(arquivo);
            byte[] data = Files.readAllBytes(path);
            md.update(data);
            byte[] digest = md.digest();
            return ByteData.convertToHexString(digest);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return "";
        }
    }

    public static String calculaMD5Bytes(byte[] dados) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
            return "";
        }
        md.update(dados);
        byte[] digest = md.digest();
        return ByteData.convertToHexString(digest);
    }
}

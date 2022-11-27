package br.com.spedison.biblioteca_pdi.base;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

public class SistemaArquivoImagens {

    static public final String[] extensaoImagens = new String[]{"jpg", "png", "bmp", "jpeg"};
    private static SistemaArquivoImagens instance = new SistemaArquivoImagens();

    static public SistemaArquivoImagens getInstance() {
        return instance;
    }

    public SistemaArquivoImagens() {
        leConfiguracao();
    }

    String diretorioEntrada;
    String diretorioSaida;

    void leConfiguracao() {

        File arquivoConfig = Paths.get(System.getProperty("user.home"), ".biblioca-biblioteca_pdi").toFile();

        if (arquivoConfig.exists()) {

            Properties props = new Properties();
            FileInputStream file = null;

            try {
                file = new FileInputStream(arquivoConfig);
                props.load(file);
                diretorioEntrada = props.getProperty("diretorio-entrada");
                diretorioSaida = props.getProperty("diretorio-saida");
            } catch (FileNotFoundException e) {
                System.out.println(
                        "Não localizado o arquivo de configuração: %s. Usando Diretórios padrão para carga de imagens.".
                                formatted(arquivoConfig.toString()));
            } catch (IOException e) {
                System.out.println(
                        "Problemas ao carregar o arquivo de configuração: %s. Usando Diretórios padrão para carga de imagens.".
                                formatted(arquivoConfig.toString()));
            }

        } else {
            // OS Português
            File dirImagem = Paths.get(System.getProperty("user.home"), "Imagens").toFile();

            // OS Inglês
            if (!dirImagem.exists())
                dirImagem = Paths.get(System.getProperty("user.home"), "Pictures").toFile();

            diretorioEntrada = Paths.get(dirImagem.getAbsolutePath(), "entrada").toString();

            diretorioSaida = Paths.get(dirImagem.getAbsolutePath(), "saida").toString();
        }
    }

    public String getDirBaseImagensSaida() {
        return diretorioSaida;
    }

    public String getDirBaseImagensEntrada() {
        return diretorioEntrada;
    }

    public String getDirBaseImagensSaida(String... nomeArquivo) {
        return Paths.get(diretorioSaida, nomeArquivo).toString();
    }

    public String getDirBaseImagensEntrada(String... nomeArquivo) {
        return Paths.get(diretorioEntrada, nomeArquivo).toString();
    }

    public boolean criaDirEntrada(String... dirs) {
        File arquivo = Paths.get(this.diretorioEntrada, dirs).toFile();
        if (arquivo.exists())
            return true;
        return arquivo.mkdirs();
    }

    public SistemaArquivoImagens criaDirSaida(String... dirs) {
        File arquivo = Paths.get(this.diretorioSaida, dirs).toFile();
        if (!arquivo.exists())
            arquivo.mkdirs();
        return this;
    }

    public boolean removeArquivosSaida(String... dirs) {
        var listaApagar = getListaArquivosImagensSaida(extensaoImagens, dirs);
        final boolean[] ret = {true};
        Arrays.
                stream(listaApagar).
                parallel().
                forEach(f -> {
                    System.out.println("Apagando arquivo : %s".formatted(f.toString()));
                    ret[0] &= f.delete();
                });
        return ret[0];
    }

    public SistemaArquivoImagens criaDirs() {
        criaDirSaida();
        criaDirEntrada();
        return this;
    }

    public File[] getListaArquivosImagensEntrada(String[] ext, String... dirs) {
        return listaArquivosDir(ext, getDirBaseImagensEntrada(dirs));
    }

    public File[] getListaArquivosImagensSaida(String[] ext, String... dirs) {
        return listaArquivosDir(ext, getDirBaseImagensSaida(dirs));
    }

    public File[] listaArquivosDir(String[] ext, String dirListagem) {
        File dir = new File(dirListagem);
        var ret = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (ext == null || ext.length == 0) return true;
                final boolean[] ret = {false};
                Arrays.
                        stream(ext).
                        filter(val -> ret[0] == false).
                        forEach(extVal -> ret[0] |= name.toLowerCase().endsWith(extVal));
                return ret[0];
            }
        });
        return ret;
    }


}

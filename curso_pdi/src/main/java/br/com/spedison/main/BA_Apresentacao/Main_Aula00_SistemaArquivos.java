package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;

import java.util.Arrays;

public class Main_Aula00_SistemaArquivos {
    public static void main(String[] args) {

        // Caso não tenha criado, crie um arquivo no home do usuário chamado: .biblioteca-biblioteca_pdi (sim, o arquivo inicia com ".")
        // Coloque nesse arquivo as variáveis de imagens de entrada e saida (CUIDADO: Esse é um exemplo crie os diretórios de entrada e saida e coloque nesse arquivo)
        //
        //  diretorio-entrada=/Imagens/entrada
        //  diretorio-saida=/Imagens/saida
        // Ou no Windows
        //  diretorio-entrada=C:\Users\Imagens\entrada
        //  diretorio-saida=C:\Users\Imagens\saida


        // Conceito de Singleton.
        SistemaArquivoImagens sai = SistemaArquivoImagens.getInstance();

        // Impressão, o que está no Arquivo e o que aparece aqui
        System.out.println("O Diretório de Entrada é : " + sai.getDirBaseImagensEntrada());
        System.out.println("O arquivo cafe1.jpg do diretório de entrada está em: " + sai.getDirBaseImagensEntrada("cafe1.jpg"));
        System.out.println("Onde está o arquivo de saída: " + sai.getDirBaseImagensSaida());

        var str = "Como ficaria o caminho: /dir_imagem_entrada/dir1/dir2 ou no Windows C:\\dir_imagem_entrada\\dir1\\dir2 \n:::Resultado::: \n%s\n:::";
        System.out.println(str.formatted(sai.getDirBaseImagensSaida("dir1", "dir2")));

        System.out.println("Criando Diretórios de entrada e saida");
        System.out.println(sai.criaDirSaida("dir-saida-nivel-1", "dir-saida-nivel-2"));
        System.out.println(sai.criaDirEntrada("dir-entrada-nivel-1", "dir-entrada-nivel-2"));

        System.out.println("Listando todas as imagens de um diretório");
        var arquivos = sai.getListaArquivosImagensEntrada(SistemaArquivoImagens.extensaoImagens, "grid", "foto1");

        Arrays.stream(arquivos).
                map(f -> f.toString()).
                map(s -> "Arquivo : %s".formatted(s)).
                forEach(System.out::println);

        System.out.println("Fim da listagem.");
    }
}
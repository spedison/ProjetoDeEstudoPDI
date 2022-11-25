package br.com.spedison.main.BA_Apresentacao;

import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi.base.MatrizIntImagem;
import br.com.spedison.biblioteca_pdi.base.SistemaArquivoImagens;

public class Main_Aula02_Representacao {
    public static void main(String[] args) {
        SistemaArquivoImagens cp = SistemaArquivoImagens.getInstance();

        var a = new Imagem(120, 50);
        a.escreveTexto("Texto de Teste", 15, 15);
        a.salva(cp.getDirBaseImagensSaida("Aula1-Sem-Cafe.jpg"));

        var imprime = new MatrizIntImagem(a);
        imprime.imprimeMatrix(Imagem.Vermelho);
    }
}

package br.com.spedison.AA_estrutura;


public class AA_Main_Compilacao {

    public static void main(String[] args) {
        System.out.println("Alô Java...");
    }

    /******
     O que é uma linguagem de programação: Uma forma de comunicação entre o Homem e a Máquina.
     Linguagem : Forma de comunicação que tem uma estrutura, gramática e semântica. Nos leva a pensar.
     Programa : Série de passos que apresentados uma entrada se obtem uma saída desejada.
        Exemplo : Entrada N1 N2, saida é a soma de N1 com N2. Isso é um programa.

     Interpretação : Leio linha a linha de um programa e o executo. Lento, Portável. (Exemplo Scrips VBA, Basic, Navegador Web com JavaScript)
     Compilado     : Compilo o programa, gero um executável para o µP e SO. Rápido, Não Portável. (Exemplo compilação do Google Chrome, Office)

     Java   -> Linguagem de computador, Plataforma de Execução e Compilação.
     -----
     Origem :  Sun Microsystems (Atualmente comprada pela Oracle) - 1991
     Projeto : Green Project
     Nome preliminar: Oak
     Criador : Patrick Naughton, Mike Sheridan, e James Gosling
     Objetivo : Ter uma linguagem de programação que seria executada em qualquer plataforma de computação, inclusice eletrodomésticos.
     (Relógio, Celular, Geladeira, Web, PC Doméstico...)
     Nome preliminar : linguagem de programação de Oak
     É um software de uso livre e não precisa pagar direitos de uso para as versões atuais :::
     Curiosidade ::: O Nome Java era o nome do Café (origem) que o programadores da Equipe admiravam, daí a relação do Java com Café.

     Plaraforma Java:: Compilador JDK e executor da JVM que chamamos JRE.
        https://openjdk.org/install/ , https://jdk.java.net/

     IDE:: Ferramenta integrada para desenvolvimento de Programas.
        https://netbeans.apache.org/download/index.html
        https://www.eclipse.org/downloads/
        https://www.jetbrains.com/pt-br/idea/download/

     Ajusta o JAVA_HOME no Ambiente.

     Fazendo na mão

 Processo
     "COMPILAÇÃO" ::: (Txt) -> (Tira Comentárias, Quebra, Encaixa) -> (Gera Classe, Não é executado na máquina, é executado em uma JVM. Esse .class é chamado de ByteCode.)
     "EXECUÇÃO"   ::: java <- (.jar ou .class) -> Inicia a Execuçao até o programa terminar.
     Máquina (ARM/ARM64/m68k/X86/x86-64) -> SO -> JAVA -> BYTECODE.

     No Linux ou MAC
     -----------------
     $JAVA_HOME/bin/javac br/com/spedison/main/AA_PreJava/AA_estrutura/AA_Main_Compilacao.java
     $JAVA_HOME/bin/java br.com.spedison.main.AA_PreJava.AA_estrutura.AA_Main_Compilacao

     No Windows
     -----------
     %JAVA_HOME%/bin/javac br/com/spedison/main/AA_PreJava/AA_estrutura/AA_Main_Compilacao.java
     %JAVA_HOME%/bin/java br.com.spedison.main.AA_PreJava.AA_estrutura.AA_Main_Compilacao
     */

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entrada_Saida;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import trabalho_pesquisa_operacional
        .Operacoes_Matriciais.TAD_Operacoes_Matriciais;

/**
 *
 * @author Vitor
 */
public class Dados_In {

    private String tipo;
    private int num_var;
    private int num_restricao;
    private String[][] matriz_coef;
    private String[] custos;
    private String[] recursos;

    /**
     * Construtor da classe com parâmetros;
     *
     * @param tipo: Minimização ou Maximização;
     * @param num_var: número de váriaveis;
     * @param num_restricao: número de restrições;
     * @param matriz_coef: matriz de coeficientes;
     * @param custos: vetor de custos;
     * @param recursos: vetor de recursos
     */
    public Dados_In(String tipo, int num_var, int num_restricao, 
            String[][] matriz_coef, String[] custos, String[] recursos) {
        this.tipo = tipo;
        this.num_var = num_var;
        this.num_restricao = num_restricao;
        this.matriz_coef = matriz_coef;
        this.custos = custos;
        this.recursos = recursos;
    }

    public Dados_In() {
        this.matriz_coef = new String[num_restricao][num_var];
        this.custos = new String[num_var];
        this.recursos = new String[num_restricao];
    }

    /*GET & SETTER'S==========================================================*/
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNum_var() {
        return num_var;
    }

    public void setNum_var(int num_var) {
        this.num_var = num_var;
    }

    public int getNum_restricao() {
        return num_restricao;
    }

    public void setNum_restricao(int num_restricao) {
        this.num_restricao = num_restricao;
    }

    public String[][] getMatriz_coef() {
        return matriz_coef;
    }

    public void setMatriz_coef(String[][] matriz_coef) {
        this.matriz_coef = matriz_coef;
    }

    public String[] getCustos() {
        return custos;
    }

    public void setCustos(String[] custos) {
        this.custos = custos;
    }

    public String[] getRecursos() {
        return recursos;
    }

    public void setRecursos(String[] recursos) {
        this.recursos = recursos;
    }
    
    //==========================================================================
    
    /**
     * Esta função realizará a leitura do arquivo
     *
     * @param Caminho: Caminho do arquivo a ser lido
     * @return os Dados de entrada(Dados_In) que conterá os dados necessários
     * para realizar o simplex
     */
    public Dados_In leitura_arquivo(File Caminho) {
        Dados_In dados = new Dados_In();

        TAD_Operacoes_Matriciais tad = new TAD_Operacoes_Matriciais();

        try {
            try (BufferedReader meu_buffer = new BufferedReader(new FileReader(Caminho))) {
                String linha;
                try {
                    //enquanto eu conseguir ler o arquivo faço algo
                    while (meu_buffer.ready()) {
                        
                        //lendo a linha
                        linha = meu_buffer.readLine();
                        
                        if (linha.contains("tipo")) {
                            //se a linha contém a chave que define o tipo
                            this.tipo = linha
                                    .substring(linha.indexOf("<tipo>") + 6, linha.indexOf("</tipo>"));
                        } else if (linha.contains("<num_var>")) {
                            //se a linha contém a chave que define o numero de váriaveis
                            String aux = linha
                                    .substring(linha.indexOf("<num_var>") + 9, linha.indexOf("</num_var>"));
                            this.num_var = Integer.parseInt(aux);
                        } else if (linha.contains("num_restricao")) {
                            //se a linha contém a chave que define o número de restrições
                            String aux = linha
                                    .substring(linha.indexOf("<num_restricao>") + 15, linha.indexOf("</num_restricao>"));
                            this.num_restricao = Integer.parseInt(aux);
                        } else if (linha.contains("<A>")) {
                            
                            //indice da linha para inserir o elemento certo na matriz A
                            int Indice_linha = 0;
                            
                            String[][] matriz_auxiliar = new String[this.num_restricao][this.num_var];
                            linha = meu_buffer.readLine();
                            
                            do {
                                String aux = linha
                                        .substring(linha.indexOf("<linha>") + 7, linha.indexOf("</linha>"));
                                StringTokenizer meu_tokenizer = new StringTokenizer(aux, ";");
                                
                                int Indice_coluna = 0;
                                while (meu_tokenizer.hasMoreTokens()) {
                                    String auxiliar = meu_tokenizer.nextToken();
                                    matriz_auxiliar[Indice_linha][Indice_coluna] = (auxiliar);
                                    Indice_coluna++;
                                }
                                Indice_linha++;
                                linha = meu_buffer.readLine();
                            } while (!linha.contains("</A>"));
                            this.matriz_coef = matriz_auxiliar;
                        } else if (linha.contains("<B>")) {
                            
                            //indice da coluna para inserir o elemento certo na matriz
                            int Indice_coluna = 0;
                            
                            String[] vetor_auxiliar = new String[this.num_restricao];
                            
                            linha = meu_buffer.readLine();
                            
                            String aux = linha
                                    .substring(linha.indexOf("<linha>") + 7, linha.indexOf("</linha>"));
                            
                            StringTokenizer meu_tokenizer = new StringTokenizer(aux, ";");
                            while (meu_tokenizer.hasMoreElements()) {
                                String auxiliar = meu_tokenizer.nextToken();
                                vetor_auxiliar[Indice_coluna] = (auxiliar);
                                Indice_coluna++;
                            }
                            this.recursos = vetor_auxiliar;
                        } else if (linha.contains("<C>")) {
                            
                            //indice da coluna para inserir o elemento certo na matriz
                            int Indice_coluna = 0;
                            
                            String[] vetor_auxiliar = new String[this.num_var];
                            
                            linha = meu_buffer.readLine();
                            
                            String aux = linha
                                    .substring(linha.indexOf("<linha>") + 7, linha.indexOf("</linha>"));
                            
                            StringTokenizer meu_tokenizer = new StringTokenizer(aux, ";");
                            while (meu_tokenizer.hasMoreElements()) {
                                String auxiliar = meu_tokenizer.nextToken();
                                vetor_auxiliar[Indice_coluna] = (auxiliar);
                                Indice_coluna++;
                            }
                            this.custos = vetor_auxiliar;
                        }
                    }
                } catch (IOException ex) {
                    Logger
                        .getLogger(Dados_In.class.getName()).log(Level.SEVERE, null, ex);
                }
            }        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o Caminho! "
                    + "Verifique o caminho", "Erro", JOptionPane.ERROR_MESSAGE);
            Logger
                .getLogger(Dados_In.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger
                .getLogger(Dados_In.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dados;
    }

}

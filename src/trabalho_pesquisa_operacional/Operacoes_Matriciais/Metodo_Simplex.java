/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_pesquisa_operacional.Operacoes_Matriciais;

import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Vitor
 */
//import trabalho_pesquisa_operacional.Operacoes_Matriciais.TAD_Operacoes_Matriciais;
public class Metodo_Simplex {

    public static String userHomedir = System
            .getProperty("user.home") + File.separator + "Desktop" + File.separator + "novo.txt";

    /**
     * Construtor da classe
     */
    public Metodo_Simplex() {
    }

    /**
     * Esta função calcula a otimização do modelo utilizando o método simplex
     *
     * @param A: matriz de coeficientes, são os coeficientes das restrições;
     * @param b: Vetor de recursos;
     * @param c: Vetor de custos;
     * @param IndicesBase: Índices das váriaveis que estão na Base no momento;
     * @param IndicesNaoBase: Índices das váriaveis que não estão na Base no
     * momento;
     * @param m: Indica o número de restrições;
     * @param n: Indica o número de váriaveis, incluindo as que são colocadas
     * depois para colocar o modelo na forma padrão;
     * @param caminho: local onde será salvo o arquivo txt;
     *
     * @return retorna o vetor com o resultado de cada váriável do modelo depois
     * de otimizado
     * @throws java.io.IOException
     */
    public static double[] Calcula_Simplex(double[][] A, double[] b, double[] c,
            int[] IndicesBase, int[] IndicesNaoBase,
            int m, int n, String caminho) throws IOException {
        
        //Declarando BufferedWriter que será usado para escrever no arquivo
        BufferedWriter escritor;

        //Instanciando o BufferedWriter(escritor) e um FileWriter
        /*O FileWriter escreve diretamente no arquivo. 
        O BufferedWriter, assim como vários outros writers do Java, usam o padrão decorator. 
        Isso permite combinar writers diferentes, para os mais diversos tipos de situação.
        
        O BufferedWriter faz com que todo comando de escrita vá para um buffer e
        , só de vez enquando, seja enviado de uma só vez para o 
        Writer que for passado para ele no construtor. 
        Isso otimiza o processo de leitura e escrita em dispositivos lentos.
        */
        escritor = new BufferedWriter(new FileWriter(caminho));

        //linhas a ser lida, mesmo não sendo necessário, "limpei" a String 
        //para evitar qualquer possível erro 
        String linha = "";
        int iteracao = 0;

        boolean Terminou = false;

        //Tad de operações
        TAD_Operacoes_Matriciais tad = new TAD_Operacoes_Matriciais();

        //Esta matriz vai receber os elementos da Base
        double[][] Matriz_basica;
        //Esta matriz vai receber a inversa da Base
        double[][] InversaBase=null;

        //Soluçao Básica Factível, é o produto da inversa da Base por b
        double[] x = new double[n];
        //solucao final
        double[] solucao = new double[n];
        //laço de repetição do método
        do {
            linha = "Iteração " + iteracao + "\n";
            escritor.append(linha);
            linha = "";
            //instanciando o espaço para a matriz básica
            Matriz_basica = new double[m][m];

//Inicio Passo 1: Calculando SBF inicial============================
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < IndicesBase.length; j++) {
                    for (int k = 0; k < A[0].length; k++) {
                        if (k == IndicesBase[j]) {
                            Matriz_basica[i][j] = A[i][k];
                        }
                    }
                }
            }

            //Apenas para Debug, exibe a base
            linha = "Base: \n";
            escritor.append(linha);
            linha = "";
            for (double[] Matriz_basica1 : Matriz_basica) {
                for (int j = 0; j < Matriz_basica[0].length; j++) {
                    linha = linha + "[" + Matriz_basica1[j] + "]";
                }
                linha = linha + "\n";
                escritor.append(linha);
                linha = "";
            }

            try {
                InversaBase = tad.calculaInversa(Matriz_basica);                
            } catch (Exception e) {
                JOptionPane
                        .showMessageDialog(null, "Não existe solução, "
                                + "pois existe uma matriz que não "
                                + "possui determinante", "Não Existe Solução", JOptionPane.ERROR_MESSAGE);
                escritor.close();
                throw new RuntimeException("Não existe solução");
            }
            

            linha = "Inversa: \n";
            escritor.append(linha);
            linha = "";
            for (double[] InversaBase1 : InversaBase) {
                for (int j = 0; j < InversaBase[0].length; j++) {
                    linha = linha + "[" + InversaBase1[j] + "]";
                }
                linha = linha + "\n";
                escritor.append(linha);
                linha = "";
            }
            //Calcula a SBF inicial pelo produto da inversa de B com b
            x = tad.produto_matriz_vetor(InversaBase, b);

            linha = "X: \n";
            escritor.append(linha);
            linha = "";

            for (int i = 0; i < x.length; i++) {
                linha = linha + "[" + x[i] + "]";
            }
            linha = linha + "\n";
            escritor.append(linha);
            linha = "";

            double objetivo = 0;
            for (int i = 0; i < m; i++) {
                objetivo = objetivo + c[IndicesBase[i]] * x[i];
            }
            linha = "Objetivo: " + objetivo + "\n";
            escritor.append(linha);
            linha = "";
//Fim Passo 1=======================================================

//Passo 2:Calculando os custos reduzidos dos indices nao basicos
            //instanciando o vetor custoBase
            double[] custoBase = new double[m];

            for (int i = 0; i < m; i++) {
                custoBase[i] = c[IndicesBase[i]];
            }

            linha = "Custo_Base: \n";
            escritor.append(linha);
            linha = "";
            for (int i = 0; i < custoBase.length; i++) {
                linha = linha + "[" + custoBase[i] + "]";
            }
            linha = linha + "\n";
            escritor.append(linha);
            linha = "";

            int jota_escolhido = -1;
            double CustoEscolhido = 999999999;

            for (int j = 0; j < IndicesNaoBase.length; j++) {

                //j-ésima coluna de A
                double[] A_j = new double[m];

                for (int i = 0; i < m; i++) {
                    A_j[i] = A[i][IndicesNaoBase[j]];
                }

                //printando A_j 
                System.out.println("[" + j + "]");
                tad.printa_vetor(A_j);

                double[] direcao = new double[m];
                direcao = tad.produto_matriz_vetor(InversaBase, A_j);

                linha = j + "-ésima direcao(sinal trocado)";
                escritor.append(linha);
                linha = "";
                for (int i = 0; i < direcao.length; i++) {
                    linha = linha + "[" + direcao[i] + "]";
                }
                linha = linha + "\n";
                escritor.append(linha);
                linha = "";

                double Custo = c[IndicesNaoBase[j]] - tad.produto_escalar(custoBase, direcao);

                linha = "Custo Reduzido: " + Custo + "\n";
                escritor.append(linha);
                linha = "";
                //Guarda um indice de direcao basica factivel com custo reduzido negativo, se houver
                if (Custo < 0) {
                    if (Custo < CustoEscolhido) {
                        jota_escolhido = IndicesNaoBase[j];
                        CustoEscolhido = Custo;
                    }
                }
            }

            if (jota_escolhido == -1) {

                double ValObjetivo = 0;

                for (int i = 0; i < m; i++) {
                    ValObjetivo = ValObjetivo + custoBase[i] * x[i];
                }

                linha = "Objetivo=" + ValObjetivo + "\n";
                escritor.append(linha);
                linha = "";

                for (int i = 0; i < n; i++) {
                    solucao[i] = 0;
                }

                for (int i = 0; i < m; i++) {
                    solucao[IndicesBase[i]] = x[i];
                }
                
                Terminou = true;
                break;
            }
//Fim Passo 2=======================================================

//Passo 3: Computa vetor u
            double[] u = new double[m];

            //aux é a coluna A_jotaescolhido
            double[] aux = new double[m];
            for (int i = 0; i < m; i++) {
                aux[i] = A[i][jota_escolhido];
            }

            u = tad.produto_matriz_vetor(InversaBase, aux);

            linha = "Vetor u: \n";
            escritor.append(linha);
            linha = "";
            for (int i = 0; i < u.length; i++) {
                linha = linha + "[" + u[i] + "]";
            }
            linha = linha + "\n";
            escritor.append(linha);
            linha = "";

            boolean existe_positivo = false;

            for (int i = 0; i < m; i++) {
                if (u[i] > 0) {
                    existe_positivo = true;
                }
            }

            if (!existe_positivo) {
                System.out.println("Custo Ótimo = -Infinito \n "
                        + "Retornando um vetor de zeros");
                for (int i = 0; i < m; i++) {
                    solucao[i] = 0;
                }
                Terminou = true;
                break;
            }

            linha = "\tVariavel Entra Base: x[" + jota_escolhido + "]\n";
            escritor.append(linha);
            linha = "";
//Fim Passo 3=======================================================

//Passo 4: Determina o valor de Theta
            double theta = 999999999;
            int IndiceL = -1;
            double Razao;

            for (int i = 0; i < m; i++) {
                if (u[i] > 0) {
                    Razao = x[i] / u[i];
                    if (Razao < theta) {
                        theta = Razao;
                        IndiceL = IndicesBase[i];
                    }
                }
            }
            linha = "\tVariavel  Sai  Base: x[" + IndiceL + "], Theta = " + theta + '\n';
            escritor.append(linha);
            linha = "";
//Fim passo 4=======================================================

//Passo 5: Atualiza variável básica e não-básica
            for (int i = 0; i < m; i++) {
                if (IndicesBase[i] == IndiceL) {
                    x[i] = theta;
                    IndicesBase[i] = jota_escolhido;
                }
            }

            for (int i = 0; i < (n - m); i++) {
                if (IndicesNaoBase[i] == jota_escolhido) {
                    IndicesNaoBase[i] = IndiceL;
                }
            }
//Fim Passo 5=======================================================
            iteracao++;
        } while (!Terminou);

        linha = "Solucao: \n";
        escritor.append(linha);
        linha = "";
        for (int i = 0; i < solucao.length; i++) {
            linha = linha + "[" + solucao[i] + "]";
        }
        linha = linha + "\n";
        escritor.append(linha);
        linha = "";
        escritor.close();
        return solucao;
    }
}

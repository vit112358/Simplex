/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_pesquisa_operacional.Operacoes_Matriciais;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Vitor
 */
public class TAD_Operacoes_Matriciais {

    /*Esta operação define a multiplicação de uma matriz por um escalar
    @parametros:
    matriz: matriz que vai ser multiplicada;
    escalar: valor que eu vou multiplicar a matriz
    num_linhas: número de linhas da matriz original
    num_colunas: número de colun as da matriz original
     */
    public double[][] multiplica_escalar(double[][] matriz, double escalar, int num_linhas, int num_colunas) {
        double[][] matriz_resultado = new double[num_linhas][num_colunas];
        for (int i = 0; i < num_linhas; i++) {
            for (int j = 0; j < num_colunas; j++) {
                matriz_resultado[i][j] = matriz[i][j] * escalar;
            }
        }
        return matriz_resultado;
    }

    /*ESTA FUNÇÃO DEFINE O PRODUTO VETORIAL entre vetores linha e vetores coluna
    @parametros:
    vet1: primeiro vetor a ser multiplicado
    vet2: segundo vetor a ser multiplicado
     */
    public double produto_escalar(double vet1[], double vet2[]) {
        double resultado;
        resultado = 0;
        if (vet1.length == vet2.length) {
            for (int i = 0; i < vet1.length; i++) {
                resultado = resultado + (vet1[i] * vet2[i]);
            }
            return resultado;
        } else {
            throw new RuntimeException("Não foi possível executar o "
                    + "produto escalar entre os vetores.\n "
                    + "Verifique as Dimensões de tal");
        }
    }

    /*Esta função calcula o produto de matrizes
    @parametros:
    matriz_a:primeira matriz a ser multiplicada
    matriz_b:segunda matriz a ser multiplicada
     */
    public double[][] produto_matricial(double[][] matriz_a, double[][] matriz_b) {

        //verificando dimensões
        if (matriz_a[0].length != matriz_b.length) {
            throw new RuntimeException("Dimensões inconsistentes. "
                    + "Impossível multiplicar as matrizes");
        } else {
            //instanciando matriz resultado
            double[][] resultado = new double[matriz_a.length][matriz_b[0].length];

            for (int i = 0; i < matriz_a.length; i++) {
                for (int j = 0; j < matriz_b[0].length; j++) {
                    double soma = 0;
                    for (int k = 0; k < matriz_a[0].length; k++) {
                        double multiplicacao = matriz_a[i][k] * matriz_b[k][j];
                        soma = soma + multiplicacao;
                    }
                    resultado[i][j] = soma;
                }
            }
            return resultado;
        }
    }

    /**
     * Esta Função calcula o produto entre uma matriz e um vetor
     *
     * @param matriz: matriz que vai ser multiplicada;
     * @param vetor: vetor que vai ser multiplicado pela matriz;
     *
     * @return retorna um vetor resultado da matriz pelo vetor
     */
    public double[] produto_matriz_vetor(double[][] matriz, double[] vetor) {

        if (matriz[0].length != vetor.length) {
            throw new RuntimeException("Dimensões inconsistentes. "
                    + "Impossível multiplicar as matrizes");
        } else {
            double[] resultado = new double[matriz.length];

            for (int i = 0; i < matriz.length; i++) {
                double soma = 0;
                for (int j = 0; j < matriz[0].length; j++) {
                    double multiplicacao = matriz[i][j] * vetor[j];
                    soma = soma + multiplicacao;
                }
                resultado[i] = soma;
            }
            return resultado;
        }
    }

    /*Esta função calcula a transposta de uma matriz dada
    @parametros:
    matriz: matriz a ser transposta;
     */
    public double[][] transpor_matriz(double[][] matriz) {
        double[][] transposta = new double[matriz[0].length][matriz.length];

        for (int i = 0; i < matriz.length; i++) {//conta as linhas
            for (int j = 0; j < matriz[i].length; j++) {//conta as colunas
                if (i > j || i < j) {
                    transposta[i][j] = matriz[j][i];
                }
                if (i == j) {
                    transposta[i][j] = matriz[i][j];
                }
            }
        }

        return transposta;
    }

    /*Esta função calcula o determinante da matriz dada
    @parametros:
    matriz: matriz a ser usada;
     */
    public double calcula_determinante(double[][] matriz) {
        double determinante;
        if (matriz.length == matriz[0].length) {
            if (matriz.length == 2) {//se diemnsão igual à 2 retorna o calculo feito na mão
                determinante = (matriz[0][0] * matriz[1][1]) - (matriz[1][0] * matriz[0][1]);
                return determinante;
            } else {
                double soma = 0;
                for (int i = 0; i < matriz.length; i++) {
                    double[][] new_matriz = new double[matriz.length - 1][matriz.length - 1];
                    for (int j = 0; j < matriz.length; j++) {
                        if (j != i) {
                            for (int k = 1; k < matriz.length; k++) {
                                int indice = -1;
                                if (j < i) {
                                    indice = j;
                                } else if (j > i) {
                                    indice = j - 1;
                                }
                                new_matriz[indice][k - 1] = matriz[j][k];
                            }
                        }
                    }
                    if (i % 2 == 0) {
                        soma += matriz[i][0] * calcula_determinante(new_matriz);
                    } else {
                        soma -= matriz[i][0] * calcula_determinante(new_matriz);
                    }
                }
                return soma;
            }
        } else {
            throw new RuntimeException("Erro matriz não quadrada!!");
        }
    }

    /*Esta função calcula a inversa da matriz dada
    @parametros:
    matriz: matriz a ser invertida;
     */
    //=====Inicio dos métodos auxiliares para calcular a Inversa================
    /*Esta função vai calcular a Submatriz de dada matriz 
    @parametros: 
    matriz: a matriz que vai ser cortada
    i:coluna que vai ser retirada
    j:linha que vai ser retirada
     */
    public double[][] SubMatriz(int i, int j, double[][] matriz) {
        double[][] SubMatriz = new double[matriz.length - 1][matriz.length - 1];

        //indexadores da Submatriz;
        int aux1 = 0;
        int aux2;

        for (int k = 0; k < matriz.length; k++) {
            if (k != j) {
                aux2 = 0;
                for (int l = 0; l < matriz.length; l++) {
                    if (l != i) {
                        SubMatriz[aux1][aux2] = matriz[k][l];
                        aux2++;
                    }
                }
                aux1++;
            }
        }
        return SubMatriz;
    }

    /*Esta função vai calcular a matriz adjunta de dada matriz
    @parametros: matriz: a matriz que vai ser calculada a adjunta
     */
    public double[][] calculaMatrizAdjunta(double[][] matriz) {
        double[][] tempAdjunta = new double[matriz.length][matriz.length];

        for (int i = 0; i < tempAdjunta.length; i++) {
            for (int j = 0; j < tempAdjunta.length; j++) {
                double[][] temp = this.SubMatriz(i, j, matriz);
                double elementoAdjunto = Math.pow(-1, i + j) * this.calcula_determinante(temp);
                tempAdjunta[i][j] = elementoAdjunto;
            }
        }
        return tempAdjunta;
    }

    //====Fim dos métodos auxiliares============================================
    /*Esta função calcula a inversa de dada matriz pelo método dos cofatores
    @parametros: matriz a ser calculada a inversa
     */
    public double[][] calculaInversa(double[][] matriz) {
        BigDecimal numero;
        double resultado = calcula_determinante(matriz);
        double aux[][] = new double[matriz.length][matriz.length];
        if (resultado != 0 && (matriz[0].length == matriz.length)) {
            double[][] Inversa = new double[matriz.length][matriz.length];
            aux = this.calculaMatrizAdjunta(matriz);
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz.length; j++) {
                    numero = new BigDecimal(((1 / resultado) * aux[i][j])).setScale(6, RoundingMode.HALF_EVEN);
                    //a linha acima arredonda o número
                    Inversa[i][j] = numero.doubleValue();
                }
            }
            return Inversa;
        } else {
            this.printa_matriz(matriz);
            throw new RuntimeException("O determinante de dada Matriz é zero,"
                    + " logo não existe INVERSA, Use outra matriz!!");
        }
    }

    /**
     * Esta função printa uma matriz
     *
     * @param matriz: matriz a ser printada;
     */
    public void printa_matriz(double[][] matriz) {
        for (double[] matriz1 : matriz) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print("|" + matriz1[j] + "|");
            }
            System.out.println("");
        }
    }

    /**
     * Esta função printa dado vetor
     * @param vetor: vetor a ser printado; 
     */
    public void printa_vetor(double[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            System.out.print("[" +vetor[i]+"]");
        }
        System.out.println("");
    }
    
    public boolean contains(int[] vetor, int elemento){
        for (Object vetor1 : vetor) {
            if (vetor1.equals(elemento)) {
                return true;
            }
        }
        return false;
    }
}

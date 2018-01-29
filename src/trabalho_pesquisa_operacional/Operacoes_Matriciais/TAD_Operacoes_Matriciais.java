package trabalho_pesquisa_operacional.Operacoes_Matriciais;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Vitor
 */
public class TAD_Operacoes_Matriciais {

    /**
     * Esta operação define a multiplicação de uma matriz por um escalar
     * 
     * @param matriz: matriz que vai ser multiplicada;
     * @param escalar: valor que eu vou multiplicar a matriz;
     * @param num_linhas: número de linhas da matriz original
     * @param num_colunas: número de colun as da matriz original
     * @return retorna uma matriz
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

    /**
     * ESTA FUNÇÃO DEFINE O PRODUTO VETORIAL entre vetores linha e vetores coluna
     * 
     * @param vet1: primeiro vetor a ser multiplicado
     * @param vet2: segundo vetor a ser multiplicado
     * @return retorna um número que é defino pelo produto escalar
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

    /**
     * Esta função calcula o produto de matrizes
     * 
     * @param matriz_a: primeira matriz a ser multiplicada
     * @param matriz_b: segunda matriz a ser multiplicada
     * @return retorna a matriz gerada pela multiplicação de A por B 
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

    /**
     * Esta função calcula a transposta de uma matriz dada
     * 
     * @param matriz: matriz a ser transposta
     * @return retorna a matriz transposta
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
    /**
     * Esta função calcula o determinante da matriz dada
     * 
     * @param matriz: matriz a ser usada
     * @return retorna o determinante da matriz, se la for quadrada, 
     * caso contrário, retorna uma exceção
     */
    public double calcula_determinante(double[][] matriz) {
        double determinante;
        //se diemnsão igual à 2 retorna o calculo feito na mão
        if (matriz.length == matriz[0].length) {
            if (matriz.length == 2) {
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
    
    /**
     * Calcula o determinante com o primeiro passo da decomposição LU,
     * que é zerar a parte inferior à diagonal principal, 
     * gerando uma matriz triangular superior, sendo assim basta multiplicar 
     * a diagonal principal para obter o determinante.
     * 
     * @param matriz: matriz que vai ser calculada o determinante
     * @return double: determinante da matriz passada
     */
    public double CalculaDeterminanteLU(double[][] matriz){
        double det=1;
        double multiplicador;
        if(matriz.length == matriz[0].length){
            if(matriz.length == 2){
                det = (matriz[0][0] * matriz[1][1]) - (matriz[1][0] * matriz[0][1]);
                return det;
            }else{
                for (int i = 0; i < matriz.length-1; i++) {
                    for (int j = i+1; j < matriz.length; j++) {
                        multiplicador =  matriz[j][i] / matriz[i][i];
                        for (int k = 0; k < matriz[0].length; k++) {
                            matriz[j][k] = matriz[j][k] - multiplicador * matriz[i][k];
                        }
                    }
                }
                
                for (int i = 0; i < matriz.length; i++) {
                    det=det*matriz[i][i];
                }
                
                return det;
            }
        }else{
            throw new RuntimeException("Erro! Matriz nao quadrada.");
        }
    }
    //=====Inicio dos métodos auxiliares para calcular a Inversa================
    
    /**
     * Esta função vai calcular a Submatriz de dada matriz 
     * 
     * @param i: coluna que vai ser retirada
     * @param j: linha que vai ser retirada
     * @param matriz: a matriz que vai ser cortada
     * @return retorna a nova matriz cortada, ela foi gerada a partir da matriz  
     * dada, retirando a coluna i e a linha j;
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
    
    /**
     * Esta função vai calcular a matriz adjunta de dada matriz
     * 
     * @param matriz: matriz que vai gerar a matriz adjunta
     * @return retorna uma matriz que será ADJUNTA de A
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
    /**
     * Calcula a matriz inversa usando o método de Gauss-Jordan
     * 
     * @param matriz: matriz a ser calculada a Inversa
     * @return matriz inversa de A
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
                    numero = new BigDecimal(((1 / resultado) * aux[i][j]))
                            .setScale(6, RoundingMode.HALF_EVEN);
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
     * 
     *
     * @param matriz_A matriz a ser calculada a Inversa
     * @return matriz inversa de A
     */
    public double[][] calculaInversaGaussJordan(double[][] matriz_A) {
        BigDecimal numero;//arredondamento
        
        //numero de linhas da matriz original
        int num_linhas_A = matriz_A.length;
        //número de colunas da matriz original
        int num_colunas_A = matriz_A[0].length;
        
        /*matriz aumentada A|I, onde I é a identidade*/
        double[][] matriz_A_aumentada = new double[num_linhas_A][num_colunas_A * 2];

        if ((calcula_determinante(matriz_A) != 0) && (matriz_A.length == matriz_A[0].length)) {
            int pos = num_linhas_A;
            /*Da linha 258 a 271 os for's irão construir a matriz aumentada*/
            for (int i = 0; i < num_linhas_A; i++) {//linhas
                for (int j = 0; j < num_colunas_A * 2; j++) {//colunas
                    if (j < num_colunas_A) {
                        matriz_A_aumentada[i][j] = matriz_A[i][j];
                    } else {
                        if (j == pos) {
                            matriz_A_aumentada[i][j] = 1;
                        } else {
                            matriz_A_aumentada[i][j] = 0;
                        }
                    }
                }
                pos++;
            }

            int num_linhas_A_aumentada = matriz_A_aumentada.length;
            int num_colunas_A_aumentada = matriz_A_aumentada[0].length;
            double multiplicador;

            /*Da linha 278 a 285 os for irão zerar a parte inferior à 
            diagonal principal*/
            for (int i = 0; i < num_linhas_A_aumentada - 1; i++) {
                for (int j = i + 1; j < num_linhas_A_aumentada; j++) {
                    multiplicador = matriz_A_aumentada[j][i] / matriz_A_aumentada[i][i];
                    for (int k = 0; k < num_colunas_A_aumentada; k++) {
                        matriz_A_aumentada[j][k] = matriz_A_aumentada[j][k] - multiplicador * matriz_A_aumentada[i][k];
                    }
                }
            }

            /*Da linha 288 à 295 os for's irão zerar a parte superior à 
            diagonal principal*/
            for (int i = num_linhas_A_aumentada - 1; i >= 1; i--) {
                for (int j = i - 1; j >= 0; j--) {
                    multiplicador = matriz_A_aumentada[j][i] / matriz_A_aumentada[i][i];
                    for (int k = 0; k < num_colunas_A_aumentada; k++) {
                        matriz_A_aumentada[j][k] = matriz_A_aumentada[j][k] - multiplicador * matriz_A_aumentada[i][k];
                    }
                }
            }
                
            /*Da linha 298 à 304 os for's irão transformar a diagonal 
            principal em 1*/
            for (int i = 0; i < num_linhas_A_aumentada; i++) {
                //System.out.println(matriz_A_aumentada[i][i]);
                double aux = matriz_A_aumentada[i][i];
                for (int j = 0; j < num_colunas_A_aumentada; j++) {
                    matriz_A_aumentada[i][j] = (matriz_A_aumentada[i][j] / aux);
                }
            }

            double[][] Inversa = new double[num_linhas_A][num_colunas_A];
            for (int i = 0; i < num_linhas_A_aumentada; i++) {
                for (int j = 0; j < num_colunas_A; j++) {
                    numero = new BigDecimal(matriz_A_aumentada[i][j + num_linhas_A])
                            .setScale(6, RoundingMode.HALF_EVEN);
                    Inversa[i][j] = numero.doubleValue();
                }
            }
            /*debug*/
            //System.out.println("Inversa");
            //printa_matriz(Inversa);

            return Inversa;
        } else {
            throw new RuntimeException("O determinante da matriz não existe "
                    + "ou ela não é quadrada, logo não existe INVERSA. "
                    + "Use outra Matriz!!");
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
     *
     * @param vetor: vetor a ser printado;
     */
    public void printa_vetor(double[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            System.out.print("[" + vetor[i] + "]");
        }
        System.out.println("");
    }

    public boolean contains(int[] vetor, int elemento) {
        for (Object vetor1 : vetor) {
            if (vetor1.equals(elemento)) {
                return true;
            }
        }
        return false;
    }
}

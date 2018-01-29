/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_pesquisa_operacional;

import trabalho_pesquisa_operacional.Operacoes_Matriciais.TAD_Operacoes_Matriciais;

/**
 * Classe de Teste dos métodos de Operações Matriciais, a classe a ser 
 * executada será a classe principal
 * 
 * Esta classe pode ser excluída
 * 
 * @author Vitor
 */
public class NewClass {
    
    public static void main(String args[]){
        
        double[][] A ={{1,2,1,7,8},{2,6,1,8,7},{1,1,4,9,15},{1,2,3,4,8},{6,8,4,2,1}};
        
        TAD_Operacoes_Matriciais tad = new TAD_Operacoes_Matriciais();
        
        tad.printa_matriz(A);
        System.out.println("");
        long TI = System.nanoTime();
        double[][] Inversa = tad.calculaInversaGaussJordan(A);
        long TF = System.nanoTime();
        long diff = TF-TI;
        System.out.println("Gauss-Jordan executou em: "+diff);
        tad.printa_matriz(Inversa);
        System.out.println("");
        long TI1 = System.nanoTime();
        Inversa = tad.calculaInversa(A);
        long TF1 = System.nanoTime();
        long diff1= TF1 - TI1;
        System.out.println("Laplace executou em: "+diff1);
        tad.printa_matriz(Inversa);
        System.out.println("");
        double det = tad.CalculaDeterminanteLU(A);
        System.out.println("Determinante: "+det);
    }
    
}

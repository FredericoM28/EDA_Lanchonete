/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model;

/**
 *
 * @author Déleo Cambula
 */
public class EdaLanchonete {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Um exemplo para mostrar o uso de filas com listas ligadas, podemos usar qualquero tipo de dado ou objecto
        //A string é só exemplo
       
        Fila<String> fila = new Fila<>();

        fila.enfileirar("A");
        fila.enfileirar("B");
        fila.enfileirar("C");

        fila.mostrarFila(); // Fila: A B C

        System.out.println("Primeiro da fila: " + fila.frente()); // A

        System.out.println("Removido: " + fila.desenfileirar()); // A
        fila.mostrarFila(); // Fila: B C

        System.out.println("Tamanho: " + fila.tamanho()); // 2
    }
}
    

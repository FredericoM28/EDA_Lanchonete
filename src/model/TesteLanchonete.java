/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
public class TesteLanchonete {

    public static void main(String[] args) {
        //Pizza.criarVenda("Pata", "Toamte", "Lisa", "Branco", 200);
        //Pizza.criarVenda("tuti", "Toamte", "Lisa", "Branco", 200);
        Fila<Pizza> lista= Pizza.lerPizza();
        lista.mostrarFila();
        Pizza novaPizza= new Pizza("Bolonesa", "Toamte", "Lisa", "Branco", 200);
        Pizza.editarPorId(0, novaPizza);
     
        lista.mostrarFila();

    }
}

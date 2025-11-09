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
<<<<<<< HEAD
//
//        //Pizza.criarVenda("Pata", "Toamte", "Lisa", "Branco", 200);
//        Pizza.criarPizza("Bolonesa", "Carne", "Lisa", "Branco", 200);
//        Pizza.criarPizza("Bolonesa", "Carne", "Lisa", "Branco", 200);
//        Pizza.criarPizza("Bolonesa", "Carne", "Lisa", "Branco", 200);
//        Pizza.criarPizza("Bolonesa", "Carne", "Lisa", "Branco", 200);
//        Pizza.criarPizza("Bolonesa", "Carne", "Lisa", "Branco", 200);
        Fila<Pizza> lista = Pizza.lerPizza();
        lista.mostrarFila();
        
        Pizza.deletePizza(5);
        System.out.println("Apgada");
           lista.mostrarFila();
//       Pizza novaPizza = new Pizza("Tuti", "Toamte", "Recheda", "Branco", 200);
//       Pizza.editarPorId(1, novaPizza);
////
//        lista.mostrarFila();

        // Pizza.criarPizza("xyz", "tal", "recheiada", "tal", 1000);
=======


        //Pizza.criarVenda("Pata", "Toamte", "Lisa", "Branco", 200);
       // Pizza.criarPizza("Bolonesa", "Carne", "Lisa", "Branco", 200);
        Fila<Pizza> lista = Pizza.lerPizza();
        lista.mostrarFila();
       Pizza novaPizza = new Pizza("Tuti", "Toamte", "Recheda", "Branco", 200);
       Pizza.editarPorId(1, novaPizza);
>>>>>>> 2b8581bbeee1e61ea4cea342d151f985828d2a2a
    }
}

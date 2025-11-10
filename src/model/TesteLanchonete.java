/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author HP
 */
public class TesteLanchonete {

    public static void main(String[] args) {
        Pizza.criarPizza("Margarida", "Carne", "Lisa", "Tomate", 200);
       
        Fila<Pizza> lista= Pizza.lerPizza();
        lista.mostrarFila();
        
<<<<<<< HEAD
        Pizza.deletePizza(5);
        System.out.println("Apgada");
           lista.mostrarFila();
//       Pizza novaPizza = new Pizza("Tuti", "Toamte", "Recheda", "Branco", 200);
//       Pizza.editarPorId(1, novaPizza);
////
//        lista.mostrarFila();

        // Pizza.criarPizza("xyz", "tal", "recheiada", "tal", 1000);


        //Pizza.criarVenda("Pata", "Toamte", "Lisa", "Branco", 200);
       // Pizza.criarPizza("Bolonesa", "Carne", "Lisa", "Branco", 200);
       
        lista.mostrarFila();
       Pizza novaPizza = new Pizza("Tuti", "Toamte", "Recheda", "Branco", 200);
       
//itemVenda itemVenda = new itemVenda("pizza", 2);
//adicionarItem(filaItens, "pizza", 2);
        Fila<Pizza> fila = Pizza.lerPizza();
        Fila<Object> filaItens =  new Fila();
        while(!fila.estaVazia()){
            Pizza pizza = fila.desenfileirar();
            
           itemVenda.adicionarItem(filaItens, pizza, 0);
        }
        
        Pizza.deletePizza(4);

        
        
        while(!fila.estaVazia()){
            Pizza pizza = fila.desenfileirar();
            System.out.println(pizza.getId());
        }

        
        Pizza.deletePizza(4);
        
               while(!fila.estaVazia()){
            Pizza pizza = fila.desenfileirar();
            System.out.println(pizza.getId());
        }


=======
>>>>>>> f197c9768b6fe2e2c78b23602cebe3d182d83e84
    }
}

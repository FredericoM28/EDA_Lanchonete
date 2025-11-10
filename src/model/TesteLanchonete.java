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


        //Pizza.criarVenda("Pata", "Toamte", "Lisa", "Branco", 200);
       // Pizza.criarPizza("Bolonesa", "Carne", "Lisa", "Branco", 200);
       
        lista.mostrarFila();
       Pizza novaPizza = new Pizza("Tuti", "Toamte", "Recheda", "Branco", 200);
       

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


    }
}

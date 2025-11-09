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
        Fila<Pizza> fila = Pizza.lerPizza();
        Fila<Object> filaItens =  new Fila();
        while(!fila.estaVazia()){
            Pizza pizza = fila.desenfileirar();
            
           itemVenda.adicionarItem(filaItens, pizza);
        }
        
        Pizza.deletePizza(4);
        
        while(!fila.estaVazia()){
            Pizza pizza = fila.desenfileirar();
            System.out.println(pizza.getId());
        }
    }
}

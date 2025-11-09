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

        Fila<Pizza> lista = Pizza.lerPizza();
        
        while(!lista.estaVazia()){
            Pizza pizza = lista.desenfileirar();
            System.out.println(pizza.getId() );
        }    
    }
}

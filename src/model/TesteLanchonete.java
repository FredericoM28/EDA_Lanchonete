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

        Pizza pizza = new Pizza("Pizza Margarida", "Tomate", "Com borda", "Tomate", 500);
        Pizza pizza1 = new Pizza("Pizza Babalaza", "Tomate", "Com borda", "Tomate", 500);
        Pizza.gravarPizza(pizza1);
        Fila<Pizza> listaDepizza = Pizza.lerPizza();
        listaDepizza.mostrarFila();
    }
}

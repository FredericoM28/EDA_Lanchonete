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
        
    }
}

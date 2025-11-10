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
        Pizza.criarPizza("xyz", "xyz", "rec", "x", 1000);//crie uma venda qualquer so para exemplificar
        
        Fila<Pizza> fila = Pizza.lerPizza();//le pizza ou salgadinhi
        
        Produto produto = fila.desenfileirar();//pega uma pizza ou slagadinho atribui como produtp
        
        Fila<ItemVenda> itens = new Fila();//cria uma fila inicial do tipo item venda
        
        itens = ItemVenda.adicionarItem(itens, produto, 2);//adiciona item, e o metodo retorna o item adicionado
        
        while(!itens.estaVazia()){
            ItemVenda item = itens.desenfileirar();
            System.out.println(item.getItem().getNome());
        }
            
        
    }
}

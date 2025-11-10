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

            Pizza novaPizza= Pizza.lerPizzaPorId(3);
//            Fila<Pizza> fila= Pizza.lerPizza();
//            Fila<Object> filaO= new Fila<>();
//            fila.mostrarFila();
            
<<<<<<< HEAD
            itemVenda item = new itemVenda(novaPizza, 10);
            Fila<itemVenda>filaI = new Fila<>();
            filaI.enfileirar(item);
               Venda venda= new Venda(filaI, 1000, LocalDate.now(), 1050, 1);
//               if(Venda.gravarVenda(venda)){
//                   System.out.println("Venda com sucesso");
//               }else{
//                   System.out.println("Falhou");
//               }
               System.out.println(Venda.lerVenda());
=======
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

>>>>>>> 16a92ddb6b35aeca78999f56b74e043254ef8d60

    }
}

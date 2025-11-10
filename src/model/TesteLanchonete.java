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

    }
}

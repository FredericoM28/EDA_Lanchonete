/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Fila;
import model.Pizza;
import view.TelaDeRegistarPizza;

/**
 *
 * @author HP
 */
public class ControlTelaDePizza {

    private final TelaDeRegistarPizza telaPizza;

    public ControlTelaDePizza(TelaDeRegistarPizza view) {
        this.telaPizza = view;
    }

    //Metodo para registar PIzza
    public void registarPizza() {
        String nome = telaPizza.getTfNome().getText();
        String recheio = telaPizza.getTfRecheio().getText();
        String borda = (String) telaPizza.getCbTipoDeBorda().getSelectedItem();
        String molho = telaPizza.getTfMolho().getText();
        Double preco = Double.valueOf(telaPizza.getTfPreco().getText());
        Pizza novaPizza = new Pizza(recheio, borda, molho, preco);
        Pizza.gravarPizza(novaPizza);
    }
    
    //Metodo para listar as vendas
    public void listarPizza(){
        DefaultTableModel model = (DefaultTableModel) telaPizza.getTabelaPizza().getModel();
        model.setRowCount(0);
        Fila<Pizza> listaDePizzas = Pizza.lerPizza();

        if (listaDePizzas != null) {
            for (Pizza pizza : listaDePizzas) {
                if (pizza != null) {
                    

                    if (pizza.getStatus() == true) {
                        model.addRow(new Object[]{
                            pizza.getRecheio(),
                            pizza.getBorda(),
                            pizza.getMolho(),
                            pizza.getPreco(),
                            
                            

                        });
                    }
                }
            }
        }
    
    }

}

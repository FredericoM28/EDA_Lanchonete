/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Fila;
import model.Pizza;
import static model.Pizza.lerPizza;
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
        Pizza novaPizza = new Pizza(nome,recheio, borda, molho, preco);
        if(Pizza.gravarPizza(novaPizza)){
            JOptionPane.showMessageDialog(null, "Pizza Salva com sucesso");
        }else{
            System.out.println("Erro ao registar a Pizza");
        }
    }

    //Metodo para listar as vendas
    public void listarPizza() {
        DefaultTableModel model = (DefaultTableModel) telaPizza.getTabelaPizza().getModel();
        model.setRowCount(0);

        Fila<Pizza> fila = lerPizza();

        while (!fila.estaVazia()) {
            Pizza atual = fila.desenfileirar();

            if (atual != null) {
                model.addRow(new Object[]{
                    atual.getId(),
                    atual.getNomePizza(),
                    atual.getRecheio(),
                    atual.getBorda(),
                    atual.getPreco()
                });

            }

        }

    }

}

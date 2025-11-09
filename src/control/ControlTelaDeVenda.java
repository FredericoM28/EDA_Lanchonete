/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import javax.swing.table.DefaultTableModel;
import model.Fila;
import model.Pizza;
import static model.Pizza.lerPizza;
import model.Salgadinho;
import view.TelaDeVenda;

/**
 *
 * @author HP
 */
public class ControlTelaDeVenda {

    //metodo para listar Venda Salgados
    private final TelaDeVenda telaVenda;

    public ControlTelaDeVenda(TelaDeVenda view) {
        this.telaVenda = view;
    }

    //Metodo para listar Pizzas
    public void listarPizzas() {
        DefaultTableModel model = (DefaultTableModel) telaVenda.getTabelaPizzaVenda().getModel();
        model.setRowCount(0);

        Fila<Pizza> fila = lerPizza();

        while (!fila.estaVazia()) {
            Fila<Pizza> filaOrdenada = Pizza.ordenarPorPreco(fila);
            Pizza atual = filaOrdenada.desenfileirar();

            if (atual != null) {
                model.addRow(new Object[]{
                    atual.getId(),
                    atual.toString()
                });

            }
        }

    }
//Metodo para listarSalgados

    public void listarSalgados() {
        DefaultTableModel model = (DefaultTableModel) telaVenda.getTabelaSalgadosVenda().getModel();
        model.setRowCount(0);

        Fila<Salgadinho> fila = Salgadinho.lerSalgadinho();

        while (!fila.estaVazia()) {
            Fila<Salgadinho> filaOrdenada = Salgadinho.ordenarPorPreco(fila);
            Salgadinho atual = filaOrdenada.desenfileirar();

            if (atual != null) {
                model.addRow(new Object[]{
                    atual.getId(),
                    atual.toString()
                });

            }

        }

    }
    
    //Metodo para listar no carrinho
    

}

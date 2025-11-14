/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import javax.swing.table.DefaultTableModel;
import model.Fila;
import model.Salgadinho;
import model.Venda;
import view.ListarVendas;
import view.TelaDeVenda;

/**
 *
 * @author HP
 */
public class ControlTelaListarVenda {

    private final ListarVendas listarVenda;

    public ControlTelaListarVenda(ListarVendas view) {
        this.listarVenda = view;
    }

    public void listarVendas() {
        DefaultTableModel model = (DefaultTableModel) listarVenda.getTabelaVendas().getModel();
        model.setRowCount(0);

        Fila<Venda> fila = Venda.lerVenda();

        while (!fila.estaVazia()) {
           
            Venda atual = fila.desenfileirar();

            if (atual != null) {
                model.addRow(new Object[]{
                    atual.getIdVenda(),
                    atual.getItemVenda().toString(),
                    atual.getValorTotal(),
                    atual.getValorRecebido(),
                    atual.getDataDeVenda(),
                });

            }

        }
    }

}

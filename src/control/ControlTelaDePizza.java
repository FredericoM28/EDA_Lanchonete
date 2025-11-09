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
        if (Pizza.criarPizza(nome, recheio, borda, molho, preco)) {
            JOptionPane.showMessageDialog(null, "Pizza Salva com sucesso");
        } else {
            System.out.println("Erro ao registar a Pizza");
        }
    }

    //Metodo para listar as vendas
    public void listarPizza() {
        DefaultTableModel model = (DefaultTableModel) telaPizza.getTabelaPizza().getModel();
        model.setRowCount(0);

        Fila<Pizza> fila = lerPizza();

        while (!fila.estaVazia()) {
            Fila<Pizza> filaOrdenada = Pizza.ordenarPorPreco(fila);
            Pizza atual = filaOrdenada.desenfileirar();

            if (atual != null) {
                model.addRow(new Object[]{
                    atual.getId(),
                    atual.getNomePizza(),
                    atual.getRecheio(),
                    atual.getBorda(),
                    atual.getMolho(),
                    atual.getPreco()
                });

            }

        }

    }

    //Metodo para limparCampos
    public void limparCampos() {
        telaPizza.getTfIdPizza().setText("");
        telaPizza.getTfNome().setText("");
        telaPizza.getTfMolho().setText("");
        telaPizza.getTfPreco().setText("");
        telaPizza.getTfRecheio().setText("");

        String[] borda = {"Selecione a Borda", "Lisa", "Recheada"};
        String[] tipoDeBorda = new String[borda.length];
        for (int i = 0; i < tipoDeBorda.length; i++) {
            tipoDeBorda[i] = borda[i];
        }

        telaPizza.getCbTipoDeBorda().setModel(new javax.swing.DefaultComboBoxModel<>(tipoDeBorda));

        telaPizza.getCbTipoDeBorda().setToolTipText("");

    }

    //Metodo para editar a pizza
    public void editarPizza() {
        int id = Integer.parseInt(telaPizza.getTfIdPizza().getText());
        String nome = telaPizza.getTfNome().getText();
        String recheio = telaPizza.getTfRecheio().getText();
        String borda = (String) telaPizza.getCbTipoDeBorda().getSelectedItem();
        String molho = telaPizza.getTfMolho().getText();
        Double preco = Double.valueOf(telaPizza.getTfPreco().getText());
       
        if (Pizza.editarPorId(id, nome, recheio, borda, molho, preco)) {
            JOptionPane.showMessageDialog(null, "Pizza Editada com sucesso");
        } else {
            System.out.println("Erro ao Editar a Pizza");
        }

    }

    //metodo Para deletar a pizza
    public void deletarPizza() {
        int id = Integer.parseInt(telaPizza.getTfIdPizza().getText());
        Pizza.deletePizza(id);
    }

    //Metodo para selecionar a tabela pizza
    public void selecionarTabelaPizza() {
        int linha = telaPizza.getTabelaPizza().getSelectedRow();
        String id = telaPizza.getTabelaPizza().getValueAt(linha, 0).toString();
        String nomeDaPizza = telaPizza.getTabelaPizza().getValueAt(linha, 1).toString();
        String recheio = telaPizza.getTabelaPizza().getValueAt(linha, 2).toString();
        String borda = telaPizza.getTabelaPizza().getValueAt(linha, 3).toString();
        String molho = telaPizza.getTabelaPizza().getValueAt(linha, 4).toString();
        double preco = Double.parseDouble(telaPizza.getTabelaPizza().getValueAt(linha, 5).toString());
        telaPizza.getTfIdPizza().setText(id + "");
        telaPizza.getTfNome().setText(nomeDaPizza + " ");
        telaPizza.getTfRecheio().setText(recheio + " ");
        telaPizza.getCbTipoDeBorda().setSelectedItem(borda);
        telaPizza.getTfMolho().setText(molho + " ");
        telaPizza.getTfPreco().setText(preco + " ");

    }

}

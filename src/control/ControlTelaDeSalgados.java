/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Fila;
import model.Pizza;
import static model.Pizza.lerPizza;
import model.Salgadinho;
import view.TelaDeRegistarPizza;
import view.TelaDeRegistarSalgados;

/**
 *
 * @author HP
 */
public class ControlTelaDeSalgados {
     private final TelaDeRegistarSalgados telaSalgados;

    public ControlTelaDeSalgados(TelaDeRegistarSalgados view) {
        this.telaSalgados = view;
    }

    //Metodo para registar PIzza
    public void registarSalgados() {
        String nome = telaSalgados.getTfNome().getText();
        String recheio = telaSalgados.getTfRecheio().getText();
        String tipoDeSalgado = (String)telaSalgados.getCbTipoDeSalgado().getSelectedItem();
        String massa = (String) telaSalgados.getTfMassa().getText();
        Double preco = Double.valueOf(telaSalgados.getTfPreco().getText());
        
        if (Salgadinho.criarSalgadinho(nome,tipoDeSalgado, massa,recheio,preco)) {
            JOptionPane.showMessageDialog(null, "Salgadinho Salva com sucesso");
        } else {
            System.out.println("Erro ao registar a Salgadinho");
        }
    }

    //Metodo para listar as vendas
    public void listarSalgados() {
        DefaultTableModel model = (DefaultTableModel) telaSalgados.getTabelaSalgados().getModel();
        model.setRowCount(0);

        Fila<Salgadinho> fila = Salgadinho.lerSalgadinho();

        while (!fila.estaVazia()) {
            Fila<Salgadinho> filaOrdenada = Salgadinho.ordenarPorPreco(fila);
            Salgadinho atual = filaOrdenada.desenfileirar();

            if (atual != null) {
                model.addRow(new Object[]{
                    atual.getId(),
                    atual.getNomeSalgado(),
                    atual.getRecheio(),
                    atual.getTipo(),
                    atual.getMassa(),
                    atual.getPreco()
                });

            }

        }

    }
    
    
    //Metodo para limparCampos
    public void limparCampos() {
        telaSalgados.getTdIdSalgado().setText("");
        telaSalgados.getTfNome().setText("");
        telaSalgados.getTfRecheio().setText("");
        telaSalgados.getTfPreco().setText("");
        telaSalgados.getTfRecheio().setText("");

        String[] borda = {"Selecione o Tipo", "Assado", "Frito"};
        String[] tipoDeBorda = new String[borda.length];
        for (int i = 0; i < tipoDeBorda.length; i++) {
            tipoDeBorda[i] = borda[i];
        }

        telaSalgados.get().setModel(new javax.swing.DefaultComboBoxModel<>(tipoDeBorda));

        telaSalgados.getCbTipoDeBorda().setToolTipText("");

    }

    //Metodo para editar a pizza
    public void editarPizza() {
        int id = Integer.parseInt(telaSalgados.getTfIdPizza().getText());
        String nome = telaSalgados.getTfNome().getText();
        String recheio = telaSalgados.getTfRecheio().getText();
        String borda = (String) telaSalgados.getCbTipoDeBorda().getSelectedItem();
        String molho = telaSalgados.getTfMolho().getText();
        Double preco = Double.valueOf(telaSalgados.getTfPreco().getText());
        Pizza novaPizza = new Pizza(nome, recheio, borda, molho, preco);
        if (Pizza.editarPorId(id, novaPizza)) {
            JOptionPane.showMessageDialog(null, "Pizza Editada com sucesso");
        } else {
            System.out.println("Erro ao Editar a Pizza");
        }

    }

    //metodo Para deletar a pizza
    public void deletarPizza() {
        int id = Integer.parseInt(telaSalgados.getTfIdPizza().getText());
        Pizza.deletePizza(id);
    }

    //Metodo para selecionar a tabela pizza
    public void selecionarTabelaPizza() {
        int linha = telaSalgados.getTabelaPizza().getSelectedRow();
        String id = telaSalgados.getTabelaPizza().getValueAt(linha, 0).toString();
        String nomeDaPizza = telaSalgados.getTabelaPizza().getValueAt(linha, 1).toString();
        String recheio = telaSalgados.getTabelaPizza().getValueAt(linha, 2).toString();
        String borda = telaSalgados.getTabelaPizza().getValueAt(linha, 3).toString();
        String molho = telaSalgados.getTabelaPizza().getValueAt(linha, 4).toString();
        double preco = Double.parseDouble(telaSalgados.getTabelaPizza().getValueAt(linha, 5).toString());
        telaSalgados.getTfIdPizza().setText(id + "");
        telaSalgados.getTfNome().setText(nomeDaPizza + " ");
        telaSalgados.getTfRecheio().setText(recheio + " ");
        telaSalgados.getCbTipoDeBorda().setSelectedItem(borda);
        telaSalgados.getTfMolho().setText(molho + " ");
        telaSalgados.getTfPreco().setText(preco + " ");

    }
}

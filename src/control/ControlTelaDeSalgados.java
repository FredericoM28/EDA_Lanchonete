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
        String tipoDeSalgado = (String) telaSalgados.getCbTipoDeSalgado().getSelectedItem();
        String massa = (String) telaSalgados.getTfMassa().getText();
        Double preco = Double.valueOf(telaSalgados.getTfPreco().getText());

        if (Salgadinho.criarSalgadinho(nome, tipoDeSalgado, massa, recheio, preco)) {
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
                    atual.getTipo(),
                    atual.getRecheio(),
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
        String[] salgado = {"Selecione o Tipo", "Assado", "Frito"};
        String[] tipoDeSalgadosa = new String[salgado.length];
        for (int i = 0; i < tipoDeSalgadosa.length; i++) {
            tipoDeSalgadosa[i] = salgado[i];
        }

        telaSalgados.getCbTipoDeSalgado().setModel(new javax.swing.DefaultComboBoxModel<>(tipoDeSalgadosa));

        telaSalgados.getTfMassa().setText("");
        telaSalgados.getTfPreco().setText("");
    }

    //Metodo para editar a pizza
    public void editarPizza() {
        int id = Integer.parseInt(telaSalgados.getTdIdSalgado().getText());
        String nome = telaSalgados.getTfNome().getText();
        String recheio = telaSalgados.getTfRecheio().getText();
        String tipoDeSalgado = (String) telaSalgados.getCbTipoDeSalgado().getSelectedItem();
        String massa = (String) telaSalgados.getTfMassa().getText();
        Double preco = Double.valueOf(telaSalgados.getTfPreco().getText());
        if (Salgadinho.editar(id, nome,tipoDeSalgado, massa, recheio,preco)) {
            JOptionPane.showMessageDialog(null, "Salgadinho Editado com sucesso");
        } else {
            System.out.println("Erro ao editar o Salgadinho");
        }

    }

    //metodo Para deletar a Salgado
    public void deletarSalgado() {
        int id = Integer.parseInt(telaSalgados.getTdIdSalgado().getText());
        Salgadinho.deleteSalagadinho(id);
    }

    //Metodo para selecionar a tabela Salgado
    public void selecionarTabelaPizza() {
        int linha = telaSalgados.getTabelaSalgados().getSelectedRow();
        String id = telaSalgados.getTabelaSalgados().getValueAt(linha, 0).toString();
        String nomeDoSalgado = telaSalgados.getTabelaSalgados().getValueAt(linha, 1).toString();
        String tipoDeSalgado = telaSalgados.getTabelaSalgados().getValueAt(linha, 2).toString();
        String massa = telaSalgados.getTabelaSalgados().getValueAt(linha, 3).toString();
        
        String recheio = telaSalgados.getTabelaSalgados().getValueAt(linha, 4).toString();
        double preco = Double.parseDouble(telaSalgados.getTabelaSalgados().getValueAt(linha, 5).toString());
        telaSalgados.getTdIdSalgado().setText(id + "");
        telaSalgados.getTfNome().setText(nomeDoSalgado + " ");
          telaSalgados.getCbTipoDeSalgado().setSelectedItem(tipoDeSalgado);
        telaSalgados.getTfMassa().setText(massa + " ");
        telaSalgados.getTfRecheio().setText(recheio + " ");
        telaSalgados.getTfPreco().setText(preco + " ");

    }
}

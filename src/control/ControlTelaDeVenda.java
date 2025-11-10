/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Locale;
import javax.print.attribute.standard.Fidelity;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;
import model.Fila;
import model.ItemVenda;
import model.Pizza;
import static model.Pizza.lerPizza;
import model.Produto;
import model.Salgadinho;
import model.Venda;
import view.TelaDeVenda;

/**
 *
 * @author HP
 */
public class ControlTelaDeVenda {

    //metodo para listar Venda Salgados
    private final TelaDeVenda telaVenda;
    Fila<ItemVenda> itens = new Fila<>();

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

    //Metodo para selecionar a tabela Pizza
    public void selecionartabelaPizza() {
        int linha = telaVenda.getTabelaPizzaVenda().getSelectedRow();

        String id = telaVenda.getTabelaPizzaVenda().getValueAt(linha, 0).toString();
        telaVenda.getTfIdVenda().setText(id);
    }

    public void selecionartabelaSalgados() {
        int linha = telaVenda.getTabelaSalgadosVenda().getSelectedRow();

        String id = telaVenda.getTabelaSalgadosVenda().getValueAt(linha, 0).toString();
        telaVenda.getTfIdVenda().setText(id);
    }

    //Metodo para listar no carrinho
    public void listarItens() {
        DefaultTableModel model = (DefaultTableModel) telaVenda.getTabelaCarrinhoVenda().getModel();
        model.setRowCount(0);

<<<<<<< HEAD
        while (!itens.estaVazia()) {

            ItemVenda atual = itens.desenfileirar();
=======
<<<<<<< HEAD
        Fila<ItemVenda> fila = itens;
=======
<<<<<<< HEAD
       // Fila<ItemVenda> fila = itens;
=======
        Fila<itemVenda> fila = itens;
>>>>>>> 191a1c3ba4b9143a730499241c83a2e83dbcb0f9
>>>>>>> 930fcb238a05081a3a7f9a672a237b53ab89faac

        while (!itens.estaVazia()) {

<<<<<<< HEAD
            ItemVenda atual = fila.desenfileirar();
=======
<<<<<<< HEAD
            ItemVenda atual = itens.desenfileirar();
=======
            itemVenda atual = fila.desenfileirar();
>>>>>>> 191a1c3ba4b9143a730499241c83a2e83dbcb0f9
>>>>>>> 930fcb238a05081a3a7f9a672a237b53ab89faac
>>>>>>> f7f15d34b71537b8876965f4718f9e3685273428

            if (atual != null) {
                model.addRow(new Object[]{
                    atual.getItem().getId(),
                    atual.getItem().getNome(),
                    atual.getQtd()});

            }

        }
    }

    public void adicionarItemNoCarrinho() {

        int id = Integer.parseInt(telaVenda.getTfIdVenda().getText().trim());

        int quantidade = Integer.parseInt(telaVenda.getTfQtd().getText().trim());
<<<<<<< HEAD
        Fila<Pizza> fila = Pizza.lerPizza();//le pizza ou salgadinhi

        Produto produto = fila.desenfileirar();//pega uma pizza ou slagadinho atribui como produtp

        //if (itens != null) {
        itens = ItemVenda.adicionarItem(itens, produto, quantidade);//adiciona item, e o metodo retorna o item adicionado
        listarItens();
=======
        Pizza pizza = Pizza.lerPizzaPorId(id);
        Salgadinho salgado = Salgadinho.lerPizzaPorId(id);
        while (pizza != null || salgado != null) {
            if (pizza.getId() == id) {
<<<<<<< HEAD
                itens = ItemVenda.adicionarItem(itens, pizza, id);
            } else if (salgado.getId() == id) {
                itens = ItemVenda.adicionarItem(itens, salgado, id);
=======
<<<<<<< HEAD
                itens = ItemVenda.adicionarItem(itens, pizza, quantidade);
            } else if (salgado.getId() == id) {
                itens = ItemVenda.adicionarItem(itens, salgado, quantidade);
=======
                itens = itemVenda.adicionarItem(itens, pizza, id);
            } else if (salgado.getId() == id) {
                itens = itemVenda.adicionarItem(itens, salgado, id);
>>>>>>> 191a1c3ba4b9143a730499241c83a2e83dbcb0f9
>>>>>>> 930fcb238a05081a3a7f9a672a237b53ab89faac
            }
>>>>>>> f7f15d34b71537b8876965f4718f9e3685273428

//     
        try {
            double valorTotal = ItemVenda.precoTotal(itens);

            // ✅ Validar se o valor é válido
            if (Double.isNaN(valorTotal) || Double.isInfinite(valorTotal)) {
                telaVenda.getLblValorTotal().setText("0.00");
                return;
            }

            // ✅ Garantir que o valor não seja negativo
            if (valorTotal < 0) {
                valorTotal = 0.00;
            }

            // ✅ Formatar com duas casas decimais e locale correto
            String valorFormatado = String.format(Locale.US, "%.2f", valorTotal);

            // ✅ Substituir ponto por vírgula se necessário para o formato brasileiro
            valorFormatado = valorFormatado.replace(".", ",");

            telaVenda.getLblValorTotal().setText(valorFormatado);

        } catch (Exception e) {
            System.err.println("Erro ao calcular preço total: " + e.getMessage());
            telaVenda.getLblValorTotal().setText("0,00");
        }

//        } else {
//            JOptionPane.showMessageDialog(null, "O produto já está na lista", "Produto na Lista", quantidade);
//
//        }
        // Conversão do precoTotal da venda
        double valorTotal = ItemVenda.precoTotal(itens);
        String valorFormatado = String.valueOf(valorTotal);// Duas casas decimais

        telaVenda.getLblValorTotal().setText(valorFormatado);

    }

    public void removerItemDoCarinho() {
        try {
            int selectedRow = telaVenda.getTabelaCarrinhoVenda().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(telaVenda, "Selecione um item para remover!");
                return;
            }

            String produtoID = String.valueOf(telaVenda.getTabelaCarrinhoVenda().getValueAt(selectedRow, 0));
            int Id = Integer.parseInt(produtoID);

            // Remover item usando Iterator
            boolean itemRemovido = false;
            while (!itens.estaVazia()) {
                ItemVenda item = itens.desenfileirar();
                // ✅ CORRETO - Usar equals() para comparação de Long
                if (item.getItem().getId() == Id) {
                    itens.desenfileirar();
                    itemRemovido = true;

                    double valorTotal = ItemVenda.precoTotal(itens);
                    String valorFormatado = String.valueOf(valorTotal);// Duas casas decimais
                    telaVenda.getLblValorTotal().setText(valorFormatado);
                    break;
                }
            }

            if (itemRemovido) {
                // ✅ CORRETO - Atualizar modelo da tabela
                listarItens();

                JOptionPane.showMessageDialog(telaVenda, "Item removido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(telaVenda, "Item não encontrado no carrinho!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(telaVenda, "Erro ao remover item: " + e.getMessage());
        }
    }

    //metodo para finalizar venda
    public void finalizarVenda() {

        float ValorRecebido = Float.parseFloat(telaVenda.getTfValorRecebido().getText().trim());

        if(Venda.criarVenda(itens, LocalDate.now(), ValorRecebido)){
            JOptionPane.showMessageDialog(null, "Venda Finalizada com sucesso");
        }else{
            System.out.println("Erro ao cria finalizar Venda");
        }

    }

}

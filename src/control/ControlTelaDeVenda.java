/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.Iterator;
import java.util.Locale;
import javax.print.attribute.standard.Fidelity;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;
import model.Fila;
import model.itemVenda;
import model.Pizza;
import static model.Pizza.lerPizza;
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
    Fila<itemVenda> itens = new Fila<>();

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

        Fila<itemVenda> fila = itens;

        while (!fila.estaVazia()) {

            itemVenda atual = fila.desenfileirar();

            if (atual != null) {
                model.addRow(new Object[]{
                    atual.getItem(),
                    atual.getQtd(),});

            }

        }
    }

    public void adicionarItemNoCarrinho() {

        int id = Integer.parseInt(telaVenda.getTfIdVenda().getText().trim());
        //int quantidade = Integer.parseInt(telaVenda.getTfQtd().getText());
        Pizza pizza = Pizza.lerPizzaPorId(id);
        Salgadinho salgado = Salgadinho.lerPizzaPorId(id);
        while (pizza != null || salgado != null) {
            if (pizza.getId() == id) {
                itens = itemVenda.adicionarItem(itens, pizza, id);
            } else if (salgado.getId() == id) {
                itens = itemVenda.adicionarItem(itens, salgado, id);
            }

//     
//                try {
//                    double valorTotal = Venda.calcPrecoTotal(itens);
//
//                    // ✅ Validar se o valor é válido
//                    if (Double.isNaN(valorTotal) || Double.isInfinite(valorTotal)) {
//                        viewteladevendedor.getLblPrecoDeTotalVenda().setText("0.00");
//                        return;
//                    }
//
//                    // ✅ Garantir que o valor não seja negativo
//                    if (valorTotal < 0) {
//                        valorTotal = 0.00;
//                    }
//
//                    // ✅ Formatar com duas casas decimais e locale correto
//                    String valorFormatado = String.format(Locale.US, "%.2f", valorTotal);
//
//                    // ✅ Substituir ponto por vírgula se necessário para o formato brasileiro
//                    valorFormatado = valorFormatado.replace(".", ",");
//
//                    viewteladevendedor.getLblPrecoDeTotalVenda().setText(valorFormatado);
//
//                } catch (Exception e) {
//                    System.err.println("Erro ao calcular preço total: " + e.getMessage());
//                    viewteladevendedor.getLblPrecoDeTotalVenda().setText("0,00");
//                }
//
//                listarProdutosSelecionados(itens);
//
//            } else {
//                JOptionPane.showMessageDialog(null, "O produto já está na lista", "Produto na Lista", quantidade);
//
//            }
        }
//        // Conversão do precoTotal da venda
//        double valorTotal = Venda.calcPrecoTotal(itens);
//        String valorFormatado = String.valueOf(valorTotal);// Duas casas decimais
//        viewteladevendedor.getLblPrecoDeTotalVenda().setText(valorFormatado);
//    }
//
//    public void removerItemDoCarinho() {
//        try {
//            int selectedRow = telaVenda.getTabelaProdutosSelecionados().getSelectedRow();
//            if (selectedRow == -1) {
//                JOptionPane.showMessageDialog(viewteladevendedor, "Selecione um item para remover!");
//                return;
//            }
//
//            String produtoID = String.valueOf(viewteladevendedor.getTabelaProdutosSelecionados().getValueAt(selectedRow, 0));
//            int Id = Integer.parseInt(produtoID);
//
//            // Remover item usando Iterator
//            Iterator<ItemVenda> iterator = itens.iterator();
//            boolean itemRemovido = false;
//            while (iterator.hasNext()) {
//                ItemVenda item = iterator.next();
//                // ✅ CORRETO - Usar equals() para comparação de Long
//                if (item.getProduto().getId() == Id) {
//                    iterator.remove();
//                    itemRemovido = true;
//
//                    double valorTotal = Venda.calcPrecoTotal(itens);
//                    String valorFormatado = String.valueOf(valorTotal);// Duas casas decimais
//                    viewteladevendedor.getLblPrecoDeTotalVenda().setText(valorFormatado);
//                    break;
//                }
//            }
//
//            if (itemRemovido) {
//                // ✅ CORRETO - Atualizar modelo da tabela
//                atualizarTabelaProdutosSelecionados();
//
//                // ✅ CORRETO - Atualizar totais
//                listarProdutosSelecionados(itens);
//
//                JOptionPane.showMessageDialog(viewteladevendedor, "Item removido com sucesso!");
//            } else {
//                JOptionPane.showMessageDialog(viewteladevendedor, "Item não encontrado no carrinho!");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(viewteladevendedor, "Erro ao remover item: " + e.getMessage());
//        }
//    }

    }
}

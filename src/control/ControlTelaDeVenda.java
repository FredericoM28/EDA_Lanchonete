/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.time.LocalDate;
import java.util.Locale;
import javax.swing.JOptionPane;
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
    Fila<ItemVenda> itens = new Fila();
    Fila<ItemVenda> itemAuxiliar = new Fila();
    ControlTelaListarVenda listar;

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
                    atual.getNome(),
                    atual.getRecheio(),
                    atual.getBorda(),
                    atual.getMolho(),
                    atual.getPreco(),});

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
                    atual.getNome(),
                    atual.getTipo(),
                    atual.getRecheio(),
                    atual.getMassa(),
                    atual.getPreco(),});

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

        Fila<ItemVenda> aux = new Fila<>();

        while (!this.itens.estaVazia()) {
            ItemVenda atual = this.itens.desenfileirar();
            aux.enfileirar(atual);

            model.addRow(new Object[]{
                atual.getItem().getId(),
                atual.getItem().getNome(),
                atual.getQtd()
            });
        }

        while (!aux.estaVazia()) {
            this.itens.enfileirar(aux.desenfileirar());
        }
    }

    public void calculoTrocos() {
        try {
            String valorRecebidoTexto = telaVenda.getTfValorRecebido().getText().trim();

            if (valorRecebidoTexto.isEmpty()) {
                telaVenda.getLblTrocos().setText("R$ 0.00");
                return;
            }

            // Normalizar formato numérico
            valorRecebidoTexto = valorRecebidoTexto.replace(",", ".").replaceAll("[^\\d.]", "");

            if (valorRecebidoTexto.isEmpty() || valorRecebidoTexto.equals(".")) {
                telaVenda.getLblTrocos().setText("R$ 0.00");
                return;
            }

            float valorRecebido = Float.parseFloat(valorRecebidoTexto);
            float troco = ItemVenda.troco(valorRecebido, itens);

            // Formatar como moeda brasileira
            String trocoTexto = String.format("R$ %.2f", Math.max(0, troco));
            telaVenda.getLblTrocos().setText(trocoTexto);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(telaVenda, "Digite um valor válido!", "Erro", JOptionPane.ERROR_MESSAGE);
            telaVenda.getLblTrocos().setText("R$ 0.00");
        }
    }
//    public void calculoTrocos() {
//        String valorRecebidoTexto = telaVenda.getTfValorRecebido().getText().trim();
//
//        float valorRecebido = Float.parseFloat(valorRecebidoTexto);
//        float troco = ItemVenda.troco(valorRecebido, itens);
//        String trocoTexto = String.format("R$ %.2f",troco);
//      //  trocoTexto.replace(",", ".").replaceAll("[^\\d.]", "");
//        telaVenda.getLblTrocos().setText(trocoTexto);
//
//    }

    public void adicionarItemNoCarrinho() {
        try {
            // Validação dos campos de entrada
            if (telaVenda.getTfIdVenda().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Informe o ID do produto!", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (telaVenda.getTfQtd().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Informe a quantidade!", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Conversão segura dos valores
            int id = Integer.parseInt(telaVenda.getTfIdVenda().getText().trim());
            int quantidade = Integer.parseInt(telaVenda.getTfQtd().getText().trim());

            // Validação da quantidade
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(null, "A quantidade deve ser maior que zero!", "Quantidade Inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar estoque disponível
            Fila<Pizza> fila = Pizza.lerPizza();
            if (fila.estaVazia()) {
                JOptionPane.showMessageDialog(null, "Não há produtos disponíveis!", "Estoque Vazio", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Buscar produto pelo ID
            Produto produto = Produto.lerProdutoPorId(id);
            if (produto == null) {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!\nVerifique o ID informado.", "Produto Inexistente", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Adicionar item ao carrinho
            this.itens = ItemVenda.adicionarItem(this.itens, produto, quantidade);

            // Atualizar interface
            listarItens();
            atualizarValorTotal();

            // Limpar campos após adição
            telaVenda.getTfQtd().setText("1"); // Reset para quantidade padrão
            telaVenda.getTfIdVenda().requestFocus(); // Foco no campo de ID para próximo produto

            // Feedback de sucesso
            JOptionPane.showMessageDialog(null,
                    String.format("%s adicionado ao carrinho!\nQuantidade: %d", produto.getNome(), quantidade),
                    "Item Adicionado",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "ID e Quantidade devem ser números válidos!",
                    "Erro de Formatação",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao adicionar item: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

// Método auxiliar para atualizar o valor total
    private void atualizarValorTotal() {
        try {
            float valorTotal = ItemVenda.precoTotal(itens);

            // Validações de segurança
            if (Float.isNaN(valorTotal) || Float.isInfinite(valorTotal)) {
                valorTotal = 0.00f;
            }

            // Garantir que o valor não seja negativo
            if (valorTotal < 0) {
                valorTotal = 0.00f;
            }

            // Formatar para o padrão brasileiro
            String valorFormatado = String.format("R$ %.2f", valorTotal).replace(".", ",");
            telaVenda.getLblValorTotal().setText(valorFormatado);

        } catch (Exception e) {
            telaVenda.getLblValorTotal().setText("R$ 0,00");
        }
    }
//    public void adicionarItemNoCarrinho() {
//
//        int id = Integer.parseInt(telaVenda.getTfIdVenda().getText().trim());
//        int quantidade = Integer.parseInt(telaVenda.getTfQtd().getText().trim());
//
//        Fila<Pizza> fila = Pizza.lerPizza();//le pizza ou salgadinhi
//        if (fila.estaVazia()) {
//            JOptionPane.showMessageDialog(null, "Não há salgadinhos disponíveis!", "Estoque Vazio", JOptionPane.WARNING_MESSAGE);
//            return; // Sai do método sem executar o restante
//        }
//
//        Produto produto = Produto.lerProdutoPorId(id);//pega uma pizza ou slagadinho atribui como produtp
//        if (produto == null) {
//            System.out.println("nullo");
//            return;
//        }
//        //if (itens != null) {
//        if (!telaVenda.getTfQtd().getText().trim().isEmpty()) {
//            this.itens = ItemVenda.adicionarItem(this.itens, produto, quantidade);//adiciona item, e o metodo retorna o item adicionado
//            listarItens();
//        }
//
//        float valorTotal = ItemVenda.precoTotal(itens);
//        System.out.println(valorTotal);
//        String valorFormatado = String.format(Locale.US, "%.2f", valorTotal);
//        // ✅ Validar se o valor é válido
//        if (Float.isNaN(valorTotal) || Float.isInfinite(valorTotal)) {
//            telaVenda.getLblValorTotal().setText("0.00");
//            return;
//        }
//
//        // ✅ Garantir que o valor não seja negativo
//        if (valorTotal < 0) {
//            valorTotal = (float) 0.00;
//        }
//
//        // ✅ Formatar com duas casas decimais e locale correto
//        // ✅ Substituir ponto por vírgula se necessário para o formato brasileiro
//        valorFormatado = valorFormatado.replace(".", ",");
//
//        telaVenda.getLblValorTotal().setText(valorFormatado);
//
//    }

    public void removerItemDoCarinho() {
        try {
            int selectedRow = telaVenda.getTabelaCarrinhoVenda().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(telaVenda, "Selecione um item para remover!");
                return;
            }

            String produtoID = String.valueOf(telaVenda.getTabelaCarrinhoVenda().getValueAt(selectedRow, 0));
            int Id = Integer.parseInt(produtoID);

            // Remover item usando Iteratorla
            Fila<ItemVenda> filaAuxiliar = new Fila();

            while (!this.itens.estaVazia()) {
                ItemVenda atual = this.itens.desenfileirar();
                if (atual.getItem().getId() != Id) {
                    filaAuxiliar.enfileirar(atual);
                }

            }

            while (!filaAuxiliar.estaVazia()) {
                this.itens.enfileirar(filaAuxiliar.desenfileirar());
            }

//            boolean itemRemovido = false;
//            while (!itens.estaVazia()) {
//                ItemVenda item = itens.desenfileirar();
//                // ✅ CORRETO - Usar equals() para comparação de Long
//                if (item.getItem().getId() == Id) {
//                    itens.desenfileirar();
//                    itemRemovido = true;
//
            double valorTotal = ItemVenda.precoTotal(itens);
            String valorFormatado = String.valueOf(valorTotal);// Duas casas decimais
            telaVenda.getLblValorTotal().setText(valorFormatado);
//                    break;
//                }
//            }

            // if (itemRemovido) {
            // ✅ CORRETO - Atualizar modelo da tabela
            listarItens();
//
//                JOptionPane.showMessageDialog(telaVenda, "Item removido com sucesso!");
//            } else {
//                JOptionPane.showMessageDialog(telaVenda, "Item não encontrado no carrinho!");
//            }
//
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(telaVenda, "Erro ao remover item: " + e.getMessage());
        }
    }

    //metodo para finalizar venda
    public void finalizarVenda() {

        float ValorRecebido = Float.parseFloat(telaVenda.getTfValorRecebido().getText().trim());

        if (Venda.criarVenda(itens, LocalDate.now(), ValorRecebido)) {
            JOptionPane.showMessageDialog(null, "Venda Finalizada com sucesso");
           // listar.listarVendas();
        } else {
            System.out.println("Erro ao cria finalizar Venda");
        }

    }

}

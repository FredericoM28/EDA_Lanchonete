/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author HP
 */
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class VendaFrame extends JFrame {

    private JTable tabelaProdutos, tabelaCarrinho;
    private DefaultTableModel tableModelProdutos, tableModelCarrinho;
    private JTextField txtQuantidade, txtValorRecebido, txtTotal, txtTroco;
    private JButton btnAdicionarCarrinho, btnRemoverCarrinho, btnFinalizarVenda, btnLimparCarrinho;
    private Fila<ItemVenda> carrinho;

    public VendaFrame() {
        carrinho = new Fila<ItemVenda>();
        initComponents();
        carregarProdutos();
        atualizarTotal();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Sistema de Vendas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de produtos
        JPanel produtosPanel = new JPanel(new BorderLayout());
        produtosPanel.setBorder(BorderFactory.createTitledBorder("Produtos Disponíveis"));

        String[] colunasProdutos = {"ID", "Tipo", "Nome", "Detalhes", "Preço"};
        tableModelProdutos = new DefaultTableModel(colunasProdutos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaProdutos = new JTable(tableModelProdutos);
        JScrollPane scrollProdutos = new JScrollPane(tabelaProdutos);
        produtosPanel.add(scrollProdutos, BorderLayout.CENTER);

        // Painel de controle
        JPanel controlePanel = new JPanel(new FlowLayout());
        controlePanel.add(new JLabel("Quantidade:"));
        txtQuantidade = new JTextField("1", 5);
        controlePanel.add(txtQuantidade);

        btnAdicionarCarrinho = criarBotao("➕ Adicionar ao Carrinho", new Color(46, 204, 113));
        controlePanel.add(btnAdicionarCarrinho);

        produtosPanel.add(controlePanel, BorderLayout.SOUTH);

        // Painel do carrinho
        JPanel carrinhoPanel = new JPanel(new BorderLayout());
        carrinhoPanel.setBorder(BorderFactory.createTitledBorder("Carrinho de Compras"));

        String[] colunasCarrinho = {"ID", "Produto", "Quantidade", "Preço Unit.", "Subtotal"};
        tableModelCarrinho = new DefaultTableModel(colunasCarrinho, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaCarrinho = new JTable(tableModelCarrinho);
        JScrollPane scrollCarrinho = new JScrollPane(tabelaCarrinho);
        carrinhoPanel.add(scrollCarrinho, BorderLayout.CENTER);

        // Painel de botões do carrinho
        JPanel carrinhoBotoesPanel = new JPanel(new FlowLayout());
        btnRemoverCarrinho = criarBotao("➖ Remover do Carrinho", new Color(231, 76, 60));
        btnLimparCarrinho = criarBotao("🗑️ Limpar Carrinho", new Color(241, 196, 15));

        carrinhoBotoesPanel.add(btnRemoverCarrinho);
        carrinhoBotoesPanel.add(btnLimparCarrinho);
        carrinhoPanel.add(carrinhoBotoesPanel, BorderLayout.SOUTH);

        // Painel de pagamento
        JPanel pagamentoPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        pagamentoPanel.setBorder(BorderFactory.createTitledBorder("Pagamento"));

        pagamentoPanel.add(new JLabel("Total:"));
        txtTotal = new JTextField();
        txtTotal.setEditable(false);
        txtTotal.setBackground(Color.LIGHT_GRAY);
        pagamentoPanel.add(txtTotal);

        pagamentoPanel.add(new JLabel("Valor Recebido:"));
        txtValorRecebido = new JTextField();
        pagamentoPanel.add(txtValorRecebido);

        pagamentoPanel.add(new JLabel("Troco:"));
        txtTroco = new JTextField();
        txtTroco.setEditable(false);
        txtTroco.setBackground(Color.LIGHT_GRAY);
        pagamentoPanel.add(txtTroco);

        btnFinalizarVenda = criarBotao("💰 Finalizar Venda", new Color(155, 89, 182));
        pagamentoPanel.add(btnFinalizarVenda);

        // Layout principal
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, produtosPanel, carrinhoPanel);
        splitPane.setResizeWeight(0.5);

        mainPanel.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(pagamentoPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Event Listeners
        btnAdicionarCarrinho.addActionListener(e -> adicionarAoCarrinho());
        btnRemoverCarrinho.addActionListener(e -> removerDoCarrinho());
        btnLimparCarrinho.addActionListener(e -> limparCarrinho());
        btnFinalizarVenda.addActionListener(e -> finalizarVenda());

        txtValorRecebido.addActionListener(e -> calcularTroco());
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return botao;
    }

    private void carregarProdutos() {
        tableModelProdutos.setRowCount(0);

        // Carregar pizzas
        Fila<Pizza> pizzas = Pizza.lerPizza();
        Fila<Pizza> tempPizzas = new Fila<>();

        while (!pizzas.estaVazia()) {
            Pizza pizza = pizzas.desenfileirar();
            tempPizzas.enfileirar(pizza);

            Object[] rowData = {
                pizza.getId(),
                "Pizza",
                pizza.getNome(),
                String.format("Recheio: %s, Borda: %s", pizza.getRecheio(), pizza.getBorda()),
                String.format("Mzn %.2f", pizza.getPreco())
            };
            tableModelProdutos.addRow(rowData);
        }

        while (!tempPizzas.estaVazia()) {
            pizzas.enfileirar(tempPizzas.desenfileirar());
        }

        // Carregar salgadinhos
        Fila<Salgadinho> salgadinhos = Salgadinho.lerSalgadinho();
        Fila<Salgadinho> tempSalgadinhos = new Fila<>();

        while (!salgadinhos.estaVazia()) {
            Salgadinho salgadinho = salgadinhos.desenfileirar();
            tempSalgadinhos.enfileirar(salgadinho);

            Object[] rowData = {
                salgadinho.getId(),
                "Salgadinho",
                salgadinho.getNome(),
                String.format("Tipo: %s, Massa: %s", salgadinho.getTipo(), salgadinho.getMassa()),
                String.format("Mzn %.2f", salgadinho.getPreco())
            };
            tableModelProdutos.addRow(rowData);
        }

        while (!tempSalgadinhos.estaVazia()) {
            salgadinhos.enfileirar(tempSalgadinhos.desenfileirar());
        }
    }

    private void adicionarAoCarrinho() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = (int) tableModelProdutos.getValueAt(selectedRow, 0);
            String nomeProduto = tableModelProdutos.getValueAt(selectedRow, 2).toString();
            int quantidade = Integer.parseInt(txtQuantidade.getText().trim());

            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Produto produto = Produto.lerProdutoPorId(id);
            if (produto != null) {
                // Verifica se o produto já está no carrinho
                ItemVenda itemExistente = ItemVenda.buscarItemPorId(carrinho, id);

                if (itemExistente != null) {
                    // Produto já existe - pergunta se quer somar ou substituir
                    Object[] options = {"Somar Quantidades", "Substituir Quantidade", "Cancelar"};
                    int choice = JOptionPane.showOptionDialog(this,
                            "Este produto já está no carrinho!\n"
                            + "Quantidade atual: " + itemExistente.getQtd() + "\n"
                            + "Nova quantidade: " + quantidade + "\n\n"
                            + "O que deseja fazer?",
                            "Produto Já no Carrinho",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (choice == 0) {
                        // Somar quantidades
                        carrinho = ItemVenda.adicionarItem(carrinho, produto, quantidade);
                        JOptionPane.showMessageDialog(this,
                                "Quantidade somada ao carrinho!\nTotal: "
                                + (itemExistente.getQtd() + quantidade),
                                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else if (choice == 1) {
                        // Substituir quantidade
                        carrinho = ItemVenda.atualizarQuantidade(carrinho, id, quantidade);
                        JOptionPane.showMessageDialog(this,
                                "Quantidade atualizada no carrinho!",
                                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }
                    // Se choice == 2 (Cancelar), não faz nada
                } else {
                    // Produto não está no carrinho - adiciona normalmente
                    carrinho = ItemVenda.adicionarItem(carrinho, produto, quantidade);
                    JOptionPane.showMessageDialog(this,
                            "Produto adicionado ao carrinho!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }

                atualizarCarrinho();
                atualizarTotal();
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Fila<ItemVenda> atualizarQuantidade(Fila<ItemVenda> fila, int id, int novaQuantidade) {
        Fila<ItemVenda> auxiliar = new Fila<>();

        while (!fila.estaVazia()) {
            ItemVenda item = fila.desenfileirar();

            if (item.getItem().getId() == id) {
                item.setQtd(novaQuantidade);
            }
            auxiliar.enfileirar(item);
        }

        // Restaura a fila original
        while (!auxiliar.estaVazia()) {
            fila.enfileirar(auxiliar.desenfileirar());
        }

        return fila;
    }

    private void removerDoCarrinho() {
        int selectedRow = tabelaCarrinho.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item do carrinho!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModelCarrinho.getValueAt(selectedRow, 0);
        carrinho = ItemVenda.removerItem(carrinho, id);
        atualizarCarrinho();
        atualizarTotal();
    }

    private void limparCarrinho() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja limpar o carrinho?", "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            carrinho = new Fila<>();
            atualizarCarrinho();
            atualizarTotal();
        }
    }

    private void finalizarVenda() {
        if (carrinho.estaVazia()) {
            JOptionPane.showMessageDialog(this, "Carrinho vazio! Adicione produtos antes de finalizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            float valorRecebido = Float.parseFloat(txtValorRecebido.getText().trim());
            float total = Float.parseFloat(txtTotal.getText().replace("Mzn", "").trim());

            if (valorRecebido < total) {
                JOptionPane.showMessageDialog(this, "Valor recebido é menor que o total!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean sucesso = Venda.criarVenda(carrinho, LocalDate.now(), valorRecebido);
            if (sucesso) {
                JOptionPane.showMessageDialog(this,
                        String.format("Venda realizada com sucesso!\nTotal: Mzn %.2f\nTroco: Mzn %.2f",
                                total, valorRecebido - total),
                        "Venda Concluída", JOptionPane.INFORMATION_MESSAGE);

                carrinho = new Fila<>();
                atualizarCarrinho();
                atualizarTotal();
                txtValorRecebido.setText("");
                txtTroco.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor recebido deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularTroco() {
        try {
            if (!txtValorRecebido.getText().trim().isEmpty()) {
                float valorRecebido = Float.parseFloat(txtValorRecebido.getText().trim());
                float total = Float.parseFloat(txtTotal.getText().replace("Mzn", "").trim());
                float troco = valorRecebido - total;
                txtTroco.setText(String.format("Mzn %.2f", troco));
            }
        } catch (NumberFormatException e) {
            txtTroco.setText("Mzn 0.00");
        }
    }

    private void atualizarCarrinho() {
        tableModelCarrinho.setRowCount(0);
        Fila<ItemVenda> temp = new Fila<>();

        while (!carrinho.estaVazia()) {
            ItemVenda item = carrinho.desenfileirar();
            temp.enfileirar(item);

            float subtotal = item.getQtd() * (float) item.getItem().getPreco();

            Object[] rowData = {
                item.getItem().getId(),
                item.getItem().getNome(),
                item.getQtd(),
                String.format("Mzn %.2f", item.getItem().getPreco()),
                String.format("Mzn %.2f", subtotal)
            };
            tableModelCarrinho.addRow(rowData);
        }

        while (!temp.estaVazia()) {
            carrinho.enfileirar(temp.desenfileirar());
        }
    }

    private void atualizarTotal() {
        float total = ItemVenda.precoTotal(carrinho);
        txtTotal.setText(String.format("Mzn %.2f", total));
        calcularTroco();
    }
}

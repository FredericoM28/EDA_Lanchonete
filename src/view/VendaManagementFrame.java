package view;

//import com.formdev.flatlaf.FlatLightLaf;
import model.Venda;
import model.ItemVenda;
import model.Fila;
import model.Produto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

public class VendaManagementFrame extends JFrame {
    private JTextField idProdutoField, quantidadeField, valorRecebidoField, idVendaField;
    private JTable vendaTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    private Fila<ItemVenda> itens = new Fila<>();
    private DefaultListModel<String> itensListModel;
    private JList<String> itensList;

    public VendaManagementFrame() {
        setTitle("Gerenciar Vendas - Pizzaria Sabor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Gerenciar Vendas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        idProdutoField = createTextField();
        quantidadeField = createTextField();
        valorRecebidoField = createTextField();
        idVendaField = createTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ID da Venda (para deletar):"), gbc);
        gbc.gridx = 1;
        formPanel.add(idVendaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("ID do Produto:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idProdutoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx = 1;
        formPanel.add(quantidadeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Valor Recebido:"), gbc);
        gbc.gridx = 1;
        formPanel.add(valorRecebidoField, gbc);

        itensListModel = new DefaultListModel<>();
        itensList = new JList<>(itensListModel);
        itensList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane itensScrollPane = new JScrollPane(itensList);
        itensScrollPane.setPreferredSize(new Dimension(200, 100));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(itensScrollPane, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(createActionButton("Adicionar Item", "➕", e -> addItem()));
        buttonPanel.add(createActionButton("Registrar Venda", "💰", e -> registerVenda()));
        buttonPanel.add(createActionButton("Listar Vendas", "📋", e -> listVendas()));
        buttonPanel.add(createActionButton("Deletar Venda", "🗑️", e -> deleteVenda()));

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(buttonPanel, gbc);

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(255, 99, 71));
        gbc.gridy = 6;
        formPanel.add(statusLabel, gbc);

        String[] columns = {"ID Venda", "Data", "Valor Total", "Valor Recebido"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        vendaTable = new JTable(tableModel);
        vendaTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        vendaTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        vendaTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(vendaTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane.setPreferredSize(new Dimension(400, 400));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        centerPanel.add(formPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        centerPanel.add(scrollPane, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        return field;
    }

    private JButton createActionButton(String text, String icon, java.awt.event.ActionListener action) {
        JButton button = new JButton(icon + " " + text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(255, 99, 71));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(255, 140, 0));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(255, 99, 71));
            }
        });
        return button;
    }

    private void addItem() {
        try {
            int idProduto = Integer.parseInt(idProdutoField.getText().trim());
            int quantidade = Integer.parseInt(quantidadeField.getText().trim());

            if (quantidade <= 0) {
                statusLabel.setText("Erro: Quantidade deve ser positiva!");
                return;
            }

            Produto produto = Produto.lerProdutoPorId(idProduto);
            if (produto == null) {
                statusLabel.setText("Erro: Produto não encontrado!");
                return;
            }

            itens = ItemVenda.adicionarItem(itens, produto, quantidade);
            itensListModel.addElement("ID: " + idProduto + ", Nome: " + produto.getNome() + ", Qtd: " + quantidade);
            statusLabel.setText("Item adicionado à venda!");
            idProdutoField.setText("");
            quantidadeField.setText("");
        } catch (NumberFormatException e) {
            statusLabel.setText("Erro: ID do produto ou quantidade inválida!");
        }
    }

    private void registerVenda() {
        if (itens.estaVazia()) {
            statusLabel.setText("Erro: Adicione pelo menos um item!");
            return;
        }

        try {
            float valorRecebido = Float.parseFloat(valorRecebidoField.getText().trim());
            if (valorRecebido < 0) {
                statusLabel.setText("Erro: Valor recebido deve ser não-negativo!");
                return;
            }

            boolean criada = Venda.criarVenda(itens, LocalDate.now(), valorRecebido);
            statusLabel.setText(criada ? "Venda registrada com sucesso!" : "Erro ao registrar venda.");
            itens = new Fila<>();
            itensListModel.clear();
            valorRecebidoField.setText("");
        } catch (NumberFormatException e) {
            statusLabel.setText("Erro: Valor recebido inválido!");
        }
    }

    private void listVendas() {
        tableModel.setRowCount(0);
        Fila<Venda> vendas = Venda.lerVenda();
        Fila<Venda> temp = new Fila<>();
        while (!vendas.estaVazia()) {
            Venda v = vendas.desenfileirar();
            tableModel.addRow(new Object[]{
                    v.getIdVenda(),
                    v.getDataDeVenda(),
                    v.getValorTotal(),
                    v.getValorRecebido()
            });
            temp.enfileirar(v);
        }
        while (!temp.estaVazia()) {
            vendas.enfileirar(temp.desenfileirar());
        }
        statusLabel.setText(tableModel.getRowCount() > 0 ? "Lista atualizada." : "Nenhuma venda registrada.");
    }

    private void deleteVenda() {
        try {
            int id = Integer.parseInt(idVendaField.getText().trim());
            Fila<Venda> vendas = Venda.lerVenda();
            Fila<Venda> temp = new Fila<>();
            boolean found = false;
            while (!vendas.estaVazia()) {
                Venda v = vendas.desenfileirar();
                if (v.getIdVenda() != id) {
                    temp.enfileirar(v);
                } else {
                    found = true;
                }
            }
            while (!temp.estaVazia()) {
                vendas.enfileirar(temp.desenfileirar());
            }

            try (FileOutputStream file = new FileOutputStream("Venda.dat");
                 ObjectOutputStream out = new ObjectOutputStream(file)) {
                out.writeObject(vendas);
            } catch (Exception e) {
                statusLabel.setText("Erro ao salvar vendas!");
                return;
            }

            statusLabel.setText(found ? "Venda deletada com sucesso!" : "Venda não encontrada.");
            idVendaField.setText("");
        } catch (NumberFormatException e) {
            statusLabel.setText("Erro: ID inválido!");
        }
    }
}
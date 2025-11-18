package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */


import model.Pizza;
import model.Fila;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaFrame extends JFrame {
    private JTextField txtNome, txtRecheio, txtBorda, txtMolho, txtPreco;
    private JButton btnAdicionar, btnEditar, btnDeletar, btnLimpar, btnBuscar;
    private JTable tabelaPizzas;
    private DefaultTableModel tableModel;
    private int pizzaSelecionadaId = -1;

    public PizzaFrame() {
        initComponents();
        carregarPizzas();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Gerenciamento de Pizzas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados da Pizza"));

        // Campos do formulário
        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        formPanel.add(txtNome);

        formPanel.add(new JLabel("Recheio:"));
        txtRecheio = new JTextField();
        formPanel.add(txtRecheio);

        formPanel.add(new JLabel("Borda:"));
        txtBorda = new JTextField();
        formPanel.add(txtBorda);

        formPanel.add(new JLabel("Molho:"));
        txtMolho = new JTextField();
        formPanel.add(txtMolho);

        formPanel.add(new JLabel("Preço:"));
        txtPreco = new JTextField();
        formPanel.add(txtPreco);

        // Painel de botões do formulário
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAdicionar = criarBotao("➕ Adicionar", new Color(46, 204, 113));
        btnEditar = criarBotao("✏️ Editar", new Color(52, 152, 219));
        btnDeletar = criarBotao("🗑️ Deletar", new Color(231, 76, 60));
        btnLimpar = criarBotao("🧹 Limpar", new Color(241, 196, 15));
        btnBuscar = criarBotao("🔍 Buscar", new Color(155, 89, 182));

        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnDeletar);
        buttonPanel.add(btnLimpar);
        buttonPanel.add(btnBuscar);

        formPanel.add(buttonPanel);

        // Tabela de pizzas
        String[] colunas = {"ID", "Nome", "Recheio", "Borda", "Molho", "Preço"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaPizzas = new JTable(tableModel);
        tabelaPizzas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaPizzas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Pizzas"));

        // Adicionando componentes ao painel principal
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // Event Listeners
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarPizza();
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarPizza();
            }
        });

        btnDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarPizza();
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPizza();
            }
        });

        // Listener para seleção na tabela
        tabelaPizzas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaPizzas.getSelectedRow() != -1) {
                int selectedRow = tabelaPizzas.getSelectedRow();
                pizzaSelecionadaId = (int) tableModel.getValueAt(selectedRow, 0);
                carregarDadosPizzaSelecionada();
            }
        });
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return botao;
    }

    private void carregarPizzas() {
        tableModel.setRowCount(0);
        Fila<Pizza> pizzas = Pizza.lerPizza();
        Fila <Pizza> pizzasOrdenadas= Pizza.ordenarPorPreco(pizzas);
        Fila<Pizza> temp = new Fila<>();

        while (!pizzasOrdenadas.estaVazia()) {
            Pizza pizza = pizzasOrdenadas.desenfileirar();
            temp.enfileirar(pizza);
            
            Object[] rowData = {
                pizza.getId(),
                pizza.getNome(),
                pizza.getRecheio(),
                pizza.getBorda(),
                pizza.getMolho(),
                String.format("Mzn %.2f", pizza.getPreco())
            };
            tableModel.addRow(rowData);
        }

        // Restaurar fila original
        while (!temp.estaVazia()) {
            pizzas.enfileirar(temp.desenfileirar());
        }
    }

    private void adicionarPizza() {
        try {
            String nome = txtNome.getText().trim();
            String recheio = txtRecheio.getText().trim();
            String borda = txtBorda.getText().trim();
            String molho = txtMolho.getText().trim();
            double preco = Double.parseDouble(txtPreco.getText().trim());

            if (nome.isEmpty() || recheio.isEmpty() || borda.isEmpty() || molho.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean sucesso = Pizza.criarPizza(nome, recheio, borda, molho, preco);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Pizza adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarPizzas();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar pizza!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarPizza() {
        if (pizzaSelecionadaId == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma pizza para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String nome = txtNome.getText().trim();
            String recheio = txtRecheio.getText().trim();
            String borda = txtBorda.getText().trim();
            String molho = txtMolho.getText().trim();
            double preco = Double.parseDouble(txtPreco.getText().trim());

            boolean sucesso = Pizza.editarPorId(pizzaSelecionadaId, nome, recheio, borda, molho, preco);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Pizza editada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarPizzas();
                pizzaSelecionadaId = -1;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarPizza() {
        if (pizzaSelecionadaId == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma pizza para deletar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja deletar esta pizza?", "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean sucesso = Pizza.deletePizza(pizzaSelecionadaId);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Pizza deletada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarPizzas();
                pizzaSelecionadaId = -1;
            }
        }
    }

    private void buscarPizza() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID da pizza:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr.trim());
                Pizza pizza = Pizza.lerPizzaPorId(id);
                
                if (pizza.getId() != 0) {
                    txtNome.setText(pizza.getNome());
                    txtRecheio.setText(pizza.getRecheio());
                    txtBorda.setText(pizza.getBorda());
                    txtMolho.setText(pizza.getMolho());
                    txtPreco.setText(String.valueOf(pizza.getPreco()));
                    pizzaSelecionadaId = pizza.getId();
                } else {
                    JOptionPane.showMessageDialog(this, "Pizza não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarDadosPizzaSelecionada() {
        int selectedRow = tabelaPizzas.getSelectedRow();
        if (selectedRow != -1) {
            txtNome.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtRecheio.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtBorda.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtMolho.setText(tableModel.getValueAt(selectedRow, 4).toString());
            
            String precoStr = tableModel.getValueAt(selectedRow, 5).toString();
            precoStr = precoStr.replace("Mzn", "").trim();
            txtPreco.setText(precoStr);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtRecheio.setText("");
        txtBorda.setText("");
        txtMolho.setText("");
        txtPreco.setText("");
        pizzaSelecionadaId = -1;
        tabelaPizzas.clearSelection();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author HP
 */


import model.Salgadinho;
import model.Fila;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SalgadinhoFrame extends JFrame {
    private JTextField txtNome, txtTipo, txtMassa, txtRecheio, txtPreco;
    private JButton btnAdicionar, btnEditar, btnDeletar, btnLimpar, btnBuscar;
    private JTable tabelaSalgadinhos;
    private DefaultTableModel tableModel;
    private int salgadinhoSelecionadoId = -1;

    public SalgadinhoFrame() {
        initComponents();
        carregarSalgadinhos();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Gerenciamento de Salgadinhos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Salgadinho"));

        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        formPanel.add(txtNome);

        formPanel.add(new JLabel("Tipo (frito/assado):"));
        txtTipo = new JTextField();
        formPanel.add(txtTipo);

        formPanel.add(new JLabel("Massa:"));
        txtMassa = new JTextField();
        formPanel.add(txtMassa);

        formPanel.add(new JLabel("Recheio:"));
        txtRecheio = new JTextField();
        formPanel.add(txtRecheio);

        formPanel.add(new JLabel("Preço:"));
        txtPreco = new JTextField();
        formPanel.add(txtPreco);

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

        // Tabela
        String[] colunas = {"ID", "Nome", "Tipo", "Massa", "Recheio", "Preço"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaSalgadinhos = new JTable(tableModel);
        tabelaSalgadinhos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaSalgadinhos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Salgadinhos"));

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);

        // Event Listeners
        btnAdicionar.addActionListener(e -> adicionarSalgadinho());
        btnEditar.addActionListener(e -> editarSalgadinho());
        btnDeletar.addActionListener(e -> deletarSalgadinho());
        btnLimpar.addActionListener(e -> limparCampos());
        btnBuscar.addActionListener(e -> buscarSalgadinho());

        tabelaSalgadinhos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaSalgadinhos.getSelectedRow() != -1) {
                int selectedRow = tabelaSalgadinhos.getSelectedRow();
                salgadinhoSelecionadoId = (int) tableModel.getValueAt(selectedRow, 0);
                carregarDadosSalgadinhoSelecionado();
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

    private void carregarSalgadinhos() {
        tableModel.setRowCount(0);
        Fila<Salgadinho> salgadinhos = Salgadinho.lerSalgadinho();
        Fila<Salgadinho> temp = new Fila<>();

        while (!salgadinhos.estaVazia()) {
            Salgadinho salgadinho = salgadinhos.desenfileirar();
            temp.enfileirar(salgadinho);
            
            Object[] rowData = {
                salgadinho.getId(),
                salgadinho.getNome(),
                salgadinho.getTipo(),
                salgadinho.getMassa(),
                salgadinho.getRecheio(),
                String.format("Mzn %.2f", salgadinho.getPreco())
            };
            tableModel.addRow(rowData);
        }

        while (!temp.estaVazia()) {
            salgadinhos.enfileirar(temp.desenfileirar());
        }
    }

    private void adicionarSalgadinho() {
        try {
            String nome = txtNome.getText().trim();
            String tipo = txtTipo.getText().trim();
            String massa = txtMassa.getText().trim();
            String recheio = txtRecheio.getText().trim();
            double preco = Double.parseDouble(txtPreco.getText().trim());

            if (nome.isEmpty() || tipo.isEmpty() || massa.isEmpty() || recheio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean sucesso = Salgadinho.criarSalgadinho(nome, tipo, massa, recheio, preco);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Salgadinho adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarSalgadinhos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarSalgadinho() {
        if (salgadinhoSelecionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um salgadinho para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String nome = txtNome.getText().trim();
            String tipo = txtTipo.getText().trim();
            String massa = txtMassa.getText().trim();
            String recheio = txtRecheio.getText().trim();
            double preco = Double.parseDouble(txtPreco.getText().trim());

            boolean sucesso = Salgadinho.editar(salgadinhoSelecionadoId, nome, tipo, massa, recheio, preco);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Salgadinho editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarSalgadinhos();
                salgadinhoSelecionadoId = -1;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarSalgadinho() {
        if (salgadinhoSelecionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um salgadinho para deletar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja deletar este salgadinho?", "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean sucesso = Salgadinho.deleteSalagadinho(salgadinhoSelecionadoId);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Salgadinho deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarSalgadinhos();
                salgadinhoSelecionadoId = -1;
            }
        }
    }

    private void buscarSalgadinho() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do salgadinho:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr.trim());
                Salgadinho salgadinho = Salgadinho.lerPizzaPorId(id);
                
                if (salgadinho.getId() != 0) {
                    txtNome.setText(salgadinho.getNome());
                    txtTipo.setText(salgadinho.getTipo());
                    txtMassa.setText(salgadinho.getMassa());
                    txtRecheio.setText(salgadinho.getRecheio());
                    txtPreco.setText(String.valueOf(salgadinho.getPreco()));
                    salgadinhoSelecionadoId = salgadinho.getId();
                } else {
                    JOptionPane.showMessageDialog(this, "Salgadinho não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarDadosSalgadinhoSelecionado() {
        int selectedRow = tabelaSalgadinhos.getSelectedRow();
        if (selectedRow != -1) {
            txtNome.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtTipo.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtMassa.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtRecheio.setText(tableModel.getValueAt(selectedRow, 4).toString());
            
            String precoStr = tableModel.getValueAt(selectedRow, 5).toString();
            precoStr = precoStr.replace("Mzn", "").trim();
            txtPreco.setText(precoStr);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtTipo.setText("");
        txtMassa.setText("");
        txtRecheio.setText("");
        txtPreco.setText("");
        salgadinhoSelecionadoId = -1;
        tabelaSalgadinhos.clearSelection();
    }
}

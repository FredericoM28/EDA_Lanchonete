package view;

//import com.formdev.flatlaf.FlatLightLaf;
import model.Salgadinho;
import model.Fila;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SalgadinhoManagementFrame extends JFrame {
    private JTextField nomeField, tipoField, massaField, recheioField, precoField, idField;
    private JTable salgadinhoTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    public SalgadinhoManagementFrame() {
        setTitle("Gerenciar Salgadinhos - Pizzaria Sabor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Gerenciar Salgadinhos", SwingConstants.CENTER);
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

        nomeField = createTextField();
        tipoField = createTextField();
        massaField = createTextField();
        recheioField = createTextField();
        precoField = createTextField();
        idField = createTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ID (para editar/deletar):"), gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Tipo (frito/assado):"), gbc);
        gbc.gridx = 1;
        formPanel.add(tipoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Massa:"), gbc);
        gbc.gridx = 1;
        formPanel.add(massaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Recheio:"), gbc);
        gbc.gridx = 1;
        formPanel.add(recheioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        formPanel.add(precoField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(createActionButton("Registrar", "🥟", e -> registerSalgadinho()));
        buttonPanel.add(createActionButton("Editar", "✏️", e -> editSalgadinho()));
        buttonPanel.add(createActionButton("Listar", "📋", e -> listSalgadinhos()));
        buttonPanel.add(createActionButton("Deletar", "🗑️", e -> deleteSalgadinho()));

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(255, 99, 71));
        gbc.gridy = 7;
        formPanel.add(statusLabel, gbc);

        String[] columns = {"ID", "Nome", "Preço", "Tipo", "Massa", "Recheio"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        salgadinhoTable = new JTable(tableModel);
        salgadinhoTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        salgadinhoTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        salgadinhoTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(salgadinhoTable);
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

    private void registerSalgadinho() {
        try {
            String nome = nomeField.getText().trim();
            String tipo = tipoField.getText().trim();
            String massa = massaField.getText().trim();
            String recheio = recheioField.getText().trim();
            double preco = Double.parseDouble(precoField.getText().trim());

            if (nome.isEmpty() || tipo.isEmpty() || massa.isEmpty() || recheio.isEmpty() || preco <= 0) {
                statusLabel.setText("Erro: Preencha todos os campos e insira um preço positivo!");
                return;
            }

            boolean criado = Salgadinho.criarSalgadinho(nome, tipo, massa, recheio, preco);
            statusLabel.setText(criado ? "Salgadinho registrado com sucesso!" : "Erro ao registrar salgadinho.");
            clearFields();
        } catch (NumberFormatException e) {
            statusLabel.setText("Erro: Preço inválido!");
        }
    }

    private void editSalgadinho() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String nome = nomeField.getText().trim();
            String tipo = tipoField.getText().trim();
            String massa = massaField.getText().trim();
            String recheio = recheioField.getText().trim();
            double preco = Double.parseDouble(precoField.getText().trim());

            if (nome.isEmpty() || tipo.isEmpty() || massa.isEmpty() || recheio.isEmpty() || preco <= 0) {
                statusLabel.setText("Erro: Preencha todos os campos e insira um preço positivo!");
                return;
            }

            boolean editado = Salgadinho.editar(id, nome, tipo, massa, recheio, preco);
            statusLabel.setText(editado ? "Salgadinho editado com sucesso!" : "Erro ao editar salgadinho.");
            clearFields();
        } catch (NumberFormatException e) {
            statusLabel.setText("Erro: ID ou preço inválido!");
        }
    }

    private void listSalgadinhos() {
        tableModel.setRowCount(0);
        Fila<Salgadinho> salgadinhos = Salgadinho.lerSalgadinho();
        Fila<Salgadinho> temp = new Fila<>();
        while (!salgadinhos.estaVazia()) {
            Salgadinho s = salgadinhos.desenfileirar();
            tableModel.addRow(new Object[]{
                    s.getId(),
                    s.getNome(),
                    s.getPreco(),
                    s.getTipo(),
                    s.getMassa(),
                    s.getRecheio()
            });
            temp.enfileirar(s);
        }
        while (!temp.estaVazia()) {
            salgadinhos.enfileirar(temp.desenfileirar());
        }
        statusLabel.setText(tableModel.getRowCount() > 0 ? "Lista atualizada." : "Nenhum salgadinho registrado.");
    }

    private void deleteSalgadinho() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            boolean deletado = Salgadinho.deleteSalagadinho(id);
            statusLabel.setText(deletado ? "Salgadinho deletado com sucesso!" : "Erro ao deletar salgadinho.");
            clearFields();
        } catch (NumberFormatException e) {
            statusLabel.setText("Erro: ID inválido!");
        }
    }

    private void clearFields() {
        nomeField.setText("");
        tipoField.setText("");
        massaField.setText("");
        recheioField.setText("");
        precoField.setText("");
        idField.setText("");
    }
}
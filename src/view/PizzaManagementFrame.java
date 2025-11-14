package view;

//import com.formdev.flatlaf.FlatLightLaf;
import model.Pizza;
import model.Fila;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PizzaManagementFrame extends JFrame {
    private JTextField nomeField, recheioField, bordaField, molhoField, precoField, idField;
    private JTable pizzaTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    public PizzaManagementFrame() {
        setTitle("Gerenciar Pizzas - Pizzaria Sabor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titleLabel = new JLabel("Gerenciar Pizzas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Painel central com formulário e tabela
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        nomeField = createTextField();
        recheioField = createTextField();
        bordaField = createTextField();
        molhoField = createTextField();
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
        formPanel.add(new JLabel("Recheio:"), gbc);
        gbc.gridx = 1;
        formPanel.add(recheioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Borda:"), gbc);
        gbc.gridx = 1;
        formPanel.add(bordaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Molho:"), gbc);
        gbc.gridx = 1;
        formPanel.add(molhoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        formPanel.add(precoField, gbc);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(createActionButton("Registrar", "🍕", e -> registerPizza()));
        buttonPanel.add(createActionButton("Editar", "✏️", e -> editPizza()));
        buttonPanel.add(createActionButton("Listar", "📋", e -> listPizzas()));
        buttonPanel.add(createActionButton("Deletar", "🗑️", e -> deletePizza()));

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Status
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setForeground(new Color(255, 99, 71));
        gbc.gridy = 7;
        formPanel.add(statusLabel, gbc);

        // Tabela
        String[] columns = {"ID", "Nome", "Preço", "Recheio", "Borda", "Molho"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        pizzaTable = new JTable(tableModel);
        pizzaTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pizzaTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        pizzaTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(pizzaTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane.setPreferredSize(new Dimension(400, 400));

        // Adiciona ao painel central
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

    private void registerPizza() {
        try {
            String nome = nomeField.getText().trim();
            String recheio = recheioField.getText().trim();
            String borda = bordaField.getText().trim();
            String molho = molhoField.getText().trim();
            double preco = Double.parseDouble(precoField.getText().trim());

            if (nome.isEmpty() || recheio.isEmpty() || borda.isEmpty() || molho.isEmpty() || preco <= 0) {
                statusLabel.setText("Erro: Preencha todos os campos e insira um preço positivo!");
                return;
            }

            boolean criado = Pizza.criarPizza(nome, recheio, borda, molho, preco);
            statusLabel.setText(criado ? "Pizza registrada com sucesso!" : "Erro ao registrar pizza.");
            clearFields();
        } catch (NumberFormatException e) {
            statusLabel.setText("Erro: Preço inválido!");
        }
    }

    private void editPizza() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String nome = nomeField.getText().trim();
            String recheio = recheioField.getText().trim();
            String borda = bordaField.getText().trim();
            String molho = molhoField.getText().trim();
            double preco = Double.parseDouble(precoField.getText().trim());

            if (nome.isEmpty() || recheio.isEmpty() || borda.isEmpty() || molho.isEmpty() || preco <= 0) {
                statusLabel.setText("Erro: Preencha todos os campos e insira um preço positivo!");
                return;
            }

            boolean editado = Pizza.editarPorId(id, nome, recheio, borda, molho, preco);
            statusLabel.setText(editado ? "Pizza editada com sucesso!" : "Erro ao editar pizza.");
            clearFields();
        } catch (NumberFormatException e) {
            statusLabel.setText("Erro: ID ou preço inválido!");
        }
    }

    private void listPizzas() {
        tableModel.setRowCount(0); // Limpa a tabela
        Fila<Pizza> pizzas = Pizza.lerPizza();
        Fila<Pizza> temp = new Fila<>();
        while (!pizzas.estaVazia()) {
            Pizza p = pizzas.desenfileirar();
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getPreco(),
                    p.getRecheio(),
                    p.getBorda(),
                    p.getMolho()
            });
            temp.enfileirar(p);
        }
        while (!temp.estaVazia()) {
            pizzas.enfileirar(temp.desenfileirar());
        }
        statusLabel.setText(tableModel.getRowCount() > 0 ? "Lista atualizada." : "Nenhuma pizza registrada.");
    }

    private void deletePizza() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            boolean deletado = Pizza.deletePizza(id);
            statusLabel.setText(deletado ? "Pizza deletada com sucesso!" : "Erro ao deletar pizza.");
            clearFields();
        } catch (NumberFormatException e) {
            statusLabel.setText("Erro: ID inválido!");
        }
    }

    private void clearFields() {
        nomeField.setText("");
        recheioField.setText("");
        bordaField.setText("");
        molhoField.setText("");
        precoField.setText("");
        idField.setText("");
    }
}
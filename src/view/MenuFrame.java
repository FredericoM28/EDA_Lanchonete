/*
 * Pacote para as views (telas) do sistema.
 */
package view;

//import com.formdev.flatlaf.FlatLightLaf; // Biblioteca para um visual moderno
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.Pizza;
import model.Salgadinho;
import model.Venda;
import model.ItemVenda;
import model.Fila;
import model.Produto;

/**
 * Classe principal para iniciar o sistema com FlatLaf.
 */


/**
 * Tela de Login com design moderno.
 */
class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login - Pizzaria Sabor");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel com gradiente de fundo
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 99, 71), 0, getHeight(), new Color(255, 165, 0));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel central com fundo branco e bordas arredondadas
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        loginPanel.setOpaque(true);
        loginPanel.setPreferredSize(new Dimension(300, 350));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo fictício (substitua por uma imagem real se desejar)
        JLabel logoLabel = new JLabel("🍕 Pizzaria Sabor", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logoLabel.setForeground(new Color(255, 99, 71));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(logoLabel, gbc);

        // Campo de usuário
        JLabel usernameLabel = new JLabel("Usuário:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gbc.gridy = 2;
        loginPanel.add(usernameField, gbc);

        // Campo de senha
        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 3;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gbc.gridy = 4;
        loginPanel.add(passwordField, gbc);

        // Botão de login
        JButton loginButton = new JButton("Entrar");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(new Color(255, 99, 71));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(255, 140, 0));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(255, 99, 71));
            }
        });

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("admin") && password.equals("123456")) {
                JOptionPane.showMessageDialog(null, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                MenuFrame menu = new MenuFrame();
                menu.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Credenciais inválidas!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        loginPanel.add(loginButton, gbc);

        mainPanel.add(loginPanel, BorderLayout.CENTER);
        add(mainPanel);
    }
}

/**
 * Tela de Menu Principal com design moderno.
 */
public class MenuFrame extends JFrame {
    public MenuFrame() {
        setTitle("Menu - Pizzaria Sabor");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Sistema de Gerenciamento", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton gerirPizzasButton = createStyledButton("Gerir Pizzas", "🍕");
        JButton gerirSalgadinhosButton = createStyledButton("Gerir Salgadinhos", "🥟");
        JButton realizarVendasButton = createStyledButton("Gerir Vendas", "💰");
        JButton sairButton = createStyledButton("Sair", "🚪");

        gerirPizzasButton.addActionListener(e -> new PizzaManagementFrame().setVisible(true));
        gerirSalgadinhosButton.addActionListener(e -> new SalgadinhoManagementFrame().setVisible(true));
        realizarVendasButton.addActionListener(e -> new VendaManagementFrame().setVisible(true));
        sairButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(gerirPizzasButton);
        buttonPanel.add(gerirSalgadinhosButton);
        buttonPanel.add(realizarVendasButton);
        buttonPanel.add(sairButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton createStyledButton(String text, String icon) {
        JButton button = new JButton(icon + " " + text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(50, 50, 50));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(255, 99, 71));
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(new Color(50, 50, 50));
            }
        });

        return button;
    }
}
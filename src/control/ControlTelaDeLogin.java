/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import model.Usuario;
import view.FrameLogin;
import view.TelaDeMenuHome;

/**
 *
 * @author HP
 */
public class ControlTelaDeLogin {

    private final FrameLogin view;

    public ControlTelaDeLogin(FrameLogin view) {
        this.view = view;
    }
    //Metodo para validacao de campos

    public void realizarLogin() {
        try {
            // Verifica se a view foi injetada corretamente
            if (view == null) {
                JOptionPane.showMessageDialog(null, "Erro: View não inicializada");
                return;
            }
            // Obtém e valida os valores dos campos
            String usuario = view.getTfNomeDeUsuario().getText().trim();
            String senha = new String(view.getPfSenha().getPassword());

            // Validação rigorosa dos campos
            if (!validarCampos(usuario, senha)) {
                return;
            }

            // Tenta autenticar o usuário
            if (autenticarUsuario(usuario, senha)) {
                // Login bem-sucedido - abre menu principal
                JOptionPane.showMessageDialog(null, "Login, Bem Sucedido");
                Usuario usuarioLogado = Usuario.usuarioBuscarPorNome(usuario);

                switch (usuarioLogado.getNivelDeAcesso()) {
                    case "Admin" ->
                        abrirTelaMenuHome();
                    default ->

                        JOptionPane.showMessageDialog(null, "Nível de acesso não reconhecido");
                }
            } else {
                // Login falhou - feedback ao usuário
                tratarFalhaLogin();
            }
        } catch (Exception ex) {
            tratarErroInesperado(ex);
        }
    }

    public void limparCamposTelaLogin() {
        view.getTfNomeDeUsuario().setText("");
        view.getTfNomeDeUsuario().setText("");
    }

    public boolean validarCampos(String usuario, String senha) {
        if (usuario == null || usuario.trim().isEmpty()) {
            view.getErroSenhaDoUsuario().setText("Preencha o nome de usuário");
            view.getTfNomeDeUsuario().requestFocus();
            return false;
        }

        if (senha == null || senha.trim().isEmpty()) {
            view.getErroSenhaDoUsuario().setText("Preencha a senha");
            view.getPfSenha().requestFocus();
            return false;
        }

        // Validação de comprimento mínimo (opcional)
        if (usuario.trim().length() < 3) {
            view.getErroSenhaDoUsuario().setText("Usuário deve ter pelo menos 3 caracteres");
            view.getTfNomeDeUsuario().requestFocus();
            return false;
        }

        view.getErroSenhaDoUsuario().setText(""); // Limpa mensagens de erro
        return true;
    }

    public boolean autenticarUsuario(String usuario, String senha) {
        try {
            return Usuario.login(usuario, senha);
        } catch (Exception ex) {
            view.getErroSenhaDoUsuario().setText("Erro durante autenticação");
            System.err.println("Erro na autenticação: " + ex.getMessage());
            return false;
        }
    }

    public void abrirTelaMenuHome() {
        // Fecha a tela de login
        view.dispose();

        // Abre o menu principal na thread de eventos
        SwingUtilities.invokeLater(() -> {
            TelaDeMenuHome telaDeMenuHome = new TelaDeMenuHome();

            // Configurações adicionais da janela
            telaDeMenuHome.setLocationRelativeTo(null); // Centraliza
            //telaDeMenuHome.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza (opcional)

            telaDeMenuHome.setVisible(true);

            // Foca na nova janela
            telaDeMenuHome.toFront();
        });
    }

    public void tratarFalhaLogin() {
        view.getErroSenhaDoUsuario().setText("Usuário ou Senha incorretos");

        // Limpa a senha e prepara para nova tentativa
        view.getPfSenha().setText("");
        view.getTfNomeDeUsuario().setText("");
        view.getTfNomeDeUsuario().selectAll();
        view.getTfNomeDeUsuario().requestFocus();

        // Efeito visual de erro 
        view.getTfNomeDeUsuario().setBackground(new Color(255, 230, 230));
        view.getPfSenha().setBackground(new Color(255, 230, 230));

        // Timer para remover o fundo colorido após 2 segundos
        new Timer(2000, e -> {
            view.getTfNomeDeUsuario().setBackground(Color.WHITE);
            view.getPfSenha().setBackground(Color.WHITE);
        }).start();
    }

    public void tratarErroInesperado(Exception ex) {
        view.getErroSenhaDoUsuario().setText("Erro inesperado. Tente novamente.");

        // Log do erro
        System.err.println("Erro inesperado no login: " + ex.getMessage());
        ex.printStackTrace();

        // Restaura o foco para tentar novamente
        view.getTfNomeDeUsuario().requestFocus();
    }

    public void entrarUsandoEnter(java.awt.event.KeyEvent evt) {

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            realizarLogin();
        }
    }

    public void mudarCampos(KeyEvent evt) {
        int codigo = evt.getKeyCode();

        if (codigo == KeyEvent.VK_ENTER) {
            realizarLogin();
        }

    }
}

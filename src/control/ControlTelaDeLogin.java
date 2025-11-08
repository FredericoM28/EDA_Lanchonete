///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package control;
//
//import java.awt.Color;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.SwingUtilities;
//import javax.swing.Timer;
//import view.FrameLogin;
//
//
///**
// *
// * @author HP
// */
//public class ControlTelaDeLogin {
//
//    private final FrameLogin view;
//
//    public ControlTelaDeLogin(FrameLogin view) {
//        this.view = view;
//    }
//    //Metodo para validacao de campos
//
//    public void realizarLogin() {
//        try {
//            // Verifica se a view foi injetada corretamente
//            if (view == null) {
//                JOptionPane.showMessageDialog(null, "Erro: View não inicializada");
//                return;
//            }
//            // Obtém e valida os valores dos campos
//            String usuario = view.getTfNomeDeUsuario().getText().trim();
//            String senha = new String(view.getPfSenha().getPassword());
//
//            // Validação rigorosa dos campos
//            if (!validarCampos(usuario, senha)) {
//                return;
//            }
//
//            // Tenta autenticar o usuário
//            if (autenticarUsuario(usuario, senha)) {
//                // Login bem-sucedido - abre menu principal
//              Usuario usuarioLogado = usuariodao.buscarPorNome(usuario);
//
//                if (!usuarioLogado.getStatus()) {
//                    JOptionPane.showMessageDialog(null, "A sua conta está desativada, contacte o Administrador", "Conta Desativada", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//                JOptionPane.showMessageDialog(null, "Login, Bem Sucedido");
//                switch (usuarioLogado.getNivelDeAcesso()) {
//                    case "Administrador" ->
//                        abrirTelaAdministrador(usuarioLogado);
//                    case "Gerente" ->
//                        abrirTelaDeGerente(usuarioLogado);
//                    case "Vendedor" ->
//                        abrirTelaVendedor(usuarioLogado);
//                    default ->
//
//                        JOptionPane.showMessageDialog(null, "Nível de acesso não reconhecido");
//                }
//            } else {
//                // Login falhou - feedback ao usuário
//                tratarFalhaLogin();
//            }
//        } catch (Exception ex) {
//            tratarErroInesperado(ex);
//        }
//    }
//
//    public void limparCamposTelaLogin() {
//        view.getTfNomeDeUsuario().setText("");
//        view.getTfNomeDeUsuario().setText("");
//    }
//
//    public boolean validarCampos(String usuario, String senha) {
//        if (usuario == null || usuario.trim().isEmpty()) {
//            view.getErroSenhaDoUsuario().setText("Preencha o nome de usuário");
//            view.getTfNomeDeUsuario().requestFocus();
//            return false;
//        }
//
//        if (senha == null || senha.trim().isEmpty()) {
//            view.getErroSenhaDoUsuario().setText("Preencha a senha");
//            view.getPfSenha().requestFocus();
//            return false;
//        }
//
//        // Validação de comprimento mínimo (opcional)
//        if (usuario.trim().length() < 3) {
//            view.getErroSenhaDoUsuario().setText("Usuário deve ter pelo menos 3 caracteres");
//            view.getTfNomeDeUsuario().requestFocus();
//            return false;
//        }
//
//        view.getErroSenhaDoUsuario().setText(""); // Limpa mensagens de erro
//        return true;
//    }
//
//    public boolean autenticarUsuario(String usuario, String senha) {
//        try {
//            return Usuario.login(usuario.trim(), senha);
//        } catch (Exception ex) {
//            view.getErroSenhaDoUsuario().setText("Erro durante autenticação");
//            System.err.println("Erro na autenticação: " + ex.getMessage());
//            return false;
//        }
//    }
//
//    public void abrirTelaDeGerente(Usuario usuarioLogado) {
//        // Fecha a tela de login
//        view.dispose();
//
//        // Abre o menu principal na thread de eventos
//        SwingUtilities.invokeLater(() -> {
//            TelaDeGerente telaDeGerente = new TelaDeGerente(usuarioLogado);
//
//            // Configurações adicionais da janela
//            telaDeGerente.setLocationRelativeTo(null); // Centraliza
//            telaDeGerente.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza (opcional)
//            telaDeGerente.setVisible(true);
//
//            // Foca na nova janela
//            telaDeGerente.toFront();
//        });
//    }
//
//    public void abrirTelaVendedor(Usuario usuarioLogado) {
//        // Fecha a tela de login
//        view.dispose();
//
//        // Abre o menu principal na thread de eventos
//        SwingUtilities.invokeLater(() -> {
//            TelaDeVendedor telaDeVendedor = new TelaDeVendedor(usuarioLogado);
//
//            // Configurações adicionais da janela
//            telaDeVendedor.setLocationRelativeTo(null); // Centraliza
//            telaDeVendedor.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza (opcional)
//            telaDeVendedor.setVisible(true);
//
//            // Foca na nova janela
//            telaDeVendedor.toFront();
//        });
//    }
//
//    public void abrirTelaAdministrador(Usuario usuarioLogado) {
//        // Fecha a tela de login
//        view.dispose();
//
//        // Abre o menu principal na thread de eventos
//        SwingUtilities.invokeLater(() -> {
//            TelaDeAdministrador telaDeAdministrador = new TelaDeAdministrador(usuarioLogado);
//
//            // Configurações adicionais da janela
//            telaDeAdministrador.setLocationRelativeTo(null); // Centraliza
//            telaDeAdministrador.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza (opcional)
//            telaDeAdministrador.setVisible(true);
//
//            // Foca na nova janela
//            telaDeAdministrador.toFront();
//        });
//    }
//
//    public void tratarFalhaLogin() {
//        view.getErroSenhaDoUsuario().setText("Usuário ou Senha incorretos");
//
//        // Limpa a senha e prepara para nova tentativa
//        view.getPfSenha().setText("");
//        view.getTfNomeDeUsuario().setText("");
//        view.getTfNomeDeUsuario().selectAll();
//        view.getTfNomeDeUsuario().requestFocus();
//
//        // Efeito visual de erro 
//        view.getTfNomeDeUsuario().setBackground(new Color(255, 230, 230));
//        view.getPfSenha().setBackground(new Color(255, 230, 230));
//
//        // Timer para remover o fundo colorido após 2 segundos
//        new Timer(2000, e -> {
//            view.getTfNomeDeUsuario().setBackground(Color.WHITE);
//            view.getPfSenha().setBackground(Color.WHITE);
//        }).start();
//    }
//
//    public void tratarErroInesperado(Exception ex) {
//        view.getErroSenhaDoUsuario().setText("Erro inesperado. Tente novamente.");
//
//        // Log do erro
//        System.err.println("Erro inesperado no login: " + ex.getMessage());
//        ex.printStackTrace();
//
//        // Restaura o foco para tentar novamente
//        view.getTfNomeDeUsuario().requestFocus();
//    }
//
//    public void entrarUsandoEnter(java.awt.event.KeyEvent evt) {
//
//         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//                realizarLogin();
//            }
//    }
//    
//    
//    public void mudarCampos(KeyEvent evt){
//        int codigo= evt.getKeyCode();
//        
//        if(codigo==KeyEvent.VK_ENTER){
//            realizarLogin();
//        }
//        
//    
//    }
//}

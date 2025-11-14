package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class Usuario implements Serializable {

    private int id;
    private String nome;
    private String senha;
    private String nivelDeAcesso;
    private boolean status;
    private static final long serialVersionUID = 1L;

    //Criacão do método construtor da classe Usuario
    public Usuario() {
    }

    public Usuario(int id, String nome, String senha, String nivelDeAcesso) {
        this.id = id;

        this.nome = nome;
        this.senha = senha;
        this.nivelDeAcesso = nivelDeAcesso;
        this.status = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNivelDeAcesso() {
        return nivelDeAcesso;
    }

    public void setNivelDeAcesso(String nivelDeAcesso) {
        this.nivelDeAcesso = nivelDeAcesso;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static Boolean CriarUsuario(String nome, String senha, String nivelDeAcesso) {
        Boolean res = false;
        if (usuarioJaExiste(nome)) {
            JOptionPane.showMessageDialog(null, "Ja existe um usuario com esse nome");
        } else {
            if (!nivelDeAcesso.equals("Admin") && !nivelDeAcesso.equals("Gerente") && !nivelDeAcesso.equals("Vendedor")) {

            } else {
                Usuario u1 = new Usuario(icrementarId(), nome, senha, nivelDeAcesso);
                Usuario.gravarUsuario(u1);
                JOptionPane.showMessageDialog(null, "Usuario registado com sucesso");
            }
        }
        return res;
    }

    public static Boolean editarUsuario(Usuario usuario) {
        ArrayList<Usuario> listaDeUsuario; // apenas uma leitura

        listaDeUsuario = lerUsuario();
        int id = usuario.getId();
        for (Usuario us : listaDeUsuario) {
            if (usuario.getId() == id) {
                us.setAll(usuario.getNome(), usuario.getSenha(), usuario.getNivelDeAcesso());
                break;

            }
        }
        try {
            FileOutputStream meuFicheiro = new FileOutputStream("UsuarioFile.dat");
            ObjectOutputStream os = new ObjectOutputStream(meuFicheiro);

            os.writeObject(listaDeUsuario);

            os.close();
            meuFicheiro.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean usuarioJaExiste(String nome) {
        ArrayList<Usuario> listaDeUsuario = lerUsuario();

        Boolean res = false;
        for (Usuario usuario : listaDeUsuario) {
            if (usuario.getNome().equals(nome)) {
                res = true;
                break;
            }
        }
        return res;

    }

    public static Usuario usuarioBuscarPorNome(String nome) {
        ArrayList<Usuario> listaDeUsuario = lerUsuario();

        Usuario usarioLogado = null;
        for (Usuario usuario : listaDeUsuario) {
            if (usuario.getNome().equals(nome)) {
                usarioLogado = usuario;

            } else {

                JOptionPane.showMessageDialog(null, "Usuário não encotrado");
            }
        }

        return usarioLogado;
    }

    //Metodo incrementar Id
    public static int icrementarId() {
        ArrayList<Usuario> usuarioLista;
        usuarioLista = lerUsuario();

        int id = 0;
        for (Usuario usuario : usuarioLista) {
            id++;
        }

        return id;
    }

    public static int totalUsuarios() {
        int id = 0;
        ArrayList<Usuario> listaDeUsuario = lerUsuario();

        for (Usuario usuario : listaDeUsuario) {
            if (usuario.getStatus() == true) {
                id++;
            }
        }
        return id;

    }

    //Metodo para editar
    public void setAll(String nome, String senha, String nivelDeAcesso) {
        if ((nivelDeAcesso.equals("Admin") || "Vendedor".equals(nivelDeAcesso) || "Gerente".equals(nivelDeAcesso)) && this.getStatus() == true) {

            this.setSenha(senha);
            this.setNivelDeAcesso(nivelDeAcesso);
            this.setStatus(status);
        } else {
        }
    }

    public static Boolean gravarUsuario(Usuario usuario) {
        ArrayList<Usuario> lista = lerUsuario(); // apenas uma leitura

        if (lista == null) {
            lista = new ArrayList<>();
        }

        lista.add(usuario);

        try (FileOutputStream meuFicheiro = new FileOutputStream("UsuarioFile.dat"); ObjectOutputStream os = new ObjectOutputStream(meuFicheiro)) {

            os.writeObject(lista);
            return true;

        } catch (IOException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<Usuario> lerUsuario() {

        File ficheiro = new File("UsuarioFile.dat");

        if (!ficheiro.exists() || ficheiro.length() == 0) {
            return new ArrayList<>(); // ficheiro não existe ou está vazio
        }

        try (FileInputStream meuFicheiro = new FileInputStream(ficheiro); ObjectInputStream in = new ObjectInputStream(meuFicheiro)) {

            return (ArrayList<Usuario>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro na leitura: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static boolean login(String nome, String senha) {
        boolean isValid=false;
        ArrayList<Usuario> listaDeUsuarios = lerUsuario();
        for (Usuario p : listaDeUsuarios) {
            if (p.getNome().equals(nome) && p.getSenha().equals(senha) && p.getStatus() == true) {
                isValid=true;
              
            }
        }
        return isValid; // Retorna null se não encontrar
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nome=" + nome + ", senha=" + senha + ", nivelDeAcesso=" + nivelDeAcesso + ", status=" + status + '}';
    }

}

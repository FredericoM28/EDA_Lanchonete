/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Déleo Cambula
 */
public class Salgadinho implements Serializable {

    private String tipo;     // frito ou assado
    private String massa;
    private String recheio;
    private double preco;
    private int idSalgadinho;
    private String nomeSalgado;
    private static final long serialVersionUID = 1L;

    public int getIdSalgadinho() {
        return idSalgadinho;
    }

    public void setIdSalgadinho(int idSalgadinho) {
        this.idSalgadinho = idSalgadinho;
    }

    public String getNomeSalgado() {
        return nomeSalgado;
    }

    public void setNomeSalgado(String nomeSalgado) {
        this.nomeSalgado = nomeSalgado;
    }

    // Construtor
    public Salgadinho(String nomeDoSalgado, String tipo, String massa, String recheio, double preco, int idSalgadinho) {
        this.nomeSalgado = nomeDoSalgado;
        this.tipo = tipo;
        this.massa = massa;
        this.recheio = recheio;
        this.preco = preco;
        this.idSalgadinho = idSalgadinho++;
    }

    public Salgadinho() {
    }
    
    public static Boolean criarSalgadinho(String nomeDoSalgado, String tipo, String massa, String recheio, double preco){
        Salgadinho novoSalgadinho = new Salgadinho(nomeDoSalgado, tipo, massa, recheio, preco, incrementarId());
        
        return gravarSalgadinho(novoSalgadinho);
    }
    // metodo para incrementar id do salgadinho
    private static int incrementarId() {
        return lerSalgadinho().tamanho() + 1;
    }

    // Getters e Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMassa() {
        return massa;
    }

    public void setMassa(String massa) {
        this.massa = massa;
    }

    public String getRecheio() {
        return recheio;
    }

    public void setRecheio(String recheio) {
        this.recheio = recheio;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getId() {
        return idSalgadinho;
    }

    public void setId(int idSalgadinho) {
        this.idSalgadinho = idSalgadinho;
    }

    private static Boolean gravarSalgadinho(Salgadinho salgado) {
        Fila<Salgadinho> lista = lerSalgadinho();

        if (lista == null) {
            lista = new Fila();
        }

        lista.enfileirar(salgado);

        try (FileOutputStream meuFicheiro = new FileOutputStream("Salgadinho.dat"); ObjectOutputStream os = new ObjectOutputStream(meuFicheiro)) {

            os.writeObject(lista);
            return true;

        } catch (IOException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
            return false;
        }
    }

    public static Fila<Salgadinho> lerSalgadinho() {
        File ficheiro = new File("Salgadinho.dat");

        if (!ficheiro.exists() || ficheiro.length() == 0) {
            return new Fila(); // ficheiro não existe ou está vazio
        }

        try (FileInputStream meuFicheiro = new FileInputStream(ficheiro); ObjectInputStream in = new ObjectInputStream(meuFicheiro)) {

            return (Fila<Salgadinho>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro na leitura: " + e.getMessage());
            return new Fila();
        }
    }

    public static boolean deleteSalagadinho(int id) {
        Fila<Salgadinho> fila = lerSalgadinho();
        Fila<Salgadinho> auxiliar = new Fila<>();

        while (!fila.estaVazia()) {
            Salgadinho atual = fila.desenfileirar();

            if (atual.getId() == id) {
                auxiliar.enfileirar(atual);
            }
        }

        while (!auxiliar.estaVazia()) {
            fila.enfileirar(auxiliar.desenfileirar());
        }

        try {
            FileOutputStream meuFicheiro = new FileOutputStream("Salgadinho.dat");
            ObjectOutputStream os = new ObjectOutputStream(meuFicheiro);

            os.writeObject(fila);

            os.close();
            meuFicheiro.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean editarPorId(int id, Salgadinho novoSalgadinho) {
        Fila<Salgadinho> auxiliar = new Fila<>();
        Fila<Salgadinho> fila = lerSalgadinho();

        while (!fila.estaVazia()) {
            Salgadinho atual = fila.desenfileirar();

            if (atual.getId() == id) {
                auxiliar.enfileirar(novoSalgadinho);
            } else {
                auxiliar.enfileirar(atual);
            }
        }

        while (!auxiliar.estaVazia()) {
            fila.enfileirar(auxiliar.desenfileirar());
        }

        try {
            FileOutputStream meuFicheiro = new FileOutputStream("Salgadinho.dat");
            ObjectOutputStream os = new ObjectOutputStream(meuFicheiro);

            os.writeObject(fila);

            os.close();
            meuFicheiro.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Fila<Salgadinho> ordenarPorPreco(Fila<Salgadinho> fila) {
        Fila<Salgadinho> ordenada = new Fila<>();

        while (!fila.estaVazia()) {
            Salgadinho menor = fila.desenfileirar();

            Fila<Salgadinho> auxiliar = new Fila<>();

            while (!fila.estaVazia()) {
                Salgadinho atual = fila.desenfileirar();
                if (atual.getPreco() < menor.getPreco()) {
                    auxiliar.enfileirar(menor);
                    menor = atual;
                } else {
                    auxiliar.enfileirar(atual);
                }
            }

            ordenada.enfileirar(menor);

            while (!auxiliar.estaVazia()) {
                fila.enfileirar(auxiliar.desenfileirar());
            }
        }

        while (!ordenada.estaVazia()) {
            fila.enfileirar(ordenada.desenfileirar());
        }
        
        return fila;
    }

    public static Salgadinho lerPizzaPorId(int id) {
        Fila<Salgadinho> fila = lerSalgadinho();

        while (!fila.estaVazia()) {
            Salgadinho atual = fila.desenfileirar();

            if (atual.getId() == id) {
                return atual;
            }
        }

        return new Salgadinho();
    }

    @Override
    public String toString() {
        return "Salgadinho{"
                + "tipo='" + tipo + '\''
                + ", massa='" + massa + '\''
                + ", recheio='" + recheio + '\''
                + ", preco=" + preco
                + '}';
    }
}

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

    // Construtor
    public Salgadinho(String tipo, String massa, String recheio, double preco, int idSalgadinho) {
        this.tipo = tipo;
        this.massa = massa;
        this.recheio = recheio;
        this.preco = preco;
        this.idSalgadinho = idSalgadinho++;
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
    
    public static Boolean gravarSalgadinho(Salgadinho salgado) {
        Fila<Salgadinho> lista = lerSalgadinho();

        if (lista == null) {
            lista = new Fila();
        }

        lista.enfileirar(salgado);

        try (FileOutputStream meuFicheiro = new FileOutputStream("Salgadinho.dat");
            ObjectOutputStream os = new ObjectOutputStream(meuFicheiro)) {

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

        try (FileInputStream meuFicheiro = new FileInputStream(ficheiro);
             ObjectInputStream in = new ObjectInputStream(meuFicheiro)) {

            return (Fila<Salgadinho>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro na leitura: " + e.getMessage());
            return new Fila();
        }
    }
    // Representação textual (para exibir facilmente)
    @Override
    public String toString() {
        return "Salgadinho{" +
                "tipo='" + tipo + '\'' +
                ", massa='" + massa + '\'' +
                ", recheio='" + recheio + '\'' +
                ", preco=" + preco +
                '}';
    }
}


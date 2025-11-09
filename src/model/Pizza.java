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
public class Pizza implements Serializable {
    private String recheio;
    private String borda;   // pode ser "recheada" ou "normal"
    private String molho;
    private double preco;
    private int idPizza;
    private String nomePizza;

    public int getIdPizza() {
        return idPizza;
    }

    public void setIdPizza(int idPizza) {
        this.idPizza = idPizza;
    }

    public String getNomePizza() {
        return nomePizza;
    }

    public void setNomePizza(String nomePizza) {
        this.nomePizza = nomePizza;
    }

    public Pizza(String recheio, String borda, String molho, double preco) {
        this.recheio = recheio;
        this.borda = borda;
        this.molho = molho;
        this.preco = preco;
        this.idPizza = idPizza++;
    }
    
    public Pizza() {
    }

    //metodo para incrementar id da pizza
    public int incrementarId(){
        return lerPizza().tamanho() + 1;  
    }    
    // Getters e Setters
    public String getRecheio() {
        return recheio;
    }

    public void setRecheio(String recheio) {
        this.recheio = recheio;
    }

    public String getBorda() {
        return borda;
    }

    public void setBorda(String borda) {
        this.borda = borda;
    }

    public String getMolho() {
        return molho;
    }

    public void setMolho(String molho) {
        this.molho = molho;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public int getId() {
        return idPizza;
    }

    public void setId() {
        this.idPizza = idPizza;
    }
        
    public static Boolean gravarPizza(Pizza pizza) {
        Fila<Pizza> lista = lerPizza();

        if (lista == null) {
            lista = new Fila();
        }

        lista.enfileirar(pizza);

        try (FileOutputStream meuFicheiro = new FileOutputStream("Pizza.dat");
            ObjectOutputStream os = new ObjectOutputStream(meuFicheiro)) {

            os.writeObject(lista);
            return true;

        } catch (IOException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
            return false;
        }
    }
    
    public static Fila<Pizza> lerPizza() {
        File ficheiro = new File("Pizza.dat");

        if (!ficheiro.exists() || ficheiro.length() == 0) {
            return new Fila(); // ficheiro não existe ou está vazio
        }

        try (FileInputStream meuFicheiro = new FileInputStream(ficheiro);
             ObjectInputStream in = new ObjectInputStream(meuFicheiro)) {

            return (Fila<Pizza>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro na leitura: " + e.getMessage());
            return new Fila();
        }
    }
    
    public static boolean deletePizza(int id){
        Fila<Pizza> fila = lerPizza();
        Fila<Pizza> auxiliar = new Fila<>();
        
        while (!fila.estaVazia()) {
            Pizza atual = fila.desenfileirar();

            if (atual.getId() == id) {
                auxiliar.enfileirar(atual);
            }
        }
        
        while (!auxiliar.estaVazia()) {
            fila.enfileirar(auxiliar.desenfileirar());
        }

        try {
            FileOutputStream meuFicheiro = new FileOutputStream("Pizza.dat");
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
    
    public static boolean editarPorId( int id, Pizza novaPizza) {
        Fila<Pizza> auxiliar = new Fila<>();
        Fila<Pizza> fila = lerPizza();

        while (!fila.estaVazia()) {
            Pizza atual = fila.desenfileirar();

            if (atual.getId() == id) {
                auxiliar.enfileirar(novaPizza);
            } else {
                auxiliar.enfileirar(atual);
            }
        }

        while (!auxiliar.estaVazia()) {
            fila.enfileirar(auxiliar.desenfileirar());
        }

        try {
            FileOutputStream meuFicheiro = new FileOutputStream("Pizza.dat");
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
    
    public static void ordenarPorPreco(Fila<Pizza> fila) {
        Fila<Pizza> ordenada = new Fila<>();

        while (!fila.estaVazia()) {
            Pizza menor = fila.desenfileirar();

            Fila<Pizza> auxiliar = new Fila<>();

            while (!fila.estaVazia()) {
                Pizza atual = fila.desenfileirar();
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
    }
    
    public static Pizza lerPizzaPorId(int id){
        Fila<Pizza> fila = lerPizza();

        while (!fila.estaVazia()) {
            Pizza atual = fila.desenfileirar();

            if (atual.getId() == id) {
                return atual;
            }
        }
        
        return new Pizza();
    }
        
   
    @Override
    public String toString() {
        return "Pizza{" +
                "recheio='" + recheio + '\'' +
                ", borda='" + borda + '\'' +
                ", molho='" + molho + '\'' +
                ", preco=" + preco +
                '}';
    }
}

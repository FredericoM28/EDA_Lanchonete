/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import static model.Pizza.lerPizza;
import static model.Salgadinho.lerSalgadinho;

/**
 *
 * @author Déleo Cambula
 */
public class Produto implements Serializable{
    private int id;
    private String nome;
    private double preco;
    private static final long serialVersionUID = 1L;
    
    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }
    
    protected static int incrementarId() {
        Fila<Pizza> fila = lerPizza();
        Fila<Salgadinho> fila2 = Salgadinho.lerSalgadinho();
        return fila.tamanho() + fila2.tamanho() + 1;
    }

    public Produto(){}

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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }   
    
public static Produto lerProdutoPorId(int id) {
    // Ler os salgadinhos
    Fila<Salgadinho> filaSalgadinhos = lerSalgadinho();

    while (!filaSalgadinhos.estaVazia()) {
        Salgadinho atual = filaSalgadinhos.desenfileirar();
        if (atual.getId() == id) {
            return atual;
        }
    }

    // Ler as pizzas
    Fila<Pizza> filaPizzas = lerPizza();

    while (!filaPizzas.estaVazia()) {
        Pizza atual = filaPizzas.desenfileirar();
        if (atual.getId() == id) {
            return atual;
        }
    }

    // Se não encontrar nenhum produto
    return null;
}

}

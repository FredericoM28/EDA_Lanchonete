/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Déleo Cambula
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.io.Serializable;

public class Venda implements Serializable{
    private  Fila<itemVenda> itemVenda = new Fila();
    private float valorTotal; //Valor a ser pago
    private float valorRecebido;//Valor entregue pelo cliente
    private LocalDate dataDeVenda;
    private static final long serialVersionUID = 1L;
    public int idVenda;
    
    //Criacao do metodo Construtor da classe venda
    public Venda(Fila<itemVenda> itemVenda, float valorTotal, float valorRecebido, float troco) {
        this.itemVenda = itemVenda;
        this.valorTotal = valorTotal;
        this.dataDeVenda = LocalDate.now();
        this.valorRecebido = valorRecebido;
        this.idVenda = idVenda++;
        
    }

    public Fila<itemVenda> getItemVenda() {
        return itemVenda;
    }

    public void setItemVenda(Fila<itemVenda> itemVenda) {
        this.itemVenda = itemVenda;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public float getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(float valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public LocalDate getDataDeVenda() {
        return dataDeVenda;
    }

    public void setDataDeVenda(LocalDate dataDeVenda) {
        this.dataDeVenda = dataDeVenda;
    }
    
    
    public static Boolean gravarVenda(Venda venda) {
        Fila<Venda> lista = lerVenda();

        if (lista == null) {
            lista = new Fila();
        }

        lista.enfileirar(venda);

        try (FileOutputStream meuFicheiro = new FileOutputStream("Venda.dat");
            ObjectOutputStream os = new ObjectOutputStream(meuFicheiro)) {

            os.writeObject(lista);
            return true;

        } catch (IOException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
            return false;
        }
    }
    
    public static Fila<Venda> lerVenda() {
        File ficheiro = new File("venda.dat");

        if (!ficheiro.exists() || ficheiro.length() == 0) {
            return new Fila(); // ficheiro não existe ou está vazio
        }

        try (FileInputStream meuFicheiro = new FileInputStream(ficheiro);
             ObjectInputStream in = new ObjectInputStream(meuFicheiro)) {

            return (Fila<Venda>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro na leitura: " + e.getMessage());
            return new Fila();
        }
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }
}


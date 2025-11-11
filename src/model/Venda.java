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
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import static model.Pizza.lerPizza;

public class Venda implements Serializable{
    private  Fila<ItemVenda> itemVenda = new Fila();
    private float valorTotal; //Valor a ser pago
    private float valorRecebido;//Valor entregue pelo cliente
    private LocalDate dataDeVenda;
    private static final long serialVersionUID = 1L;
    public int idVenda;
    
    //Criacao do metodo Construtor da classe venda
    public Venda(Fila<ItemVenda> itemVenda, float valorTotal, LocalDate dataDeVenda1, float valorRecebido, int id) {
        this.itemVenda = itemVenda;
        this.valorTotal = valorTotal;
        this.dataDeVenda = LocalDate.now();
        this.valorRecebido = valorRecebido;
        this.idVenda = id;
        
    }

    public Fila<ItemVenda> getItemVenda() {
        return itemVenda;
    }

    public void setItemVenda(Fila<ItemVenda> itemVenda) {
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
    
    /// Metodo que cria uma venda
    public static boolean criarVenda(Fila<ItemVenda> itemVenda, LocalDate dataDeVenda,float valorRecebido){
       float valorTotal = ItemVenda.precoTotal(itemVenda);
   
       Venda novaVenda = new Venda(itemVenda, valorTotal, dataDeVenda, valorRecebido, incrementarId());
       
       return gravarVenda(novaVenda);
    }
    
    private static int incrementarId() {
        Fila<Venda> vendas = lerVenda();
        return vendas.tamanho() + 1;
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

     // Registrar nova venda
    public boolean registrarVenda(Fila<ItemVenda> itens, float valorTotal, float valorRecebido, float troco) {
        Venda venda = new Venda(itens, valorTotal, dataDeVenda, valorRecebido, idVenda); 
        return Venda.gravarVenda(venda);
    }

    // Listar todas as vendas
    public List<Venda> listarVendas() {
        Fila<Venda> fila = Venda.lerVenda();
        List<Venda> lista = new ArrayList<>();
        Fila<Venda> temp = new Fila<>(); // fila temporária para não perder os dados

        if (fila == null || fila.estaVazia()) {
            return lista;
        }

        while (!fila.estaVazia()) {
            Venda v = fila.desenfileirar();
            lista.add(v);
            temp.enfileirar(v); // mantém a fila original
        }

        // sobrescreve a fila original
        while (!temp.estaVazia()) {
            fila.enfileirar(temp.desenfileirar());
        }

        return lista;
    }
}


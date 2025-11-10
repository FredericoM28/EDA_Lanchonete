/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Déleo Cambula
 */
public class itemVenda implements Serializable {
    private Produto item; // Pode ser Salgado ou Pizza
    private int Qtd;
    private static final long serialVersionUID = 1L;

    public itemVenda(Produto item, int Qtd) {
        this.item = item;
        this.Qtd = Qtd;
    }

    public Produto getItem() {
        return item;
    }

    public void setItem(Produto item) {
        this.item = item;
    }

    public int getQtd() {
        return Qtd;
    }

    public void setQtd(int Qtd) {
        this.Qtd = Qtd;
    }  

    public static Fila<itemVenda> adicionarItem(Fila<itemVenda> fila, Produto produto, int qtd) {
        itemVenda item = new itemVenda(produto, qtd);
        fila.enfileirar(item);
        return fila; 
    }

    public static Fila<itemVenda> removerItem(Fila<itemVenda> fila, int id) {
        Fila<itemVenda> aux = new Fila<>();

        while (!fila.estaVazia()) {
            itemVenda produto = fila.desenfileirar();
            
            if(produto.getItem().getId() != id){
                aux.enfileirar(produto);
            }  
        }

        // Reconstruir fila original
        while (!aux.estaVazia()) {
            fila.enfileirar(aux.desenfileirar());
        }

        return fila;
    }

    public static Produto buscarPorNome(Fila<itemVenda> fila, String nome) {
        while (fila.estaVazia()) {
            Produto produto = fila.desenfileirar().getItem();
            
            if(produto.getNome().equalsIgnoreCase(nome)){
                return produto;
            }
        }
        return new Produto() {};
    }

    public static float precoTotal(Fila<itemVenda> fila) {
        float total = 0;

        while (!fila.estaVazia()) {
            itemVenda obj = fila.desenfileirar();        
            total += obj.getItem().getPreco()*obj.getQtd();                
        }
        return total;
    }

    // Calcular o troco
    public static float troco(float valorRecebido, Fila<itemVenda> fila) {     
        float total = precoTotal(fila);
        if(valorRecebido<total){
           
        }
            
        return valorRecebido - total;
    }
}

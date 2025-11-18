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
public class ItemVenda implements Serializable {

    private Produto item; // Pode ser Salgado ou Pizza
    private int Qtd;
    private static final long serialVersionUID = 1L;

    public ItemVenda(Produto item, int Qtd) {
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

    public static Fila<ItemVenda> adicionarItem(Fila<ItemVenda> fila, Produto produto, int qtd) {
        ItemVenda item = new ItemVenda(produto, qtd);
        fila.enfileirar(item);
        return fila;
    }
//    public static Fila<ItemVenda> adicionarItem(Fila<ItemVenda> fila, Produto produto, int qtd) {
//        Fila<ItemVenda> auxiliar = new Fila<>();
//        boolean produtoEncontrado = false;
//
//        // Procura pelo produto na fila
//        while (!fila.estaVazia()) {
//            ItemVenda item = fila.desenfileirar();
//
//            if (item.getItem().getId() == produto.getId()) {
//                // Se encontrou o produto, soma a quantidade
//                item.setQtd(item.getQtd() + qtd);
//                produtoEncontrado = true;
//            }
//            auxiliar.enfileirar(item);
//        }
//
//        // Restaura a fila original
//        while (!auxiliar.estaVazia()) {
//            fila.enfileirar(auxiliar.desenfileirar());
//        }
//
//        // Se não encontrou o produto, adiciona novo item
//        if (!produtoEncontrado) {
//            ItemVenda novoItem = new ItemVenda(produto, qtd);
//            fila.enfileirar(novoItem);
//        }
//
//        return fila;
//    }

    public static Fila<ItemVenda> atualizarQuantidade(Fila<ItemVenda> fila, int id, int novaQuantidade) {
        Fila<ItemVenda> auxiliar = new Fila<>();

        while (!fila.estaVazia()) {
            ItemVenda item = fila.desenfileirar();

            if (item.getItem().getId() == id) {
                item.setQtd(novaQuantidade);
            }
            auxiliar.enfileirar(item);
        }

        // Restaura a fila original
        while (!auxiliar.estaVazia()) {
            fila.enfileirar(auxiliar.desenfileirar());
        }

        return fila;
    }

    public static ItemVenda buscarItemPorId(Fila<ItemVenda> fila, int id) {
        Fila<ItemVenda> auxiliar = new Fila<>();
        ItemVenda itemEncontrado = null;

        while (!fila.estaVazia()) {
            ItemVenda item = fila.desenfileirar();
            if (item.getItem().getId() == id) {
                itemEncontrado = item;
            }
            auxiliar.enfileirar(item);
        }

        // Restaura a fila original
        while (!auxiliar.estaVazia()) {
            fila.enfileirar(auxiliar.desenfileirar());
        }

        return itemEncontrado;
    }

    public static Fila<ItemVenda> removerItem(Fila<ItemVenda> fila, int id) {
        Fila<ItemVenda> aux = new Fila<>();

        while (!fila.estaVazia()) {
            ItemVenda produto = fila.desenfileirar();

            if (produto.getItem().getId() != id) {
                aux.enfileirar(produto);
            }
        }

        // Reconstruir fila original
        while (!aux.estaVazia()) {
            fila.enfileirar(aux.desenfileirar());
        }

        return fila;
    }

    public static Produto buscarPorNome(Fila<ItemVenda> fila, String nome) {
        while (fila.estaVazia()) {
            Produto produto = fila.desenfileirar().getItem();

            if (produto.getNome().equalsIgnoreCase(nome)) {
                return produto;
            }
        }
        return new Produto() {
        };
    }

public static float precoTotal(Fila<ItemVenda> fila) {
    float total = 0;
    Fila<ItemVenda> temp = new Fila<>();

    // Calcula o total sem esvaziar a fila original
    while (!fila.estaVazia()) {
        ItemVenda obj = fila.desenfileirar();
        temp.enfileirar(obj);
        total += obj.getItem().getPreco() * obj.getQtd();
    }

    // Restaura a fila original
    while (!temp.estaVazia()) {
        fila.enfileirar(temp.desenfileirar());
    }

    return total;
}
    // Calcular o troco
    public static float troco(float valorRecebido, Fila<ItemVenda> fila) {
        float total = precoTotal(fila);
        if (valorRecebido < total) {

        }

        return valorRecebido - total;
    }
}

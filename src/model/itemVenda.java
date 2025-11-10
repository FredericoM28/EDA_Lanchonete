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
    private Object item; // Pode ser Salgado ou Pizza
    private int Qtd;
    private static final long serialVersionUID = 1L;

    public itemVenda(Object item, int Qtd) {
        this.item = item;
        this.Qtd = Qtd;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public int getQtd() {
        return Qtd;
    }

    public void setQtd(int Qtd) {
        this.Qtd = Qtd;
    }

    // Adicionar item na fila
    public static Fila<Object> adicionarItem(Fila<Object> fila, Object item) {
        fila.enfileirar(item);
        return fila;
    }

    // Remover item da fila
    public static Fila<Object> removerItem(Fila<Object> fila, Object item) {
        Fila<Object> aux = Fila.removerItem(fila, item);
        return aux;
    }

    //  listar todos os itens 
    public static List<Object> listarItens(Fila<Object> fila) {
        List<Object> lista = new ArrayList<>();
        Fila<Object> aux = new Fila<>();

        while (!fila.estaVazia()) {
            Object obj = fila.desenfileirar();
            lista.add(obj);
            aux.enfileirar(obj);
        }

        // Reconstituir a fila original
        while (!aux.estaVazia()) {
            fila.enfileirar(aux.desenfileirar());
        }

        return lista;
    }

    //  buscar itens pelo nome 
    public static List<Object> buscarPorNome(Fila<Object> fila, String nome) {
        List<Object> encontrados = new ArrayList<>();
        Fila<Object> aux = new Fila<>();

        while (!fila.estaVazia()) {
            Object obj = fila.desenfileirar();

            String nomeItem = "";
            try {
                // Usa reflexão para tentar pegar o nome do objeto (se existir getNome)
                nomeItem = (String) obj.getClass().getMethod("getNome").invoke(obj);
            } catch (Exception e) {
                // Ignora se o objeto não tiver o método getNome
            }

            if (nomeItem != null && nomeItem.toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(obj);
            }

            aux.enfileirar(obj);
        }

        // Reconstituir a fila original
        while (!aux.estaVazia()) {
            fila.enfileirar(aux.desenfileirar());
        }

        return encontrados;
    }
}

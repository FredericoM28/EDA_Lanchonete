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

    

    // Adicionar item na fila (se já existir, aumenta a quantidade)
    public static Fila<Object> adicionarItem(Fila<Object> fila, Object item, int Qtd) { 
        fila.enfileirar(item);
         return fila; }

    // Remover item da fila
    public static Fila<Object> removerItem(Fila<Object> fila, Object item) {
        Fila<Object> aux = new Fila<>();

        while (!fila.estaVazia()) {
            Object obj = fila.desenfileirar();

            if (obj instanceof itemVenda) {
                itemVenda iv = (itemVenda) obj;

                if (!iv.getItem().equals(item)) {
                    aux.enfileirar(iv);
                }
            } else {
                aux.enfileirar(obj);
            }
        }

        // Reconstruir fila original
        while (!aux.estaVazia()) {
            fila.enfileirar(aux.desenfileirar());
        }

        return fila;
    }

    // Listar todos os itens
    public static List<Object> listarItens(Fila<Object> fila) {
        List<Object> lista = new ArrayList<>();
        Fila<Object> aux = new Fila<>();

        while (!fila.estaVazia()) {
            Object obj = fila.desenfileirar();
            lista.add(obj);
            aux.enfileirar(obj);
        }

        while (!aux.estaVazia()) {
            fila.enfileirar(aux.desenfileirar());
        }

        return lista;
    }

    // Buscar itens pelo nome
    public static List<Object> buscarPorNome(Fila<Object> fila, String nome) {
        List<Object> encontrados = new ArrayList<>();
        Fila<Object> aux = new Fila<>();

        while (!fila.estaVazia()) {
            Object obj = fila.desenfileirar();
            String nomeItem = "";

            try {
                nomeItem = (String) obj.getClass().getMethod("getNome").invoke(obj);
            } catch (Exception e) {
                // ignora
            }

            if (nomeItem != null && nomeItem.toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(obj);
            }

            aux.enfileirar(obj);
        }

        while (!aux.estaVazia()) {
            fila.enfileirar(aux.desenfileirar());
        }

        return encontrados;
    }

   

    // Calcular o total a pagar (soma dos preços * quantidade)
    public static float totalAPagar(Fila<Object> fila) {
        float total = 0;
        Fila<Object> aux = new Fila<>();

        while (!fila.estaVazia()) {
            Object obj = fila.desenfileirar();

            if (obj instanceof itemVenda) {
                itemVenda iv = (itemVenda) obj;
                try {
                    float preco = (float) iv.getItem().getClass().getMethod("getPreco").invoke(iv.getItem());
                    total += preco * iv.getQtd();
                } catch (Exception e) {
                    // ignora se o item não tiver getPreco()
                }
                aux.enfileirar(iv);
            } else {
                aux.enfileirar(obj);
            }
        }

        // Reconstruir fila original
        while (!aux.estaVazia()) {
            fila.enfileirar(aux.desenfileirar());
        }

        return total;
    }

    // Calcular o troco
    public static float troco(float valorRecebido, Fila<Object> fila) {
        float total = totalAPagar(fila);
        return valorRecebido - total;
    }
}

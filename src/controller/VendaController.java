package controller;

import model.Venda;
import model.Fila;
import model.itemVenda;
import java.util.ArrayList;
import java.util.List;

public class VendaController {

    // Registrar nova venda
    public boolean registrarVenda(Fila<itemVenda> itens, float valorTotal, float valorRecebido, float troco) {
        Venda venda = new Venda(itens, valorTotal, valorRecebido, troco); 
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

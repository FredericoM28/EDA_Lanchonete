/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Déleo Cambula
 * @param <T>
 */
public class Fila<T> implements Serializable {

    private Node<T> inicio; // Primeiro nó da fila
    private Node<T> fim;    // Último nó da fila
    private int tamanho;    // Quantos elementos há na fila
    private static final long serialVersionUID = 1L;

    public Fila() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    public void enfileirar(T valor) {//(enqueue)
        Node<T> novo = new Node<>(valor);

        if (fim != null) {
            fim.proximo = novo; // O último nó agora aponta para o novo
        }

        fim = novo; // Atualiza o fim

        if (inicio == null) {
            inicio = novo; // Se for o primeiro elemento
        }

        tamanho++;
    }

    public T desenfileirar() { //(dequeue)
        if (inicio == null) {
            throw new IllegalStateException("Fila vazia!");
        }

        T valor = inicio.dado;    // Guarda o valor do início
        inicio = inicio.proximo;  // Avança para o próximo nó

        if (inicio == null) {
            fim = null; // Se a fila ficou vazia
        }

        tamanho--;
        return valor;
    }

    // Verifica o elemento no início da fila (sem remover)
    public T frente() {
        if (inicio == null) {
            throw new IllegalStateException("Fila vazia!");
        }
        return inicio.dado;
    }

    // Verifica se a fila está vazia
    public boolean estaVazia() {
        return tamanho == 0;
    }

    // Retorna o número de elementos
    public int tamanho() {
        return tamanho;
    }

    // Exibe todos os elementos da fila
    public void mostrarFila() {
        if (estaVazia()) {
            System.out.println("Fila vazia.");
            return;
        }

        Node<T> atual = inicio;
        System.out.print("Fila: ");
        while (atual != null) {
            System.out.print(atual.dado + " ");
            atual = atual.proximo;
        }
        System.out.println();
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
}

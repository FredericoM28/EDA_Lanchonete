/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Déleo Cambula
 * @param <T>
 */
public class Node<T> implements Serializable {

    T dado;           // Valor armazenado no nó
    Node<T> proximo;  // Referência para o próximo nó
    private static final long serialVersionUID = 1L;

    Node(T valor) {
        this.dado = valor;
        this.proximo = null;
    }
}

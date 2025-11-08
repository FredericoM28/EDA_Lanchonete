/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Déleo Cambula
 */
public class itemVenda implements Serializable{
    private Object item;//para aceitar receber salgado ou pizza
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

}

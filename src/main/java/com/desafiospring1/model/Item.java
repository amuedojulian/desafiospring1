package com.desafiospring1.model;

import javax.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long itemCode;
    private float price;
    private int qtd;
    @ManyToOne
    private Venda venda;

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemCode() {
        return itemCode;
    }

    public void setItemCode(Long idItem) {
        this.itemCode = idItem;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode=" + itemCode +
                ", price=" + price +
                ", qtd=" + qtd +
                ", venda=" + venda +
                '}';
    }
}

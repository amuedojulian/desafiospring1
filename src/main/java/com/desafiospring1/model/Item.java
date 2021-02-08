package com.desafiospring1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    @JsonBackReference
    private Venda venda;

    public Item() {
    }

    public Item(Long itemCode, float price, int qtd) {
        this.itemCode = itemCode;
        this.price = price;
        this.qtd = qtd;
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

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }
}

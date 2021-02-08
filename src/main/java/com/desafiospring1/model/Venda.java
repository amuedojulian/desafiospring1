package com.desafiospring1.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "venda")
    private List<Item> item;
    @ManyToOne
    private Vendedor vendedorId;

    public Venda(){

    }

    public Venda(List<Item> item, Vendedor vendedorId) {
        this.item = item;
        this.vendedorId = vendedorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public Vendedor getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Vendedor salesMan) {
        this.vendedorId = salesMan;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "saleID=" + id +
                ", item=" + item +
                ", salesMan=" + vendedorId +
                '}';
    }
}

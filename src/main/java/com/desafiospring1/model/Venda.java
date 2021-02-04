package com.desafiospring1.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Venda {

    @Id
    private Long id;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "venda")
    private List<Item> item;
    @ManyToOne
    private Vendedor salesMan;

    public Venda() {
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

    public Vendedor getSalesMan() {
        return salesMan;
    }

    public void setSalesMan(Vendedor salesMan) {
        this.salesMan = salesMan;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "saleID=" + id +
                ", item=" + item +
                ", salesMan=" + salesMan +
                '}';
    }
}

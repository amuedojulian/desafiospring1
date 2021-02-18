package com.desafiospring1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@IdClass(VendaId.class)
public class Venda {

    @Id
    public Long id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "venda")
    @JsonBackReference
    private List<Item> items;
    @ManyToOne
    private Vendedor vendedor;
    @Id
    private String file;

    public Venda() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "vendedor=" + vendedor +
                '}';
    }
}

class VendaId implements Serializable {
    private Long id;

    private String file;

    public VendaId() {
    }

    public VendaId(Long id, String file) {
        this.id = id;
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VendaId)) return false;
        VendaId accountId = (VendaId) o;
        return id.equals(accountId.id) && file.equals(accountId.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, file);
    }
}

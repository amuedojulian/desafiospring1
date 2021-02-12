package com.desafiospring1.repository;

import com.desafiospring1.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    Vendedor findByCpf(String cpf);
    Vendedor findByNameAndFile(String name, String file);
    @Query("SELECT count(*) FROM Vendedor")
    long countVendedors();
}

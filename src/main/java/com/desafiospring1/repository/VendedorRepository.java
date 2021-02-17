package com.desafiospring1.repository;

import com.desafiospring1.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    Vendedor findByCpf(String cpf);
    Vendedor findByNameAndFile(String name, String file);
    Long countByFile(String file);
}

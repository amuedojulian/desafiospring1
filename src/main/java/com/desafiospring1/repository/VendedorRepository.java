package com.desafiospring1.repository;

import com.desafiospring1.model.Cliente;
import com.desafiospring1.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    Vendedor findByCpf(String cpf);
}

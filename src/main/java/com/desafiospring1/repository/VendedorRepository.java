package com.desafiospring1.repository;

import com.desafiospring1.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    Vendedor findByCpf(String cpf);
}

package com.desafiospring1.repository;
import com.desafiospring1.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Transactional(readOnly = true)
    Cliente findByCnpj(String cnpj);

}

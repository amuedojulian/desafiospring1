package com.desafiospring1.repository;
import com.desafiospring1.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByCnpj(String cnpj);
    Long countByFile(String file);

    void deleteAllByFile(String file);

}

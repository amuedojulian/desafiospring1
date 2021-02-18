package com.desafiospring1.services;

import com.desafiospring1.model.Vendedor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VendedorService {

    /**
     * Retorna um vendedor dado um CPF
     *
     * @param cpf
     * @return Optional<Vendedor>
     */
    Optional<Vendedor> buscarPorCpf(String cpf);

    /**
     * Busca e retorna um vendedor por ID
     *
     * @param id
     * @return Optional<Vendedor>
     */
    Optional<Vendedor> buscarPorId(Long id);

    /**
     * Busca e retorna um vendedor por seu Nome
     *
     * @param name
     * @param file
     * @return Optional<Vendedor>
     */
    Optional<Vendedor> buscarPorName(String name, String file);

    /**
     * Cadastra um novo vendedor na base de dados
     *
     * @param vendedor
     * @return Vendedor
     */
    Vendedor persistir(Vendedor vendedor);

    /**
     * Faz um count no clientes
     *
     * @param file
     * @return Long
     */
    Long countByFile(String file);

    /**
     * Procura o nome do cliente por id
     *
     * @param id
     * @return String
     */
    String findVendedorNameById(@Param("id") Long id);

    /**
     * Troca os vendedores do file x
     *
     * @param file
     */
    void delete(String file);

}

package com.desafiospring1.services;

import com.desafiospring1.model.Cliente;

import java.util.Optional;

public interface ClienteService {

    /**
     * Retorna um cliente dado um CNPJ
     *
     * @param cnpj
     * @return Optional<Cliente>
     */
    Optional<Cliente> buscarPorCnpj(String cnpj);

    /**
     * Cadastra um novo Cliente na base de dados
     *
     * @param cliente
     * @return Cliente
     */
    Cliente persistir(Cliente cliente);

    /**
     * Faz um count no clientes
     *
     * @param file
     * @return Long
     */
    Long countByFile(String file);

    /**
     * Troca os vendedores do file x
     *
     * @param file
     */
    void delete(String file);
}

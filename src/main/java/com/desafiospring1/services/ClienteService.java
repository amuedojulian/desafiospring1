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
     * Cadastra uma nova empressa na base de dados
     *
     * @param cliente
     * @return Empressa
     */
    Cliente persistir(Cliente cliente);
}

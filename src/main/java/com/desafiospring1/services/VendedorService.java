package com.desafiospring1.services;

import com.desafiospring1.model.Vendedor;

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
     * Cadastra um novo vendedor na base de dados
     *
     * @param vendedor
     * @return Vendedor
     */
    Vendedor persistir(Vendedor vendedor);
}
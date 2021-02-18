package com.desafiospring1.services;

import com.desafiospring1.model.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VendaService {

    /**
     * Retorna uma lista paginada de vendas de um determinado vendedor
     *
     * @param vendedorId
     * @return pageRequest
     */
    Page<Venda> buscarPorVendedorId(Long vendedorId, PageRequest pageRequest);

    /**
     * Retorna uma venda por Id
     *
     * @param id
     * @return Optional<Venda>
     */
    Optional<Venda> buscarPorId(Long id);

    /**
     * Cadastra uma nova venda na base de dados
     *
     * @param venda
     * @return Venda
     */
    Venda persistir(Venda venda);

    /**
     * Procura o Id do pior vendedor
     *
     * @param file
     * @return Long
     */
    Long piorVendedorId(String file);

}

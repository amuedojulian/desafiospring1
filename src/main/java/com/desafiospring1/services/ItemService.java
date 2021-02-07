package com.desafiospring1.services;

import com.desafiospring1.model.Item;

import java.util.Optional;

public interface ItemService {

    /**
     * Retorna um item dado um ID
     *
     * @param id
     * @return Optional<Item>
     */
    Optional<Item> findById(Long id);

    /**
     * Cadastra um novo item na base de dados
     *
     * @param item
     * @return Item
     */
    Item persistir(Item item);
}

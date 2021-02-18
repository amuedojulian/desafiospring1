package com.desafiospring1.services;

import com.desafiospring1.model.Item;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    /**
     * Procura as vendas mais caras sumando os valores dos items
     *
     * @param file
     * @return Long
     */
    List<Long> maxVendas(String file);
}

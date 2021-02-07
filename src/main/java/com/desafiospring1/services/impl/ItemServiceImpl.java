package com.desafiospring1.services.impl;

import com.desafiospring1.model.Item;
import com.desafiospring1.repository.ItemRepository;
import com.desafiospring1.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Optional<Item> findById(Long id) {
        log.info("Buscando um item para o ID {}", id);
        return Optional.ofNullable(itemRepository.findById(id));
    }

    @Override
    public Item persistir(Item item) {
        log.info("Persistiendo item: {}", item);
        return this.itemRepository.save(item);
    }
}

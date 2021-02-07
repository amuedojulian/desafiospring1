package com.desafiospring1.controller;

import com.desafiospring1.model.Item;
import com.desafiospring1.response.Response;
import com.desafiospring1.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/items")
@CrossOrigin(origins = "*")
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ItemService itemService;

    public ItemController() {
    }

    /**
     * Retorna um item dado um ID
     *
     * @param id
     * @return ResponseEntity<Response<Item>>
     */
    @GetMapping(value = "/id/{id}")
    public ResponseEntity<Response<Item>> buscarPorCnpj(@PathVariable("id") Long id) {
        log.info("Buscando item por ID: {}", id);
        Response<Item> response = new Response<Item>();
        Optional<Item> item = itemService.findById(id);

        if (!item.isPresent()) {
            log.info("Item nao encontrado para o ID: {}", id);
            response.getErrors().add("Item nao encontrado para o ID" + id);
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(item.get());
        return ResponseEntity.ok(response);
    }
}

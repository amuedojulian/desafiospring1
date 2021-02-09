package com.desafiospring1.controller;

import com.desafiospring1.model.Cliente;
import com.desafiospring1.response.Response;
import com.desafiospring1.services.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    public ClienteController() {
    }

    /**
     * Retorna um cliente dado um CNPJ
     *
     * @param cnpj
     * @return ResponseEntity<Response<Cliente>>
     */
    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<Response<Cliente>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
        log.info("Buscando cliente por CNPJ: {}", cnpj);
        Response<Cliente> response = new Response<Cliente>();
        Optional<Cliente> cliente = clienteService.buscarPorCnpj(cnpj);

        if (!cliente.isPresent()) {
            log.info("Cliente nao encontrado para o CNPJ: {}", cnpj);
            response.getErrors().add("Cliente nao encontrado para o CNPJ" + cnpj);
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(cliente.get());
        return ResponseEntity.ok(response);
    }

    /**
     * Adiciona um novo cliente
     *
     * @param cliente
     * @param result
     * @return ResponseEntity<Response<Cliente>>
     * @throws ParseException
     */
    @PostMapping("/add")
    public ResponseEntity<Response<Cliente>> adicionar(@Valid @RequestBody Cliente cliente, BindingResult result) throws ParseException {
        log.info("Adicionando cliente: {}", cliente.toString());
        Response<Cliente> response = new Response<Cliente>();
        if (result.hasErrors()) {
            log.error("Erro validando cliente: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        clienteService.persistir(cliente);
        response.setData(cliente);
        return ResponseEntity.ok(response);
    }

    /**
     * Atualiza os dados de um cliente
     *
     * @param id
     * @param cliente
     * @return ResponseEntity<Response<Cliente>>
     * @throws ParseException
     */
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Response<Cliente>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody Cliente cliente, BindingResult result) throws ParseException {
        log.info("Atualizando cliente: {}", cliente.toString());
        Response<Cliente> response = new Response<Cliente>();
        cliente.setId(id);
        if (result.hasErrors()) {
            log.error("Erro validando cliente: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        clienteService.persistir(cliente);
        response.setData(cliente);
        return ResponseEntity.ok(response);
    }
}

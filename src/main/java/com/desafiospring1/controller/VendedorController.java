package com.desafiospring1.controller;

import com.desafiospring1.model.Vendedor;
import com.desafiospring1.response.Response;
import com.desafiospring1.services.VendedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/vendedores")
@CrossOrigin(origins = "*")
public class VendedorController {

    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private VendedorService vendedorService;

    public VendedorController() {
    }

    /**
     * Retorna um vendedor dado um CPF
     *
     * @param cpf
     * @return ResponseEntity<Response<Vendedor>>
     */
    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<Response<Vendedor>> buscarPorCnpj(@PathVariable("cpf") String cpf) {
        log.info("Buscando cliente por CPF: {}", cpf);
        Response<Vendedor> response = new Response<Vendedor>();
        Optional<Vendedor> vendedor = vendedorService.buscarPorCpf(cpf);

        if (!vendedor.isPresent()) {
            log.info("Vendedor nao encontrado para o CPF: {}", cpf);
            response.getErrors().add("Vendedor nao encontrado para o CPF" + cpf);
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(vendedor.get());
        return ResponseEntity.ok(response);
    }
}

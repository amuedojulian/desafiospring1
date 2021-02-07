package com.desafiospring1.controller;

import com.desafiospring1.model.Venda;
import com.desafiospring1.model.Vendedor;
import com.desafiospring1.response.Response;
import com.desafiospring1.services.VendaService;
import com.desafiospring1.services.VendedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/vendas")
@CrossOrigin(origins = "*")
public class VendaController {

    private static final Logger log = LoggerFactory.getLogger(VendaController.class);

    @Autowired
    private VendaService vendaService;

    @Autowired
    private VendedorService vendedorService;

    @Value("${paginacao.qtd_por_pagina}")
    private int qtdPorPagina;

    public VendaController() {
    }

    /**
     * Retorna a listagem de vendas de um vendedor
     *
     * @param vendedorId
     * @return ResponseEntity<Response<Venda>>
     */
    @GetMapping(value = "/vendedor/{vendedorId}")
    public ResponseEntity<Response<Page<Venda>>> listarPorVendedorId(
            @PathVariable("vendedorId") Long vendedorId,
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir) {
        log.info("Buscando vendas por ID do vendedor: {}, página: {}", vendedorId, pag);
        Response<Page<Venda>> response = new Response<Page<Venda>>();

        PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Sort.Direction.valueOf(dir), ord);
        Page<Venda> lancamentos = this.vendaService.buscarPorVendedorId(vendedorId, pageRequest);

        response.setData(lancamentos);
        return ResponseEntity.ok(response);
    }

    /**
     * Retorna uma venda por ID
     *
     * @param id
     * @return ResponseEntity<Response<Venda>>
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<Venda>> listarPorId(@PathVariable("id") Long id) {
        log.info("Buscando venda por ID: {}", id);
        Response<Venda> response = new Response<Venda>();
        Optional<Venda> venda = this.vendaService.buscarPorId(id);

        if (!venda.isPresent()) {
            log.info("Venda nao encontrada para o ID: {}", id);
            response.getErrors().add("Venda nao encontrada para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(venda.get());
        return ResponseEntity.ok(response);
    }

    /**
     * Valida um vendedor. Verificando se ele é exitente e válido no sistema
     *
     * @param venda
     * @return result
     */
    private void validarVendedor(Venda venda, BindingResult result) {
        if (venda.getSalesMan().getId() == null) {
            result.addError(new ObjectError("vendedor", "Vendedor nao informado"));
            return;
        }

        log.info("Validando vendedor id {}:", venda.getSalesMan().getId());
        Optional<Vendedor> vendedor = this.vendedorService.buscarPorId(venda.getSalesMan().getId());
        if (!vendedor.isPresent()) {
            result.addError(new ObjectError("vendedor", "Vendedor nao encontrado. ID inexistente"));
        }
    }
}

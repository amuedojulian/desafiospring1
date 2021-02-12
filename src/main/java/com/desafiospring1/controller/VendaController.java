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
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.JsonPath;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
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
        Page<Venda> vendas = this.vendaService.buscarPorVendedorId(vendedorId, pageRequest);

        response.setData(vendas);
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
     * Adiciona uma nova venda
     *
     * @param venda
     //* @param result
     * @return ResponseEntity<Response<Venda>>
     * @throws ParseException
     */
    @PostMapping("/add/{vendaId}/{vendedorName}/{file}")
    public ResponseEntity<Response<Venda>> adicionar(@PathVariable Long vendaId, @PathVariable String vendedorName, @PathVariable String file, @Valid @RequestBody Venda venda, BindingResult result) throws ParseException {
        venda.setId(vendaId);
        venda.setFile(file);
        venda.setVendedor(vendedorService.buscarPorName(vendedorName, file).get());
        validarVendedor(venda, result);
        log.info("Adicionando venda: {}", venda.toString());
        Response<Venda> response = new Response<Venda>();

        if (result.hasErrors()) {
            log.error("Erro validando venda: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        vendaService.persistir(venda);
        response.setData(venda);
        return ResponseEntity.ok(response);
    }

    /**
     * Atualiza os dados de um lancamento
     *
     * @param id
     * @param lancamentoDto
     * @return ResponseEntity<Response<LancamentoDto>>
     * @throws ParseException
     */
    /*@PutMapping(value = "/{id}")
    public ResponseEntity<Response<LancamentoDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody LancamentoDto lancamentoDto, BindingResult result) throws ParseException {
        log.info("Atualizando lancamento: {}", lancamentoDto.toString());
        Response<LancamentoDto> response = new Response<LancamentoDto>();
        validarFuncionario(lancamentoDto, result);
        lancamentoDto.setId(Optional.of(id));
        Lancamento lancamento = this.converterDtoParaLancamento(lancamentoDto, result);

        if (result.hasErrors()) {
            log.error("Erro validando lancamento: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        lancamento = this.lancamentoService.persistir(lancamento);
        response.setData(this.converterLancamentoDto(lancamento));
        return ResponseEntity.ok(response);
    }*/

    /**
     * Remove um lancamento por Id
     *
     * @param id
     * @return ResponseEntity<Response<Lancamento>>
     */
    /*@DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
        log.info("Removendo lancamento: {}", id);
        Response<String> response = new Response<String>();
        Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);

        if (!lancamento.isPresent()) {
            log.error("Erro ao remover devido ao lancamento Id: {} ser inválido", id);
            response.getErrors().add("Erro ao remover lancamento. Registro nao encontrado para o id" +  id);
            return ResponseEntity.badRequest().body(response);
        }

        this.lancamentoService.remover(id);
        return ResponseEntity.ok(new Response<String>());
    }*/

    /**
     * Valida um vendedor. Verificando se ele é exitente e válido no sistema
     *
     * @param venda
     * @return result
     */
    private void validarVendedor(Venda venda, BindingResult result) {
        if (venda.getVendedor() == null) {
            result.addError(new ObjectError("vendedor", "Vendedor nao informado"));
            return;
        }

        log.info("Validando vendedor id {}:", venda.getVendedor().getId());
        Optional<Vendedor> vendedor = this.vendedorService.buscarPorId(venda.getVendedor().getId());
        if (!vendedor.isPresent()) {
            result.addError(new ObjectError("vendedor", "Vendedor nao encontrado. ID inexistente"));
        }
    }
}

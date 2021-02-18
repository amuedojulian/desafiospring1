package com.desafiospring1.services.impl;

import com.desafiospring1.model.Venda;
import com.desafiospring1.repository.VendaRepository;
import com.desafiospring1.services.VendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendaServiceImpl implements VendaService {

    private static final Logger log = LoggerFactory.getLogger(VendaServiceImpl.class);

    @Autowired
    private VendaRepository vendaRepository;

    public Page<Venda> buscarPorVendedorId(Long vendedorId, PageRequest pageRequest) {
        log.info("Buscando vendas para o vendedor Id {}", vendedorId, pageRequest);
        return this.vendaRepository.findByVendedorId(vendedorId, pageRequest);
    }

    @Cacheable(value = "vendaPorId", key = "#id")
    public Optional<Venda> buscarPorId(Long id) {
        log.info("Buscando um venda pelo Id: {}", id);
        return this.vendaRepository.findById(id);
    }

    @CachePut(value = "vendaPorId")
    public Venda persistir(Venda venda) {
        log.info("Persisitndo o venda {}", venda);
        return this.vendaRepository.save(venda);
    }

    @Override
    public Long piorVendedorId(String file) {
        return this.vendaRepository.piorVendedorId(file);
    }
}

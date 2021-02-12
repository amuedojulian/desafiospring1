package com.desafiospring1.services.impl;

import com.desafiospring1.model.Vendedor;
import com.desafiospring1.repository.VendedorRepository;
import com.desafiospring1.services.VendedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendedorServiceImpl implements VendedorService {

    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private VendedorRepository vendedorRepository;

    @Override
    public Optional<Vendedor> buscarPorCpf(String cpf) {
        log.info("Buscando um vendedor para o CPF {}", cpf);
        return Optional.ofNullable(vendedorRepository.findByCpf(cpf));
    }

    @Override
    public Optional<Vendedor> buscarPorId(Long id) {
        log.info("Buscando um vendedor para o ID {}", id);
        return vendedorRepository.findById(id);
    }

    @Override
    public Optional<Vendedor> buscarPorName(String name, String file) {
        log.info("Buscando um vendedor para o Nome {}", name);
        return Optional.ofNullable(vendedorRepository.findByNameAndFile(name, file));
    }

    @Override
    public Vendedor persistir(Vendedor vendedor) {
        log.info("Persistiendo vendedor: {}", vendedor);
        return this.vendedorRepository.save(vendedor);
    }
}

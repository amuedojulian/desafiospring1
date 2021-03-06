package com.desafiospring1.services.impl;

import com.desafiospring1.model.Vendedor;
import com.desafiospring1.repository.VendedorRepository;
import com.desafiospring1.services.VendedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service("vendedorService")
public class VendedorServiceImpl implements VendedorService {

    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Qualifier("vendedorRepository")
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

    @Override
    public Long countByFile(String file) {
        return vendedorRepository.countByFile(file);
    }

    @Override
    public String findVendedorNameById(Long id) {
        return vendedorRepository.findVendedorNameById(id);
    }

    @Override
    public void delete(String file) {
        this.vendedorRepository.deleteAllByFile(file);
    }
}

package com.desafiospring1.services.impl;

import com.desafiospring1.model.Cliente;
import com.desafiospring1.repository.ClienteRepository;
import com.desafiospring1.services.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service("clienteService")
public class ClienteServiceImpl implements ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Qualifier("clienteRepository")
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Cacheable(value = "clientePorCnpj", key = "#cnpj")
    public Optional<Cliente> buscarPorCnpj(String cnpj) {
        log.info("Buscando um cliente para o CNPJ {}", cnpj);
        return Optional.ofNullable(clienteRepository.findByCnpj(cnpj));
    }

    @Override
    @CachePut(value = "clientePorCnpj")
    public Cliente persistir(Cliente cliente) {
        log.info("Persistiendo cliente: {}", cliente);
        return this.clienteRepository.save(cliente);
    }

    @Override
    public Long countByFile(String file) {
        return clienteRepository.countByFile(file);
    }

    @Override
    public void delete(String file) {
        this.clienteRepository.deleteAllByFile(file);
    }
}

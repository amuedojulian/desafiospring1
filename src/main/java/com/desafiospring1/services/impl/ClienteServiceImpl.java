package com.desafiospring1.services.impl;

import com.desafiospring1.model.Cliente;
import com.desafiospring1.repository.ClienteRepository;
import com.desafiospring1.services.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Optional<Cliente> buscarPorCnpj(String cnpj) {
        log.info("Buscando um cliente para o CNPJ {}", cnpj);
        return Optional.ofNullable(clienteRepository.findByCnpj(cnpj));
    }

    @Override
    public Cliente persistir(Cliente cliente) {
        log.info("Persistiendo cliente: {}", cliente);
        return this.clienteRepository.save(cliente);
    }
}
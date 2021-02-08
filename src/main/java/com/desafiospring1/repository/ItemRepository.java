package com.desafiospring1.repository;

import com.desafiospring1.model.Cliente;
import com.desafiospring1.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

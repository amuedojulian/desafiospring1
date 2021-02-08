package com.desafiospring1.repository;

import com.desafiospring1.model.Item;
import com.desafiospring1.model.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.math.BigInteger;
import java.util.List;

@Transactional(readOnly = true)
@NamedQueries({
        @NamedQuery(name = "VendaRepository.findByVendedorId",
                query = "SELECT * FROM Venda venda WHERE venda.vendedor_id_id = :vendedorId") })
public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByVendedorId(@Param("vendedorId") Long vendedorId);

    Page<Venda> findByVendedorId(@Param("vendedorId") Long vendedorId, Pageable pageable);
}

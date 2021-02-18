package com.desafiospring1.repository;

import com.desafiospring1.model.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

@Transactional(readOnly = true)
@NamedQueries({
        @NamedQuery(name = "VendaRepository.findByVendedorId",
                query = "SELECT vendedor FROM Venda venda WHERE venda.vendedor_id = :vendedorId") })
public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByVendedorId(@Param("vendedorId") Long vendedorId);
    Page<Venda> findByVendedorId(@Param("vendedorId") Long vendedorId, Pageable pageable);
    @Query(value = "SELECT vendedorId FROM\n" +
            "(SELECT vendedorId, count(vendaId) AS ventas FROM \n" +
            "(select vendedor.id as vendedorId, venda.id as vendaId, vendedor.file as vFile from vendedor left join venda on vendedor.id = venda.vendedor_id) \n" +
            "where vFile like :file group by vendedorId order by count(vendaId) ASC)\n" +
            "WHERE ventas = (SELECT MIN(venta) FROM (SELECT COUNT(vendaId) as venta, vendedor_id FROM (select vendedor_id, venda.id as vendaId, vendedor.file as vFile from vendedor left join venda on vendedor.id = venda.vendedor_id) where vFile like :file group by vendedor_id order by count(vendaId)));", nativeQuery = true)
    List<Long> piorVendedorsId(@Param("file") String file);

    void deleteAllByFile(String file);
}

package com.desafiospring1.repository;

import com.desafiospring1.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "SELECT venda_id\n" +
            "from item\n" +
            "where venda_file like :file \n" +
            "group by venda_id\n" +
            "having SUM(price*qtd) = (select MAX(venda_id_price)  \n" +
            "   from (select SUM(price*qtd)  venda_id_price\n" +
            "     from item\n" +
            "     group by venda_id) maxPrice) limit 1", nativeQuery = true)
    Long maxVenda(@Param("file") String file);
}

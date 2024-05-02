package com.cellphones10.repository;

import com.cellphones10.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsById(Long id);
    @Query(value = "select * from product where category_id = ? order by created_date desc limit 10",nativeQuery = true)
    List<ProductEntity> findTop10ProdcutEntityByCategoryId(Long id);

    @Query(value = "select * from product where price between ?1 and ?2 limit ?3 offset ?4", nativeQuery = true)
    List<ProductEntity> filterProductByPrice(BigDecimal minPrice, BigDecimal maxPrice,Integer limit, Integer page);

    @Query(value = "select p.* from product p inner join category c on p.category_id=c.id where c.code=?1 limit ?2 offset ?3",nativeQuery = true)
    List<ProductEntity> findByCategoryCode(String categorycode, Integer limit, Integer offset);

    @Query(value = "select count(p.id) from product p inner join category c on p.category_id=c.id where c.code=?1",nativeQuery = true)
    Long countProductByCategoryCode(String categorycode);


    @Query(value = "select p.* from product p inner join category c on p.category_id=c.id where c.code=?1 order by ?4 limit ?2 offset ?3 case when ?5 == 1 then asc else desc",nativeQuery = true)
    List<ProductEntity> filterProduct(String categorycode, Integer limit, Integer offset, String orderby, int dir);
}

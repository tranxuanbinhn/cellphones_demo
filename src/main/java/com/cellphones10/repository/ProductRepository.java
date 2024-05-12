package com.cellphones10.repository;

import com.cellphones10.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsById(Long id);
    @Query(value = "select * from product where category_id = ? order by created_date desc limit 5",nativeQuery = true)
    List<ProductEntity> findTop5ProdcutEntityByCategoryId(Long id);

    @Query(value = "select * from product where price between ?1 and ?2 limit ?3 offset ?4", nativeQuery = true)
    List<ProductEntity> filterProductByPrice(BigDecimal minPrice, BigDecimal maxPrice,Integer limit, Integer page);

    @Query(value = "select p.* from product p inner join category c on p.category_id=c.id where c.code=?1 limit ?2 offset ?3",nativeQuery = true)
    List<ProductEntity> findByCategoryCode(String categorycode, Integer limit, Integer offset);

    @Query(value = "select count(p.id) from product p inner join category c on p.category_id=c.id where c.code=?1",nativeQuery = true)
    Long countProductByCategoryCode(String categorycode);


//    @Query(value = "SELECT p.* FROM product p INNER JOIN category c ON p.category_id=c.id WHERE c.code=?1 ORDER BY "
//            + "?2" +"?3"
//            + "LIMIT ?4 OFFSET ?5", nativeQuery = true)
//    List<ProductEntity> filterProduct(String categorycode, String orderby, String dir, Integer limit, Integer offset);


    Page<ProductEntity> findByCategoryCategoryCode(String CategoryCode, Pageable pageable);
    @Query(value = "select * from product where name like %:name%", nativeQuery = true)
    Page<ProductEntity> findByProductName(String name, Pageable pageable);

    @Query(value = "select count(*) from product where name like %:name%", nativeQuery = true)
   Long countFindByProductName(String name);

}

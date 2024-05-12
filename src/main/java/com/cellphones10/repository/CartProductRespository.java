package com.cellphones10.repository;

import com.cellphones10.entity.CartProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CartProductRespository extends JpaRepository<CartProduct, Long> {
    @Query(value = "select product_id from cart_product where created_by = ?", nativeQuery = true)
    Set<Long> getIdByUsername(String username);
    List<CartProduct> findAllByCartId(Long id, Pageable pageable);
    Optional<CartProduct> findByProductId(Long id);
}

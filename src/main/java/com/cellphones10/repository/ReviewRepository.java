package com.cellphones10.repository;

import com.cellphones10.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {
    @Query(value = "select * from review where product_id = ?1", nativeQuery = true)
    Page<ReviewEntity> findAllByProductId(Long productId,Pageable pageable);
    @Query(value = "select count(*) from review where product_id = ?", nativeQuery = true)
    Long countReviewBInProduct(Long productId);

    @Query(value = "select * from review where product_id = ?1", nativeQuery = true)
    List<ReviewEntity> findAllByProductId(Long productId);

}

package com.cellphones10.repository;

import com.cellphones10.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity,Long> {
    boolean existsByBrandName(String brandName);
    Optional<BrandEntity> findById(Long id);
    boolean existsById(Long id);



}

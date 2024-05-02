package com.cellphones10.repository;

import com.cellphones10.entity.CategoryEntity;
import com.cellphones10.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRespository extends JpaRepository<CategoryEntity,Long> {
    boolean existsByCategoryCode(String code);
    CategoryEntity findByCategoryName(String name);


}

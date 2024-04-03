package com.cellphones10.repository;


import com.cellphones10.entity.OrderDetailEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderdetailRepository extends JpaRepository<OrderDetailEntity,Long> {


    List<OrderDetailEntity> findAllByCartId(Long id, Pageable pageable);
    List<OrderDetailEntity> findAllByCartId(Long id);
}

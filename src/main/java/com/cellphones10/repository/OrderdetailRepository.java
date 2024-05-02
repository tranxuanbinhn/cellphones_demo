package com.cellphones10.repository;


import com.cellphones10.dto.OrderDetailDTO;
import com.cellphones10.entity.OrderDetailEntity;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderdetailRepository extends JpaRepository<OrderDetailEntity,Long> {



    Optional<OrderDetailEntity> findById(Long id);


    @Query(value = "select * from order_detail where id = ?", nativeQuery = true)
    Optional<OrderDetailEntity> findOneById(Long id);
}

package com.cellphones10.repository;


import com.cellphones10.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderdetailRepository extends JpaRepository<OrderDetailEntity,Long> {

}

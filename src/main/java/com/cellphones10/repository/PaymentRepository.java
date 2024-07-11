package com.cellphones10.repository;

import com.cellphones10.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity,Long> {
    PaymentEntity save(PaymentEntity payment);


}

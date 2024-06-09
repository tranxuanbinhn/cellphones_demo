package com.cellphones10.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Getter
@Setter
public class PaymentEntity extends BaseEntity{
   private EPayment method;
   private String status;
   private String transactionDetail;

   @OneToOne()
    private OrderEntity order;

   @ManyToOne
   private User user;

    public PaymentEntity(EPayment method, String status, String transactionDetail) {
        this.method = method;
        this.status = status;
        this.transactionDetail = transactionDetail;
    }

    public PaymentEntity() {

    }
}

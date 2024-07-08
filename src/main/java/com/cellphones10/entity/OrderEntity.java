package com.cellphones10.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderEntity extends BaseEntity{

    private  Boolean status;

    @Column(name ="total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetailEntity> orderDetails;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;



    @Column(name ="ship_payment")
    private BigDecimal shipPayment;

    private BigDecimal totalDue;

    @Column(name = "address")
    private  String address;

    @OneToOne(mappedBy = "order")
    private PaymentEntity payment;

    public OrderEntity() {

    }
}

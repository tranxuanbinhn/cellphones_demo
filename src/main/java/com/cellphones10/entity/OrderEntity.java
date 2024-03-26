package com.cellphones10.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity{

    private  Boolean status;

    @Column(name ="total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order")
    private List<OrderDetailEntity> orderDetails;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}

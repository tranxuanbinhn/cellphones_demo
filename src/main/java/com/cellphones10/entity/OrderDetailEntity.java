package com.cellphones10.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "order_detail")
public class OrderDetailEntity extends  BaseEntity{
        @Column(name = "unitprice")
        private BigDecimal unitPrice;
        private Integer quantity;

        @ManyToMany(mappedBy = "orderdetails")
        private List<ProductEntity> products;

        @ManyToOne
        @JoinColumn(name = "order_id")
        private OrderEntity order;


}

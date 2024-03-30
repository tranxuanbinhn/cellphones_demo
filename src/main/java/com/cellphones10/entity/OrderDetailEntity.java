package com.cellphones10.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "order_detail")
public class OrderDetailEntity extends  BaseEntity{
        @Column(name = "unitprice")
        private BigDecimal unitPrice;
        private Integer quantity;

        @ManyToOne()
        @JoinColumn(name = "product_id")
        private ProductEntity product;

        @ManyToOne
        @JoinColumn(name = "order_id")
        private OrderEntity order;

        public OrderDetailEntity() {

        }
}

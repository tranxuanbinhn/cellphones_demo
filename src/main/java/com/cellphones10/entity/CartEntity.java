package com.cellphones10.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Setter
@Getter
@Table(name = "cart")
public class CartEntity extends  BaseEntity{


        @OneToMany(mappedBy = "cart")
        private List<CartProduct> cartProducts;



        @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
        private User user;

          public CartEntity() {
    }
}

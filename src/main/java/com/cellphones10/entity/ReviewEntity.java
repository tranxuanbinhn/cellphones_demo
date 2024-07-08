package com.cellphones10.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
public class ReviewEntity extends BaseEntity{

    private String comment;
    private String image;
    private Integer rate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

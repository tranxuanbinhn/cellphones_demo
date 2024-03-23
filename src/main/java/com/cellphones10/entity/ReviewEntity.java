package com.cellphones10.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "review")
public class ReviewEntity extends BaseEntity{

    private String comment;
    private String image;
    private Integer rate;

    @ManyToMany
    @JoinTable(name = "product_review", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id"))
    private List<ProductEntity> products;
}

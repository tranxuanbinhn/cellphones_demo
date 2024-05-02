package com.cellphones10.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
public class ProductEntity extends BaseEntity{
    @Column(name = "name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;

    @Column(name = "numberstock")
    private Number numberStock;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "image")
    private String image;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "height")
    private Integer height;

    private Integer screensize;

    private String screentech;

    private  String ramstorage;

    private String internalmemory;

    private  String os;


    @Column(name = "length")
    private Integer length;

    @Column(name = "width")
    private Integer width;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToMany(mappedBy = "products")
    private List<ReviewEntity> reviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<OrderDetailEntity> orderdetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<CartProduct> cartProducts;



    public ProductEntity() {

    }
}

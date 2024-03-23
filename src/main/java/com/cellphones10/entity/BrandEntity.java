package com.cellphones10.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brand")
public class BrandEntity extends  BaseEntity{
    @Column(name = "name")
    private String brandName;

    @OneToMany(mappedBy = "brand")
    private List<ProductEntity> productEntitys;
}

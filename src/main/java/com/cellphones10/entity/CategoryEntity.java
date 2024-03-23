package com.cellphones10.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "category")
public class CategoryEntity extends  BaseEntity{
    @Column(name = "name")
    private String categoryName;

    @Column(name = "code")
    private String categoryCode;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products;
}

package com.cellphones10.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity extends  BaseEntity{
    @Column(name = "name")
    private String categoryName;

    @Column(name = "code")
    private String categoryCode;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products;

    public CategoryEntity(String categoryName, String categoryCode, List<ProductEntity> products) {
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.products = products;
    }

    public CategoryEntity() {

    }
}

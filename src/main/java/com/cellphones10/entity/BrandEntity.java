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

    public BrandEntity(String brandName, List<ProductEntity> productEntitys) {
        this.brandName = brandName;
        this.productEntitys = productEntitys;
    }

    public BrandEntity() {

    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<ProductEntity> getProductEntitys() {
        return productEntitys;
    }

    public void setProductEntitys(List<ProductEntity> productEntitys) {
        this.productEntitys = productEntitys;
    }
}

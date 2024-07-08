package com.cellphones10.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class ProductDTO extends AbstractDTO{
    @NotBlank
    private String productName;
    private  String description;
    private  String size;
    private String color;
    private Integer numberStock;
    @NotBlank
    private BigDecimal price;
    private  String image;
    private  Integer weight;
    private  Integer height;
    private  Integer length;
    private Integer width;

    private Integer screensize;

    private String screentech;

    private  String ramstorage;

    private String internalmemory;

    private  String os;

    private  String brandName;
    private String categoryName;
    private List<Long> reviewIds;
    private List<Long> orderdetailIds;



    public ProductDTO(@NotBlank String productName, String description, String size, String color, Integer numberStock,@NotBlank BigDecimal price, String image, Integer weight, Integer height, Integer length, Integer width, String brandName, String categoryName, List<Long> reviewIds, List<Long> orderdetailIds) {
        this.productName = productName;
        this.description = description;
        this.size = size;
        this.color = color;
        this.numberStock = numberStock;
        this.price = price;
        this.image = image;
        this.weight = weight;
        this.height = height;
        this.length = length;
        this.width = width;
        this.brandName = brandName;
        this.categoryName = categoryName;
        this.reviewIds = reviewIds;
        this.orderdetailIds = orderdetailIds;
    }

    public ProductDTO() {

    }

}

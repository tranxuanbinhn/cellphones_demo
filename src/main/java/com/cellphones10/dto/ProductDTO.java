package com.cellphones10.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO extends AbstractDTO{
    private String productName;
    private  String description;
    private  String size;
    private String color;
    private Integer numberStock;
    private BigDecimal price;
    private  String image;
    private  Integer weight;
    private  Integer height;
    private  Integer length;
    private Integer width;

    private  BrandDTO brand;
    private CategoryDTO category;
    private List<ReviewDTO> reviews;
    private List<OrderDetailDTO> orderdetails;


}

package com.cellphones10.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDTO extends AbstractDTO{
    private String categoryName;
    private String categoryCode;
    private List<ProductDTO> products;

    public CategoryDTO(String categoryName, String categoryCode, List<ProductDTO> products) {
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.products = products;
    }
    public CategoryDTO() {

    }
}

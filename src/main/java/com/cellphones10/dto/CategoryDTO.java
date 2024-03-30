package com.cellphones10.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDTO extends AbstractDTO{
    private String categoryName;
    private String categoryCode;
    private List<Long> productIds;

    public CategoryDTO(String categoryName, String categoryCode, List<Long> productIds) {
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.productIds = productIds;
    }

    public CategoryDTO() {
    }
}

package com.cellphones10.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class BrandDTO extends  AbstractDTO {
    @NotBlank
    private String brandName;

    private List<Long> productIds;

    public BrandDTO(@NotBlank String brandName, List<Long> productIds) {
        this.brandName = brandName;
        this.productIds = productIds;
    }
    public BrandDTO() {

    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
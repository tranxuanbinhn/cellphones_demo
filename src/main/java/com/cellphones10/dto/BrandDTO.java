package com.cellphones10.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class BrandDTO extends  AbstractDTO{
    @NotBlank
    private String brandName;

    private List<ProductDTO> productDTOS;

    public BrandDTO(String brandName, List<ProductDTO> productDTOS) {
        this.brandName = brandName;
        this.productDTOS = productDTOS;
    }

    public BrandDTO() {

    }
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<ProductDTO> getProductDTOS() {
        return productDTOS;
    }

    public void setProductDTOS(List<ProductDTO> productDTOS) {
        this.productDTOS = productDTOS;
    }
}

package com.cellphones10.dto.output;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
public class OutputProductCart {
    private BigDecimal unitPrice;
    private  Integer quantity;
    private Long id;
    private String productName;
    private String image;
    private BigDecimal price;
    private Long count;
    public OutputProductCart() {

    }
}

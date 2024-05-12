package com.cellphones10.dto.output;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
public class OutputProductCart {
    private  Long CartProductId;
    private  Integer quantity;
    private Long productId;
    private BigDecimal price;
    public OutputProductCart() {

    }
}

package com.cellphones10.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class OrderDetailDTO extends AbstractDTO{
    private BigDecimal unitPrice;
    private  Integer quantity;
    private Long productId;
    private  Long orderId;

    public OrderDetailDTO(BigDecimal unitPrice, Integer quantity, Long productId, Long orderId) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.productId = productId;
        this.orderId = orderId;
    }
    public OrderDetailDTO() {

    }
}

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
    private  String productName;
    private String image;

    public OrderDetailDTO(BigDecimal unitPrice, Integer quantity, Long productId, Long orderId, String productName, String image) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.productId = productId;
        this.orderId = orderId;
        this.productName = productName;
        this.image = image;
    }
    public OrderDetailDTO() {

    }
}

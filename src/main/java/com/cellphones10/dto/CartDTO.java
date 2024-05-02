package com.cellphones10.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CartDTO extends AbstractDTO{
    private List<Long> productListId;
    private Long productId;
    private  String userName;
    private Integer quantityProduct;
    public CartDTO() {

    }
}

package com.cellphones10.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class OrderDTO extends AbstractDTO{
    private Boolean status;
    private BigDecimal totalPrice;
    private List<Long> orderDetaiIds;
    private  String userName;

    public OrderDTO(Boolean status, BigDecimal totalPrice, List<Long> orderDetaiIds, String userName) {
        this.status = status;
        this.totalPrice = totalPrice;
        this.orderDetaiIds = orderDetaiIds;
        this.userName = userName;
    }

    public OrderDTO() {

    }
}

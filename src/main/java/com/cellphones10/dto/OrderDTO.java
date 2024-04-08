package com.cellphones10.dto;

import com.cellphones10.entity.EPayment;
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
    private BigDecimal shipPayment;
    private String address;
    private EPayment method;

    public OrderDTO(Boolean status, BigDecimal totalPrice, List<Long> orderDetaiIds, String userName, BigDecimal shipPayment, String address, EPayment method) {
        this.status = status;
        this.totalPrice = totalPrice;
        this.orderDetaiIds = orderDetaiIds;
        this.userName = userName;
        this.shipPayment = shipPayment;
        this.address = address;
        this.method = method;
    }

    public OrderDTO() {

    }
}

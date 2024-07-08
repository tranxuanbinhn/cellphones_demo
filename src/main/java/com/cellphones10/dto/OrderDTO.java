package com.cellphones10.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class OrderDTO extends AbstractDTO{

    private BigDecimal totalPrice;
    private List<Long> orderDetaiIds;
    private  String userName;
    private BigDecimal shipPayment;

    private String address;
    private EPayment method;
    private  List<Long> cartproductIds;

    private  List<OrderDetailDTO> orderDetailDTOS;

    public OrderDTO( BigDecimal totalPrice,  String userName, BigDecimal shipPayment, String address, EPayment method) {

        this.totalPrice = totalPrice;

        this.userName = userName;
        this.shipPayment = shipPayment;
        this.address = address;
        this.method = method;
    }

    public OrderDTO() {

    }
}

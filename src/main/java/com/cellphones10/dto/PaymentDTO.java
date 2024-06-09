package com.cellphones10.dto;

import com.cellphones10.entity.OrderEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToOne;

@Getter
@Setter
public class PaymentDTO extends AbstractDTO{
    private EPayment method;

    private String status;
    private String transactionDetail;

    private Long orderID;


    public PaymentDTO() {

    }
}

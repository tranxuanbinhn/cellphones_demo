package com.cellphones10.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDTO extends AbstractDTO{
    private Boolean status;
    private BigDecimal totalPrice;
    private List<OrderDetailDTO> orderDetailDTOs;
    private  UserDTO user;

}

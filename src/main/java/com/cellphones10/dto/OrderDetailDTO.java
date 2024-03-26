package com.cellphones10.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDetailDTO extends AbstractDTO{
    private BigDecimal unitPrice;
    private  Integer quantity;
    private List<ProductDTO> products;

    private  OrderDTO order;
}

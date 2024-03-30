package com.cellphones10.controller;

import com.cellphones10.dto.OrderDTO;
import com.cellphones10.dto.OrderDetailDTO;
import com.cellphones10.service.impl.OrderService;
import com.cellphones10.service.impl.OrderdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("api/test/order")
public class test2 {
    @Autowired
    private OrderService orderService;
    @PostMapping
    public OrderDTO add(@RequestBody OrderDTO orderDTO)
    {
       return orderService.save(orderDTO);
    }
}

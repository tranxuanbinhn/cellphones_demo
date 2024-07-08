package com.cellphones10.controller.admin;


import com.cellphones10.dto.OrderDTO;
import com.cellphones10.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/admin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/cancel/{id}")
    public ResponseEntity Cancel(@PathVariable()Long id, @CurrentSecurityContext(expression = "authentication?.name")String username)
    {
        if(  orderService.save(id,username))
        {
            return new  ResponseEntity(HttpStatus.OK);
        }
        return new  ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @GetMapping
    public List<OrderDTO> getAllOrder(){
        return  orderService.findAll();
    }

    @GetMapping("/pendding")
    public List<OrderDTO> getPenddingOrder(){
        return  orderService.findPenddingOrderAdmin();
    }
    @GetMapping("/paid")
    public List<OrderDTO> getPaidOrder(){
        return  orderService.findPenddingOrderAdmin();
    }

}

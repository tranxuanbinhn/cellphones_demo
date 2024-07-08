package com.cellphones10.controller.user;


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
@RequestMapping("/api/user/order")
public class OrderControllerUser {
    @Autowired
    private OrderService orderService;
    @PostMapping("/buy")
    public OrderDTO BuyProduct(@RequestBody()OrderDTO orderDTO, @CurrentSecurityContext(expression="authentication?.name")
            String username)
    {
      return   orderService.save(orderDTO, username);
    }
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
    public List<OrderDTO> getAllOrder(@CurrentSecurityContext(expression = "authentication?.name")String username){
        return  orderService.findAllOrder(username);
    }

    @GetMapping("/pendding")
    public List<OrderDTO> getPenddingOrder(@CurrentSecurityContext(expression = "authentication?.name")String username){
        return  orderService.findPenddingOrder(username);
    }
    @GetMapping("/paid")
    public List<OrderDTO> getPaidOrder(@CurrentSecurityContext(expression = "authentication?.name")String username){
        return  orderService.findPenddingOrder(username);
    }

}

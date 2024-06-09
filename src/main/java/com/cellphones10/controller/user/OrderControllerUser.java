package com.cellphones10.controller.user;


import com.cellphones10.dto.OrderDTO;
import com.cellphones10.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

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

}

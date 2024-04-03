package com.cellphones10.controller;

import com.cellphones10.dto.CartDTO;
import com.cellphones10.dto.OrderDetailDTO;
import com.cellphones10.dto.ProductDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.dto.output.OutputProductCart;
import com.cellphones10.security.service.UserDetailsImpl;
import com.cellphones10.service.impl.CartService;
import com.cellphones10.service.impl.OrderdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("api/product")
public class test {
    @Autowired
    private OrderdetailService orderdetailService;
    @Autowired
    private CartService cartService;
    @PostMapping("/buy/{id}")
    @Transactional
    public CartDTO add(@RequestBody OrderDetailDTO orderDetailDTO, @PathVariable("id") Long productId, @CurrentSecurityContext(expression="authentication?.name")
            String username)
    {
        orderDetailDTO.setProductId(productId);
       OrderDetailDTO orderDetailDTO1 = orderdetailService.save(orderDetailDTO);
    CartDTO cartDTO =new CartDTO();
    List<Long> ids= new ArrayList<>();
        ids.add(orderDetailDTO1.getId());
       cartDTO.setOrderDetailIds(ids);
       cartDTO.setUserName(username);
        return cartService.save(cartDTO);
}
    @GetMapping("/buy")
    public Output<OutputProductCart> GetAll(@CurrentSecurityContext(expression="authentication?.name") String username,@RequestParam("page") int page, @RequestParam("limit") int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        Output<OutputProductCart> output = new Output<>();
        output.setListResult(cartService.findAll(pageable,username));
        output.setTotalPage(cartService.count(username)/limit);
        output.setPage(page);


        return output;

    }
    @PutMapping("")
    public ResponseEntity<?> deleteProductFromCart(@RequestBody() List<Long> ids, @CurrentSecurityContext(expression = "authentication?.name") String username)
    {
        if(cartService.deleteProductFromCart(ids, username))
        {
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.internalServerError().build();

    }

}

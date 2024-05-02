package com.cellphones10.controller.admin;

import com.cellphones10.dto.CartDTO;
import com.cellphones10.dto.OrderDetailDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.dto.output.OutputProductCart;
import com.cellphones10.service.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("api/product")
public class CartController {


    @Autowired
    private CartService cartService;
    @PostMapping("/addcart")
    @Transactional
    public CartDTO add(@RequestBody()  CartDTO cartDTO, @CurrentSecurityContext(expression="authentication?.name")
            String username)
    {
        cartDTO.setUserName(username);
        return cartService.save(cartDTO);
}
//    @GetMapping("/buy")
//    public Output<OutputProductCart> GetAll(@CurrentSecurityContext(expression="authentication?.name") String username,@RequestParam("page") int page, @RequestParam("limit") int limit)
//    {
//        Pageable pageable = PageRequest.of(page-1, limit);
//        Output<OutputProductCart> output = new Output<>();
//        output.setListResult(cartService.findAll(pageable,username));
//        output.setTotalPage(cartService.count(username)/limit);
//        output.setPage(page);
//
//
//        return output;
//
//    }
//    @DeleteMapping("/deleteproductfromcart")
//    public ResponseEntity<?> deleteProductFromCart(@RequestBody() List<Long> ids, @CurrentSecurityContext(expression = "authentication?.name") String username)
//    {
//        if(cartService.deleteProductFromCart(ids, username))
//        {
//            return ResponseEntity.ok("success");
//        };
//        return ResponseEntity.internalServerError().build();
//    }
//
//
}

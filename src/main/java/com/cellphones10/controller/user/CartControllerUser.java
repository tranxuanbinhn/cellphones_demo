package com.cellphones10.controller.user;

import com.cellphones10.dto.BrandDTO;
import com.cellphones10.dto.CartDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.dto.output.OutputProductCart;
import com.cellphones10.entity.CartProduct;
import com.cellphones10.service.impl.BrandService;
import com.cellphones10.service.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/user/cart")
public class CartControllerUser {
    @Autowired
    private CartService cartService;
    @GetMapping("/getall")
    public Output<OutputProductCart> GetAll(@CurrentSecurityContext(expression="authentication?.name")
            String username)
    {

        Output<OutputProductCart> output = new Output<>();
        output.setListResult(cartService.findAll(username));




        return output;

    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> Delete(@RequestBody List<Long> ids)
    {
        boolean result= cartService.delete(ids);
        if(result == true)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.internalServerError().build();
    }
    @PostMapping("/add")
    public CartDTO AddCart(@RequestBody CartDTO cartDTO, @CurrentSecurityContext(expression="authentication?.name")
            String username)
    {
        return cartService.save(cartDTO, username);
    }

}

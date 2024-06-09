package com.cellphones10.controller.user;

import com.cellphones10.dto.PaymentDTO;
import com.cellphones10.repository.PaymentRepository;
import com.cellphones10.service.impl.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/user/payment")
public class PaymentControllerUser {
    @Autowired
    private PaymentService paymentService;

    @PostMapping()
    public PaymentDTO save(@RequestBody PaymentDTO paymentDTO,@CurrentSecurityContext(expression="authentication?.name")
            String username)
    {
        return  paymentService.save(paymentDTO, username);
    }
}

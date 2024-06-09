package com.cellphones10.service.impl;

import com.cellphones10.dto.PaymentDTO;
import com.cellphones10.entity.OrderEntity;
import com.cellphones10.entity.PaymentEntity;
import com.cellphones10.entity.User;
import com.cellphones10.repository.OrderRepository;
import com.cellphones10.repository.PaymentRepository;
import com.cellphones10.repository.UserRepository;
import com.cellphones10.service.IPaymentService;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private OrderRepository orderRepository;

    public PaymentDTO save(PaymentDTO paymentDTO, String username)
    {
        User user = userRepository.findByUsername(username).get();
        if(userRepository.findByUsername(username).isEmpty())
        {
            throw new RuntimeException("Not found user");
        }
        PaymentEntity paymentEntity = mapper.map(paymentDTO,PaymentEntity.class);
        OrderEntity orderEntity = orderRepository.findById(paymentDTO.getOrderID()).get();
        paymentEntity.setOrder(orderEntity);
        paymentEntity.setUser(user);

        return mapper.map(paymentRepository.save(paymentEntity), PaymentDTO.class);
    }
}

package com.cellphones10.service.impl;


import com.cellphones10.dto.OrderDetailDTO;
import com.cellphones10.entity.OrderDetailEntity;
import com.cellphones10.entity.ProductEntity;
import com.cellphones10.repository.OrderdetailRepository;
import com.cellphones10.repository.ProductRepository;
import com.cellphones10.service.IOrderdetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderdetailService implements IOrderdetailService {

    @Autowired
    private OrderdetailRepository orderdetailRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public OrderDetailDTO save(OrderDetailDTO orderDetailDTO) {
        Optional<ProductEntity> productEntity = productRepository.findById(orderDetailDTO.getProductId());

        if(productEntity == null)
        {
            throw  new RuntimeException("Not found");
        }
        BigDecimal priceProduct = productEntity.get().getPrice();
        BigDecimal unitprice = priceProduct.multiply(BigDecimal.valueOf(orderDetailDTO.getQuantity())) ;
        orderDetailDTO.setUnitPrice(unitprice);
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();

        orderDetailEntity = mapper.map(orderDetailDTO, OrderDetailEntity.class);
        orderDetailEntity.setProduct(productEntity.get());
        OrderDetailEntity saved =  orderdetailRepository.save(orderDetailEntity);

        OrderDetailDTO result = mapper.map(saved, OrderDetailDTO.class);


        result.setProductId(saved.getProduct().getId());
        return result;
    }

    @Override
    public List<OrderDetailDTO> findAll(Pageable pageable) {

        return null;
    }

    @Override
    public boolean delete(List<Long> list) {
        return false;
    }

    public Long count()
    {
        return orderdetailRepository.count();
    }
}

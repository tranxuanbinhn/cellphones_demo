package com.cellphones10.service.impl;

import com.cellphones10.dto.OrderDTO;
import com.cellphones10.entity.*;
import com.cellphones10.repository.*;
import com.cellphones10.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderdetailRepository orderdetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartProductRespository cartProductRespository;

    @Autowired
    private ProductRepository productRespository;

    @Autowired
    private ModelMapper mapper;


    @Transactional
    public OrderDTO save(OrderDTO orderDTO, String username) {
        try {
            User user = userRepository.findByUsername(username).get();
            if (user == null) {
                throw new RuntimeException("Not found User");
            }

            OrderEntity orderEntity = new OrderEntity();

            if (orderDTO.getMethod().equals("PAYBACK")) {
                orderEntity.setStatus(true);
            }
            else {
                orderEntity.setStatus(false);
            }


            orderEntity.setUser(user);

            orderEntity.setAddress(orderDTO.getAddress());
            OrderEntity saved = orderRepository.save(orderEntity);


            List<CartProduct> cartProducts = user.getCart().getCartProducts();
            List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            for (int i = 0; i< orderDTO.getCartproductIds().size();i++){
                Long id = orderDTO.getCartproductIds().get(i);
            CartProduct cartProduct = cartProductRespository.findById(id).get();

            if (cartProducts.contains(cartProduct)) {
                OrderDetailEntity orderDetailEntity = new OrderDetailEntity();

                orderDetailEntity.setQuantity(cartProduct.getQuantity());
                ProductEntity productEntity = productRespository.findById(cartProduct.getProduct().getId()).get();
                BigDecimal unitPrice = productEntity.getPrice();
                BigDecimal totalPriceForProduct = unitPrice.multiply(BigDecimal.valueOf(cartProduct.getQuantity()));
                orderDetailEntity.setUnitPrice(unitPrice);
                orderDetailEntity.setProduct(productEntity);
                orderDetailEntity.setOrder(saved);
                orderdetailRepository.save(orderDetailEntity);
                orderDetailEntityList.add(orderDetailEntity);
                total =total.add(totalPriceForProduct);

            }
        }


            saved.setOrderDetails(orderDetailEntityList);
            saved.setTotalPrice(total);
            orderRepository.save(saved);

            return mapper.map(saved, OrderDTO.class);
        }
        catch (RuntimeException e)
        {
            return null;
        }

    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public List<OrderDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public boolean delete(List<Long> list) {
        return false;
    }
}

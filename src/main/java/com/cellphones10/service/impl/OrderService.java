//package com.cellphones10.service.impl;
//
//import com.cellphones10.dto.OrderDTO;
//import com.cellphones10.entity.OrderDetailEntity;
//import com.cellphones10.entity.OrderEntity;
//import com.cellphones10.entity.ProductEntity;
//import com.cellphones10.entity.User;
//import com.cellphones10.repository.OrderRepository;
//import com.cellphones10.repository.OrderdetailRepository;
//import com.cellphones10.repository.ProductRepository;
//import com.cellphones10.repository.UserRepository;
//import com.cellphones10.service.IOrderService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class OrderService implements IOrderService {
//    @Autowired
//    private OrderdetailRepository orderdetailRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private ModelMapper mapper;
//
//    @Override
//    @Transactional
//    public OrderDTO save(OrderDTO orderDTO) {
//        try
//        {
//
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String ussername = authentication.getName();
//            User user = userRepository.findByUsername(ussername).get();
//            if(user == null)
//            {
//                throw  new RuntimeException("Not found User");
//            }
//            BigDecimal total = null;
//            List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
//            for (Long id:orderDTO.getOrderDetaiIds()) {
//                OrderDetailEntity orderDetailEntity = orderdetailRepository.findById(id).get();
//                if(orderDetailEntity==null)
//                {
//                    throw new RuntimeException();
//                }
//                total.add(orderDetailEntity.getUnitPrice());
//                orderDetailEntityList.add(orderDetailEntity);
//
//            }
//            OrderEntity orderEntity = new OrderEntity();
//            if(orderDTO.getMethod().equals("NHANHANG"))
//            {
//                orderEntity.setStatus(true);
//            }
//            orderEntity.setStatus(false);
//            orderEntity.setOrderDetails(orderDetailEntityList);
//            orderEntity.setTotalPrice(total);
//            orderEntity.setUser(user);
//            orderEntity.setMethod(orderDTO.getMethod());
//            orderEntity.setAddress(orderDTO.getAddress());
//            OrderEntity saved = orderRepository.save(orderEntity);
//            return mapper.map(saved, OrderDTO.class);
//        }
//        catch (RuntimeException e)
//        {
//
//        }
//        return null;
//    }
//
//    @Override
//    public List<OrderDTO> findAll(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public boolean delete(List<Long> list) {
//        return false;
//    }
//}

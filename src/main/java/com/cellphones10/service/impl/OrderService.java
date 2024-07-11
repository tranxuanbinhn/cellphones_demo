package com.cellphones10.service.impl;

import com.cellphones10.dto.OrderDTO;
import com.cellphones10.dto.OrderDetailDTO;
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
            orderEntity.setStatus(true);


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
            saved.setShipPayment(orderDTO.getShipPayment());
            saved.setTotalDue(total.add(orderDTO.getShipPayment()));
            orderRepository.save(saved);

            return mapper.map(saved, OrderDTO.class);
        }
        catch (RuntimeException e)
        {
            return null;
        }

    }


    public Boolean save(Long id, String userName) {
      try{
          User user = userRepository.findByUsername(userName).get();
          OrderEntity orderEntity = orderRepository.findById(id).get();
          if(!user.equals(orderEntity.getUser()))
          {
              return  false;
          }
          orderEntity.setStatus(false);
          orderRepository.save(orderEntity);

          return true;
      }
       catch (RuntimeException e)
       {
            return  false;
       }
    }
    public Boolean save(Long id) {
        try{

            OrderEntity orderEntity = orderRepository.findById(id).get();

            orderEntity.setStatus(false);
            orderRepository.save(orderEntity);

            return true;
        }
        catch (RuntimeException e)
        {
            return  false;
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


    public List<OrderDTO> findAllOrder(String username) {
        try {
            User user = userRepository.findByUsername(username).get();
            if (user == null) {
                throw new RuntimeException("Not found User");
            }
            List<OrderEntity> orderEntities = user.getOrders();
            ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
            orderEntities.stream().forEach(orderEntity -> {

                OrderDTO orderDTO = new OrderDTO();
                orderDTO = mapper.map(orderEntity, OrderDTO.class);
                ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
                orderEntity.getOrderDetails().stream().forEach(orderDetailEntity -> {
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                    orderDetailDTO.setImage(orderDetailEntity.getProduct().getImage());
                    orderDetailDTO.setProductName(orderDetailEntity.getProduct().getProductName());
                    orderDetailDTO.setQuantity(orderDetailEntity.getQuantity());
                    orderDetailDTO.setUnitPrice(orderDetailEntity.getUnitPrice());
                    orderDetailDTOS.add(orderDetailDTO);
                });

                orderDTO.setOrderDetailDTOS(orderDetailDTOS);
                orderDTO.setUserName(orderEntity.getUser().getUsername());
                if(orderEntity.getStatus().equals(true))
                {
                    orderDTOS.add(orderDTO);
                }
            });

        return orderDTOS;
    }
        catch (Error e)
        {
            throw new RuntimeException();
        }
    }


    public List<OrderDTO> findPenddingOrder(String username) {
        try {
            User user = userRepository.findByUsername(username).get();
            if (user == null) {
                throw new RuntimeException("Not found User");
            }
            List<OrderEntity> orderEntities = user.getOrders();
            ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
            orderEntities.stream().forEach(orderEntity -> {

                OrderDTO orderDTO = new OrderDTO();
                orderDTO = mapper.map(orderEntity, OrderDTO.class);
                ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
                orderEntity.getOrderDetails().stream().forEach(orderDetailEntity -> {
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                    orderDetailDTO.setImage(orderDetailEntity.getProduct().getImage());
                    orderDetailDTO.setProductName(orderDetailEntity.getProduct().getProductName());
                    orderDetailDTO.setQuantity(orderDetailEntity.getQuantity());
                    orderDetailDTO.setUnitPrice(orderDetailEntity.getUnitPrice());
                    orderDetailDTOS.add(orderDetailDTO);
                });
                orderDTO.setOrderDetailDTOS(orderDetailDTOS);
                orderDTO.setUserName(orderEntity.getUser().getUsername());
                if(orderEntity.getPayment().getStatus().equals("pendding") && orderEntity.getStatus().equals(true))
                {
                    orderDTOS.add(orderDTO);
                }
            });

            return orderDTOS;
        }
        catch (Error e)
        {
            throw new RuntimeException();
        }
    }

    public List<OrderDTO> findPenddingOrderAdmin() {
        try {


            List<OrderEntity> orderEntities = orderRepository.findAll();
            ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
            orderEntities.stream().forEach(orderEntity -> {

                OrderDTO orderDTO = new OrderDTO();
                orderDTO = mapper.map(orderEntity, OrderDTO.class);
                ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
                orderEntity.getOrderDetails().stream().forEach(orderDetailEntity -> {
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                    orderDetailDTO.setImage(orderDetailEntity.getProduct().getImage());
                    orderDetailDTO.setProductName(orderDetailEntity.getProduct().getProductName());
                    orderDetailDTO.setQuantity(orderDetailEntity.getQuantity());
                    orderDetailDTO.setUnitPrice(orderDetailEntity.getUnitPrice());
                    orderDetailDTOS.add(orderDetailDTO);
                });
                orderDTO.setOrderDetailDTOS(orderDetailDTOS);
                orderDTO.setUserName(orderEntity.getUser().getUsername());
                if(orderEntity.getPayment().getStatus().equals("pendding") && orderEntity.getStatus().equals(true))
                {
                    orderDTOS.add(orderDTO);
                }
            });

            return orderDTOS;
        }
        catch (Error e)
        {
            throw new RuntimeException();
        }
    }
    public List<OrderDTO> findPaidOrderAdmin() {
        try {

            List<OrderEntity> orderEntities = orderRepository.findAll();
            ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
            orderEntities.stream().forEach(orderEntity -> {

                OrderDTO orderDTO = new OrderDTO();
                orderDTO = mapper.map(orderEntity, OrderDTO.class);
                ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
                orderEntity.getOrderDetails().stream().forEach(orderDetailEntity -> {
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                    orderDetailDTO.setImage(orderDetailEntity.getProduct().getImage());
                    orderDetailDTO.setProductName(orderDetailEntity.getProduct().getProductName());
                    orderDetailDTO.setQuantity(orderDetailEntity.getQuantity());
                    orderDetailDTO.setUnitPrice(orderDetailEntity.getUnitPrice());
                    orderDetailDTOS.add(orderDetailDTO);
                });
                orderDTO.setOrderDetailDTOS(orderDetailDTOS);
                if(orderEntity.getPayment().getStatus().equals("paid") && orderEntity.getStatus().equals(true))
                {
                    orderDTOS.add(orderDTO);
                }
            });

            return orderDTOS;
        }
        catch (Error e)
        {
            throw new RuntimeException();
        }
    }
    public List<OrderDTO> findPaidOrder(String username) {
        try {
            User user = userRepository.findByUsername(username).get();
            if (user == null) {
                throw new RuntimeException("Not found User");
            }
            List<OrderEntity> orderEntities = user.getOrders();
            ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
            orderEntities.stream().forEach(orderEntity -> {

                OrderDTO orderDTO = new OrderDTO();
                orderDTO = mapper.map(orderEntity, OrderDTO.class);
                ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
                orderEntity.getOrderDetails().stream().forEach(orderDetailEntity -> {
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                    orderDetailDTO.setImage(orderDetailEntity.getProduct().getImage());
                    orderDetailDTO.setProductName(orderDetailEntity.getProduct().getProductName());
                    orderDetailDTO.setQuantity(orderDetailEntity.getQuantity());
                    orderDetailDTO.setUnitPrice(orderDetailEntity.getUnitPrice());
                    orderDetailDTOS.add(orderDetailDTO);
                });
                orderDTO.setOrderDetailDTOS(orderDetailDTOS);
                if(orderEntity.getPayment().getStatus().equals("paid") && orderEntity.getStatus().equals(true))
                {
                    orderDTOS.add(orderDTO);
                }
            });

            return orderDTOS;
        }
        catch (Error e)
        {
            throw new RuntimeException();
        }
    }
    @Override
    public boolean delete(List<Long> list) {
        return false;
    }
    public List<OrderDTO> findAll()
    {
        List<OrderDTO> result = new ArrayList<>();
        List<OrderEntity> orderEntities = orderRepository.findAll();
        orderEntities.stream().forEach(orderEntity -> {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO = mapper.map(orderEntity, OrderDTO.class);
            ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
            orderEntity.getOrderDetails().stream().forEach(orderDetailEntity -> {
                OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                orderDetailDTO.setImage(orderDetailEntity.getProduct().getImage());
                orderDetailDTO.setProductName(orderDetailEntity.getProduct().getProductName());
                orderDetailDTO.setQuantity(orderDetailEntity.getQuantity());
                orderDetailDTO.setUnitPrice(orderDetailEntity.getUnitPrice());
                orderDetailDTOS.add(orderDetailDTO);

        });

            orderDTO.setOrderDetailDTOS(orderDetailDTOS);
            orderDTO.setTotalPrice(orderEntity.getTotalDue());
            orderDTO.setUserName(orderEntity.getUser().getUsername());
            result.add(orderDTO);
        });
        return result;
    }
}

package com.cellphones10.service.impl;

import com.cellphones10.dto.CartDTO;
import com.cellphones10.dto.OrderDetailDTO;
import com.cellphones10.dto.output.OutputProductCart;
import com.cellphones10.entity.CartEntity;
import com.cellphones10.entity.OrderDetailEntity;
import com.cellphones10.entity.User;
import com.cellphones10.repository.CartRepository;
import com.cellphones10.repository.OrderdetailRepository;
import com.cellphones10.repository.ProductRepository;
import com.cellphones10.repository.UserRepository;
import com.cellphones10.service.ICartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {
    @Autowired
    private OrderdetailRepository orderdetailRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;
    @Override
    @Transactional
    public CartDTO save(CartDTO cartDTO) {

        User user = userRepository.findByUsername(cartDTO.getUserName()).get();
        if(user == null)
        {
            throw new RuntimeException("Not found user");
        }
        Optional<OrderDetailEntity> orderDetailEntity = orderdetailRepository.findById(cartDTO.getOrderDetailIds().get(0));
        if(orderDetailEntity.isEmpty())
        {
            throw  new RuntimeException();
        }
        CartEntity cartEntity = new CartEntity();
//        check cart exist


        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
        orderDetailEntity.get().setCart(cartEntity);
        if(user.getCart()!=null)
        {
            Optional<CartEntity> cartEntity1 = cartRepository.findById(user.getCart().getId());
            List<OrderDetailEntity> oldList = new ArrayList<>();
            oldList = cartEntity1.get().getOrderDetails();
            orderDetailEntityList = oldList;
            cartEntity.setId(user.getCart().getId());
        }

        cartEntity.setUser(user);
        orderDetailEntityList.add(orderDetailEntity.get());
        cartEntity.setOrderDetails(orderDetailEntityList);

        user.setCart(cartEntity);
        CartEntity saved = cartRepository.save(cartEntity);
        CartDTO result = new CartDTO();
        result = mapper.map(saved, CartDTO.class);
        result.setUserName(saved.getUser().getUsername());
        List<Long> orderDetailIds = new ArrayList<>();
        for (OrderDetailEntity order:saved.getOrderDetails()) {
            orderDetailIds.add(order.getId());
        }


        result.setOrderDetailIds(orderDetailIds);

     //  userRepository.save(user);
        return result;
    }

    @Override
    public List<CartDTO> findAll(Pageable pageable) {
        return null;
    }
    public List<OutputProductCart> findAll(Pageable pageable, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user == null)
        {
            throw new RuntimeException();
        }
        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
        CartEntity cart = user.get().getCart();
       List<OrderDetailEntity> orderDetailEntities = orderdetailRepository.findAllByCartId(cart.getId(), pageable);

        OutputProductCart outputProductCart = new OutputProductCart();

        orderDetailEntities.stream().forEach(orderDetailEntity -> {orderDetailEntityList.add(orderDetailEntity);});
        List<OutputProductCart> outputProductCarts = new ArrayList<>();
        orderDetailEntityList.stream().forEach(orderDetailEntity -> {
         OrderDetailDTO orderDetailDTO = mapper.map(orderDetailEntity, OrderDetailDTO.class);

         outputProductCart.setProductName( orderDetailEntity.getProduct().getProductName());
         outputProductCart.setPrice(orderDetailEntity.getProduct().getPrice());
         outputProductCart.setImage(orderDetailEntity.getProduct().getImage());
         outputProductCart.setId(orderDetailDTO.getId());
            outputProductCart.setQuantity(orderDetailDTO.getQuantity());
            outputProductCart.setUnitPrice(orderDetailDTO.getUnitPrice());
            outputProductCarts.add(outputProductCart);
        });
        return outputProductCarts;
    }

    @Override
    public boolean delete(List<Long> list) {
        return false;
    }

    public Long count(String username)
    {
        Optional<User> user = userRepository.findByUsername(username);
        if(user == null)
        {
            throw new RuntimeException();
        }
        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
        CartEntity cart = user.get().getCart();
        List<OrderDetailEntity> orderDetailEntities = orderdetailRepository.findAllByCartId(cart.getId());
        return orderDetailEntities.stream().count();
    }
    @Transactional
    public boolean deleteProductFromCart(List<Long> ids, String username)
    {
        Integer count = ids.size();
        Optional<User> user = userRepository.findByUsername(username);
        if(user == null)
        {
            throw  new RuntimeException("Not found user");
        }

        CartEntity cartEntity = user.get().getCart();
        for (Long id:ids) {
            Optional<OrderDetailEntity> orderDetailEntity = orderdetailRepository.findById(id);
            if(orderDetailEntity == null)
            {
                throw  new RuntimeException("Not found orderdetail");
            }
            if(cartEntity.getOrderDetails().contains(orderDetailEntity))
            {
                orderdetailRepository.deleteById(id);
                count --;
            }
        }
        if(count == 0)
        {
            return true;

        }
        cartRepository.save(cartEntity);
        return false;
    }
}

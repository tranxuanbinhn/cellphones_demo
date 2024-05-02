package com.cellphones10.service.impl;

import com.cellphones10.dto.CartDTO;
import com.cellphones10.dto.OrderDetailDTO;
import com.cellphones10.dto.output.OutputProductCart;
import com.cellphones10.entity.*;
import com.cellphones10.repository.*;
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
import java.util.Set;

@Service
public class CartService implements ICartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartProductRespository cartProductRespository;

    @Autowired
    private CartRepository cartRepository;
    @Override
    @Transactional
    public CartDTO save(CartDTO cartDTO) {

        Optional<User> user = userRepository.findByUsername(cartDTO.getUserName());
        if(!user.isPresent())
        {
            throw new RuntimeException("Not found user");
        }
        CartEntity cartEntity;
        List<CartProduct> cartProducts = new ArrayList<>();
        if(user.get().getCart() == null)
        {
             cartEntity = new CartEntity();
             cartEntity.setUser(user.get());

        }
        else {
            cartEntity = user.get().getCart();
            cartProducts = cartEntity.getCartProducts();

        }
        Optional<ProductEntity> productEntity = productRepository.findById(cartDTO.getProductId());

        Set<Long> idProducts = cartProductRespository.getIdByUsername(user.get().getUsername());
        CartProduct cartProduct = new CartProduct();
        if(idProducts.contains(productEntity.get().getId()))
        {
            cartProduct = cartProductRespository.findByProductId(productEntity.get().getId()).get();
            int oldQuantity = cartProduct.getQuantity();
            cartProduct.setQuantity(cartDTO.getQuantityProduct());
        }

        cartProduct.setQuantity(cartDTO.getQuantityProduct());
        cartProduct.setCart(cartEntity);
        cartProduct.setProduct(productEntity.get());

        cartProducts.add(cartProduct);
        cartEntity.setCartProducts(cartProducts);


       CartEntity result = cartRepository.save(cartEntity);
        CartProduct cartProduct1 = cartProductRespository.save(cartProduct);
        user.get().setCart(cartEntity);
        userRepository.save(user.get());
        CartDTO cartDTO1 = new CartDTO();

        cartDTO1.setQuantityProduct(cartProduct1.getQuantity());
        cartDTO1.setUserName(user.get().getUsername());
        List<Long> ids = new ArrayList<>();
        result.getCartProducts().stream().forEach(cartProduct2 -> {
            ids.add(cartProduct2.getId());
        });
        cartDTO1.setProductListId(ids);

        return  cartDTO1;
    }

    @Override
    public List<CartDTO> findAll(Pageable pageable) {
        return null;
    }
//    public List<OutputProductCart> findAll(Pageable pageable, String username) {
//        Optional<User> user = userRepository.findByUsername(username);
//        if(user == null)
//        {
//            throw new RuntimeException();
//        }
//        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
//        CartEntity cart = user.get().getCart();
//       List<OrderDetailEntity> orderDetailEntities = orderdetailRepository.findAllByCartId(cart.getId(), pageable);
//
//        OutputProductCart outputProductCart = new OutputProductCart();
//
//        orderDetailEntities.stream().forEach(orderDetailEntity -> {orderDetailEntityList.add(orderDetailEntity);});
//        List<OutputProductCart> outputProductCarts = new ArrayList<>();
//        orderDetailEntityList.stream().forEach(orderDetailEntity -> {
//         OrderDetailDTO orderDetailDTO = mapper.map(orderDetailEntity, OrderDetailDTO.class);
//
//         outputProductCart.setProductName( orderDetailEntity.getProduct().getProductName());
//         outputProductCart.setPrice(orderDetailEntity.getProduct().getPrice());
//         outputProductCart.setImage(orderDetailEntity.getProduct().getImage());
//         outputProductCart.setId(orderDetailDTO.getId());
//            outputProductCart.setQuantity(orderDetailDTO.getQuantity());
//            outputProductCart.setUnitPrice(orderDetailDTO.getUnitPrice());
//            outputProductCarts.add(outputProductCart);
//        });
//        return outputProductCarts;
//    }
//
    @Override
    public boolean delete(List<Long> list) {
        return false;
    }
//
//    public Long count(String username)
//    {
//        Optional<User> user = userRepository.findByUsername(username);
//        if(user == null)
//        {
//            throw new RuntimeException();
//        }
//        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
//        CartEntity cart = user.get().getCart();
//        List<OrderDetailEntity> orderDetailEntities = orderdetailRepository.findAllByCartId(cart.getId());
//        return orderDetailEntities.stream().count();
//    }


}

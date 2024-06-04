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

    @Transactional
    public CartDTO save(CartDTO cartDTO, String username) {

        //find user
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent())
        {
            throw new RuntimeException("Not found user");
        }
        CartEntity cartEntity;
        //get cartProduct by user
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
            if(cartDTO.getAdd())
            {
                cartProduct.setQuantity(oldQuantity+ cartDTO.getQuantityProduct());

            }
            else {
                cartProduct.setQuantity(oldQuantity- cartDTO.getQuantityProduct());

            }
        }
        else {
            cartProduct.setQuantity(cartDTO.getQuantityProduct());
        }


        cartProduct.setCart(cartEntity);
        cartProduct.setProduct(productEntity.get());

        cartProducts.add(cartProduct);
        cartEntity.setCartProducts(cartProducts);



        CartProduct cartProduct1 = cartProductRespository.save(cartProduct);
        user.get().setCart(cartEntity);
        userRepository.save(user.get());
        CartDTO cartDTO1 = new CartDTO();

        CartEntity result = cartRepository.save(cartEntity);
        cartDTO1.setQuantityProduct(cartProduct1.getQuantity());
        cartDTO1.setUserName(user.get().getUsername());
        List<Long> ids = new ArrayList<>();
        idProducts.stream().forEach(id->{
            ids.add(id);
        });
        cartDTO1.setProductListId(ids);
        cartDTO1.setId(result.getId());
        return  cartDTO1;
    }

    @Override
    public CartDTO save(CartDTO cartDTO) {
        return null;
    }

    @Override
    public List<CartDTO> findAll(Pageable pageable) {
        return null;
    }
    public List<OutputProductCart> findAll( String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user == null)
        {
            throw new RuntimeException();
        }
        CartEntity cartEntity = user.get().getCart();

        List<CartProduct> cartProductList = cartProductRespository.findAllByCartId(cartEntity.getId());
        List<OutputProductCart> result = new ArrayList<>();
        cartProductList.stream().forEach(cartProduct -> {
            OutputProductCart object = new OutputProductCart();
            object.setPrice(cartProduct.getProduct().getPrice());
            object.setProductId(cartProduct.getProduct().getId());
            object.setQuantity(cartProduct.getQuantity());
            object.setCartProductId(cartProduct.getId());
            object.setProductName(cartProduct.getProduct().getProductName());
            object.setImage(cartProduct.getProduct().getImage());

            result.add(object);
        });
        return result;
    }

    @Override
    @Transactional
    public boolean delete(List<Long> list) {
        Integer count = list.size();
        for (Long id:list) {

            if(cartProductRespository.existsById(id))
            {
                cartProductRespository.deleteById(id);
                count --;
            }


        }
        if(count == 0)
        {
            return  true;
        }
        return  false;
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

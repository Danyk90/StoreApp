package com.ecom.store.service;

import com.ecom.store.dto.CartDto;
import com.ecom.store.dto.ItemsDto;
import com.ecom.store.exceptions.ItemAlreadyExistException;
import com.ecom.store.exceptions.ItemNotFoundException;
import com.ecom.store.exceptions.UserNotFoundException;
import com.ecom.store.models.Carts;
import com.ecom.store.models.Items;
import com.ecom.store.models.Orders;
import com.ecom.store.models.User;
import com.ecom.store.repository.CartRepository;
import com.ecom.store.repository.OrderRepository;
import com.ecom.store.repository.UserRepository;
import com.ecom.store.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartService {


    @Autowired
    CartRepository cartRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    JwtUtils jwtUtils;

    public void saveCart(CartDto cartDto, HttpServletRequest httpServletRequest) {
        //todo validate item request
        Carts cart = modelMapper.map(cartDto, Carts.class);
        User user = getUserByToken(httpServletRequest.getHeader("AUTHORIZATION"));
        cart.setUserId(user.getId());
        if (cart.getCartId() == null) { //saviing new cart
            Optional<Carts> existingCart = cartRepository.getByUserId(user.getId());
            if (existingCart.isPresent())
                throw new ItemAlreadyExistException("cart already exist");
            else
                cartRepository.save(cart);
        } else { //update in existing cart

            Optional<Carts> existingCart = cartRepository.getByUserId(cart.getUserId());
            if (existingCart.isPresent()) {
                if (!cart.getItemsInCart().isEmpty()) {
                    existingCart.get().setItemsInCart(cart.getItemsInCart());
                    cartRepository.save(cart);
                } else
                    throw new ItemNotFoundException("No item To add");
            } else
                throw new ItemNotFoundException("cart does not exist");
            List<ItemsDto> itemsList = cartDto.getItemsInCart();
        }
    }

    public void deleteCart(CartDto cartDto, HttpServletRequest httpServletRequest) {

        log.info("delete cart with cart id ==" + cartDto.getCartId());
        String[] token = httpServletRequest.getHeader("AUTHORIZATION").split(" ");
        String userName = jwtUtils.getUserNameFromJwtToken(token[1]);
        User user = getUserByToken(httpServletRequest.getHeader("AUTHORIZATION"));
        Optional<Carts> existingCart = cartRepository.getByUserId(user.getId());
        if (existingCart.get().getCartId() == cartDto.getCartId())
            cartRepository.deleteById(cartDto.getCartId());

    }

    public void updateItem(CartDto cartDto) {
        //todo validate item request
        Carts items = modelMapper.map(cartDto, Carts.class);
        if (items.getCartId() == null)
            throw new ItemNotFoundException("Item id not present ");
        else
            cartRepository.save(items);

    }

    public User getUserByToken(String token) {
        String[] splitToken = token.split(" ");
        String userName = jwtUtils.getUserNameFromJwtToken(splitToken[1]);
        Optional<User> user = userRepository.findByUsername(userName);
        if (user.isPresent())
            return user.get();
        else
            throw new UserNotFoundException("No user found against this token");
    }

    public ResponseEntity orderCartItems(HttpServletRequest httpServletRequest) {

        User user = getUserByToken(httpServletRequest.getHeader("AUTHORIZATION"));
        log.info("placing order for user " + user.getUsername());
        Optional<Carts> carts = cartRepository.getByUserId(user.getId());
        List<Items> itemInCarts = carts.get().getItemsInCart();
        log.info("placing order cart id is " + carts.get().getCartId());
        if (carts.isPresent()) {

            UUID uuid = UUID.randomUUID();
            itemInCarts.forEach(items -> {
                //saving all items against common order uuid
                orderRepository.save(Orders.builder().userId(user.getId())
                        .orderUuid(uuid.toString())
                        .itemId(items.getItemId()).build());
            });

            cartRepository.deleteById(carts.get().getCartId()); //cart deleted once order placed
            return new ResponseEntity(HttpStatus.CREATED);
        } else
            throw new ItemNotFoundException("No Cart Found");
    }
}

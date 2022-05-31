package com.ecom.store.controllers;


import com.ecom.store.dto.CartDto;
import com.ecom.store.dto.ItemsDto;
import com.ecom.store.exception.TokenRefreshException;
import com.ecom.store.models.ERole;
import com.ecom.store.models.RefreshToken;
import com.ecom.store.models.Role;
import com.ecom.store.models.User;
import com.ecom.store.payload.request.LogOutRequest;
import com.ecom.store.payload.request.LoginRequest;
import com.ecom.store.payload.request.SignupRequest;
import com.ecom.store.payload.request.TokenRefreshRequest;
import com.ecom.store.payload.response.JwtResponse;
import com.ecom.store.payload.response.MessageResponse;
import com.ecom.store.payload.response.TokenRefreshResponse;
import com.ecom.store.repository.RoleRepository;
import com.ecom.store.repository.UserRepository;
import com.ecom.store.security.jwt.JwtUtils;
import com.ecom.store.security.services.RefreshTokenService;
import com.ecom.store.security.services.UserDetailsImpl;
import com.ecom.store.service.CartService;
import com.ecom.store.service.ItemService;
import com.ecom.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;
    @Autowired
    HttpServletRequest httpServletRequest;


    @PostMapping("/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        return userService.login(loginRequest);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        return userService.registerUser(signUpRequest);
    }

    @PostMapping("/auth/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        return userService.refreshToken(request);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        return userService.logout(logOutRequest);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/items")
    ResponseEntity getAllActiveItems() {
        return new ResponseEntity(itemService.getAllItems(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-item")
    ResponseEntity saveItem(@RequestBody ItemsDto itemsDto) {
        log.info("--save new item");
        itemService.saveitem(itemsDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/update-item")
    ResponseEntity updateItem(@RequestBody ItemsDto itemsDto) {

        itemService.updateItem(itemsDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete-item")
    ResponseEntity deleteItem(@RequestParam("id") Long id) {

        itemService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    @PostMapping("/cart/add")
    public ResponseEntity AddToCart(@RequestBody CartDto cartDto) {

        cartService.saveCart(cartDto, httpServletRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    @DeleteMapping("/cart/delete")
    public ResponseEntity DeleteCartItem(@RequestBody CartDto cartDto) {
        log.info("-delete cart items");
        cartService.deleteCart(cartDto, httpServletRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/store/users")
    public ResponseEntity fetchAllUsers() {

        log.info("-fetching all users.");
        return new ResponseEntity(userService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    @PostMapping("/cart/order")
    public ResponseEntity OrderCart() {

        log.info("-order cart items");
        return cartService.orderCartItems(httpServletRequest);
    }

}

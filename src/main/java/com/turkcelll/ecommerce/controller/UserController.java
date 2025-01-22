package com.turkcelll.ecommerce.controller;

import com.turkcelll.ecommerce.dto.UserLoginDto;
import com.turkcelll.ecommerce.dto.UserRegisterDto;
import com.turkcelll.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        userService.registerUser(userRegisterDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        String token = userService.loginUser(userLoginDto);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
    }

    @GetMapping("/products/list")
    public ResponseEntity<Void> getAll(){
        return ResponseEntity.ok().build();
    }

    }


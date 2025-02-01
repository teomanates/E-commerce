package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.dto.UserLoginDto;
import com.turkcelll.ecommerce.dto.UserRegisterDto;
import com.turkcelll.ecommerce.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);
    String loginUser(UserLoginDto userLoginDto);
}

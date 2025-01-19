package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.dto.UserLoginDto;
import com.turkcelll.ecommerce.dto.UserRegisterDto;
import com.turkcelll.ecommerce.dto.UserResponseDto;

import java.util.Optional;

public interface UserService {
    String registerUser(UserRegisterDto userRegisterDto); //string mi void mi olmali
    String loginUser(UserLoginDto userLoginDto); //loginde optional mı yoksa string mi dönmeli?

}

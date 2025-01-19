package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.dto.UserLoginDto;
import com.turkcelll.ecommerce.dto.UserRegisterDto;
import com.turkcelll.ecommerce.dto.UserResponseDto;
import com.turkcelll.ecommerce.entity.User;
import com.turkcelll.ecommerce.repository.UserRepository;
import com.turkcelll.ecommerce.rules.UserRules;
import com.turkcelll.ecommerce.util.JwtUtil;
import com.turkcelll.ecommerce.util.PasswordUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@NoArgsConstructor  // ????
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final SecurityUtil securityUtil; //TODO: util içinde eklencek
    private final JwtUtil jwtUtil =;  // TODO: util içinde eklencek
    private final UserRules userRules; //TODO: eklencek

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
        this.jwtUtil = jwtUtil;
        this.userRules = userRules;
    }

    // TODO: rules icerisinde mailcheck eklenebilir.
    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {
        userRules.checkEmailExists(userRegisterDto.getEmail());
        userRules.validateEmail(userRegisterDto.getEmail());
        userRules.validatePassword(userRegisterDto.getPassword());

        // TODO: exceptionlar rule icine yazilacak

        User user = new User();
        user.setFirstName(userRegisterDto.getFirstName());
        user.setLastName(userRegisterDto.getLastName());
        user.setEmail(userRegisterDto.getEmail());
        user.setHashedPassword(PasswordUtil.hashPassword(userRegisterDto.getPassword())); // TODO: utils icinde hash oluşturup securtiyUtil.encodePassword ile setlicez.

        userRepository.save(user);
        return "User registered successfully";

        // TODO: return değeri nasil olmali arastir, mapstruct?, mapToUserResponseDto?
        // void yaptım hoca böyle yapmıştı, map göstermedi
        // return ?
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {
        userRules.checkEmailExists(userLoginDto.getEmail());

        User dbUser = userRepository
                .findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or wrong password.")); // ?????????
        if(!PasswordUtil.verifyPassword(userLoginDto.getPassword(), dbUser.getHashedPassword())){
            throw new RuntimeException("Invalid email or password");
        }

        return new JwtUtil().generateToken(user.getEmail());

        // TODO: exceptionlar rule icine yazilacak
        // TODO : securityUtil ile pass matchine bakicaz
        // TODO: token üretmeliyiz ??

        // return tokeni dönmeliyiz
    }
}

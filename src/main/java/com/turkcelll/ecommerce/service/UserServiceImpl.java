package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.dto.UserLoginDto;
import com.turkcelll.ecommerce.dto.UserRegisterDto;
import com.turkcelll.ecommerce.entity.User;
import com.turkcelll.ecommerce.repository.UserRepository;
import com.turkcelll.ecommerce.rules.UserRules;
import com.turkcelll.ecommerce.util.JwtUtil;
import com.turkcelll.ecommerce.util.SecurityUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserRules userRules;

    private final SecurityUtil securityUtil;

    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, UserRules userRules, SecurityUtil securityUtil, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userRules = userRules;
        this.securityUtil = securityUtil;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void  registerUser(UserRegisterDto userRegisterDto) {

        if(userRepository.findByEmail(userRegisterDto.getEmail()).isPresent()){
            throw new RuntimeException("Email Already Exists");
        }

        User dbUser = new User();
        dbUser.setFirstName(userRegisterDto.getFirstName());
        dbUser.setLastName(userRegisterDto.getLastName());
        dbUser.setEmail(userRegisterDto.getEmail());
        dbUser.setPassword(securityUtil.hashPassword(userRegisterDto.getPassword())); //

        userRepository.save(dbUser);
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {
        User dbUser = userRepository
                .findByEmail(userLoginDto.getEmail())
                .orElseThrow(()-> new RuntimeException("Invalid email or password"));

        if(!securityUtil.checkPassword(userLoginDto.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(dbUser.getEmail());

    }
}

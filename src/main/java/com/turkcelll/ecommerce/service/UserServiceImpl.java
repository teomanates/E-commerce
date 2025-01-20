package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.dto.UserLoginDto;
import com.turkcelll.ecommerce.dto.UserRegisterDto;
import com.turkcelll.ecommerce.entity.User;
import com.turkcelll.ecommerce.repository.UserRepository;
import com.turkcelll.ecommerce.rules.UserRules;
import com.turkcelll.ecommerce.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserRules userRules;

    private final SecurityUtil securityUtil;

    public UserServiceImpl(UserRepository userRepository, UserRules userRules, SecurityUtil securityUtil) {
        this.userRepository = userRepository;
        this.userRules = userRules;
        this.securityUtil = securityUtil;
    }


    // TODO: rules icerisinde mailcheck eklenebilir.
    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {

        if(userRepository.findByEmail(userRegisterDto.getEmail()).isPresent()){
            throw new RuntimeException("Email Already Exists");
        }

        userRules.validateEmail(userRegisterDto.getEmail());
        userRules.validatePassword(userRegisterDto.getPassword());

        String hashedPassword = securityUtil.hashPassword(userRegisterDto.getPassword());

        User dbUser = new User();
        dbUser.setFirstName(userRegisterDto.getFirstName());
        dbUser.setLastName(userRegisterDto.getLastName());
        dbUser.setEmail(userRegisterDto.getEmail());
        dbUser.setPassword(hashedPassword); //

        userRepository.save(dbUser); // TODO: return değeri nasil olmali arastir, mapstruct?, mapToUserResponseDto?
        return "User registered successfully with email: " + dbUser.getEmail();
    }



    @Override
    public String loginUser(UserLoginDto userLoginDto) {//
        userRules.validateEmail(userLoginDto.getEmail());

        User dbUser = userRepository
                .findByEmail(userLoginDto.getEmail())
                .orElseThrow(()-> new RuntimeException("Invalid email or password"));

        if(!securityUtil.checkPassword(userLoginDto.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return "Login successful";

    }
}

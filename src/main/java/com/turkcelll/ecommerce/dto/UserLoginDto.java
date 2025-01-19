package com.turkcelll.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDto {
    @Email(message = "Invalid type")
    private String email;
    private String password;
}

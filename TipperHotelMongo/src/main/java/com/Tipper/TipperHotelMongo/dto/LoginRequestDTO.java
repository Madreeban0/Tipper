package com.Tipper.TipperHotelMongo.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message="Email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
}
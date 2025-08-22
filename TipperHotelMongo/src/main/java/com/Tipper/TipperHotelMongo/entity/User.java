package com.Tipper.TipperHotelMongo.entity;

import java.util.*;


import com.Tipper.TipperHotelMongo.dto.UserDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.mongodb.core.mapping.DBRef;

import jakarta.validation.constraints.NotBlank;



@Data
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;
    @NotBlank(message = "Email is required")
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private String role;
    @DBRef
    private List<Bookings> bookings = new ArrayList<>();

    // UserDetails methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // or provide roles if needed
    }

    @Override
    public String getUsername() {
        return email; // or return name if that’s your username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString(){
        return "User{" +
    "id='" + id + '\'' +
    ", email='" + email + '\'' +
    ", name='" + name + '\'' +
    ", phoneNumber='" + phoneNumber + '\'' +
    ", password='" + password + '\'' +
    ", role='" + role + '\'' +
    '}';

    }
}
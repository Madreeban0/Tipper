package com.Tipper.TipperHotelMongo.dto;


import com.Tipper.TipperHotelMongo.entity.Bookings;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserDTO {
    private String id;
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private String role;
    private List<Bookings> bookings = new ArrayList<>();
}
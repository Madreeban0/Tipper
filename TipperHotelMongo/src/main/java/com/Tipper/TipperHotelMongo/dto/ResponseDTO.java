package com.Tipper.TipperHotelMongo.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String bookingsConfirmationCode;

    private UserDTO user;
    private RoomDTO rooms;
    private BookingsDTO bookings;
    private List<UserDTO> userList;
    private List<RoomDTO> roomsList;
    private List<BookingsDTO> bookingsList;
}
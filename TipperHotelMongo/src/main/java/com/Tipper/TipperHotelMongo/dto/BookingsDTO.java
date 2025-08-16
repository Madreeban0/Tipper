package com.Tipper.TipperHotelMongo.dto;


import com.Tipper.TipperHotelMongo.entity.Rooms;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.catalina.User;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingsDTO {
    private String id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int noOfAdults;
    private int noOfChildren;
    private int totalNumOfGuest;
    private String bookingConfirmationCode;
    private Rooms room;
    private User user;
}
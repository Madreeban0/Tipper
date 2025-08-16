package com.Tipper.TipperHotelMongo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "rooms")
public class Rooms {
    private String id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String description;

    @DBRef
    private List<Bookings> bookings = new ArrayList<>();

    @Override
    public String toString() {
        return"Rooms{" +
                "id=" + id +
                ",roomType='"+ roomType + '\''+
                ",roomPrice='" + roomPrice +
                ",roomPhotoUrl='" + roomPhotoUrl+'\''+
                ",description='" + description + '\'' +
                '}';


    }
}

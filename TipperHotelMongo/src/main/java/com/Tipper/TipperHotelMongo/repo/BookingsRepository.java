package com.Tipper.TipperHotelMongo.repo;

import com.Tipper.TipperHotelMongo.entity.Bookings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingsRepository extends MongoRepository<Bookings,String> {

    Optional<Bookings> findByBookingconfirmationcode(String confirmationCode);

    @Query("{'checkInDate' : {$lte : ?1} , 'checkOutDate' : {$gte : ?0}}")
    List<Bookings> findBookingsByDataRange(LocalDate checkInDate, LocalDate checkOutData);
    
}
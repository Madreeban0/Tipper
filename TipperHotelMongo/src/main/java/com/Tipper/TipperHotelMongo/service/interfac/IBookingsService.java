package com.Tipper.TipperHotelMongo.service.interfac;

import com.Tipper.TipperHotelMongo.dto.ResponseDTO;
import com.Tipper.TipperHotelMongo.entity.Bookings;

public interface IBookingsService {
    ResponseDTO saveBookings(String roomId, String userId, Bookings bookingsRequest);

    

    ResponseDTO findBookingByConfirmationCode(String confirmationcode);

    ResponseDTO getAllBookings();

    ResponseDTO cancelBookings(String bookingId);
}
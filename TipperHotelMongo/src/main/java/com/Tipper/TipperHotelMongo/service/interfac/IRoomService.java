package com.Tipper.TipperHotelMongo.service.interfac;

import com.Tipper.TipperHotelMongo.dto.ResponseDTO;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    ResponseDTO addNewRoom( String roomType, BigDecimal roomPrice, String description);

    List<String> getAllRoomType();

    ResponseDTO getAllRooms();

    ResponseDTO deleteRooms(String roomId);

    ResponseDTO updateRooms(String roomId, String roomType, String description, BigDecimal roomPrice, MultipartFile photo);

    ResponseDTO getAllAvailableRooms();

    ResponseDTO getRoomById(String roomId);

    ResponseDTO getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
package com.Tipper.TipperHotelMongo.utils;

import com.Tipper.TipperHotelMongo.dto.BookingsDTO;
import com.Tipper.TipperHotelMongo.dto.RoomDTO;
import com.Tipper.TipperHotelMongo.dto.UserDTO;
import com.Tipper.TipperHotelMongo.entity.Bookings;
import com.Tipper.TipperHotelMongo.entity.Rooms;
import com.Tipper.TipperHotelMongo.entity.User;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static UserDTO mapUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTO(Rooms rooms) {
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId(rooms.getId());
        roomDTO.setRoomType(rooms.getRoomType());
        roomDTO.setRoomPrice(rooms.getRoomPrice());
        roomDTO.setRoomPhotoUrl(rooms.getRoomPhotoUrl());
        roomDTO.setDescription(rooms.getDescription());
        return roomDTO;
    }

    public static BookingsDTO mapBookingEntityToBookingDTO(Bookings bookings) {
        BookingsDTO bookingsDTO = new BookingsDTO();

        bookingsDTO.setId(bookings.getId());
        bookingsDTO.setCheckInDate(LocalDate.parse(bookings.getCheckInDate()));
        bookingsDTO.setCheckOutDate(LocalDate.parse(bookings.getCheckOutDate()));
        bookingsDTO.setNoOfChildren(bookings.getNoOfChildren());
        bookingsDTO.setNoOfAdults(bookings.getNoOfAdults());
        bookingsDTO.setTotalNumOfGuest(bookings.getTotalNumOfGuest());
        bookingsDTO.setBookingConfirmationCode(bookings.getBookingConfirmationCode());
        return bookingsDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTOPlusBookings(Rooms rooms) {
        RoomDTO roomDTO = mapRoomEntityToRoomDTO(rooms);

        if (rooms.getBookings() != null) {
            roomDTO.setBookings(rooms.getBookings().stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
        }

        return roomDTO;
    }

    public static BookingsDTO mapBookingEntityToBookingDTOPlusBookedRooms(Bookings bookings, boolean mapUser) {
        BookingsDTO bookingsDTO = mapBookingEntityToBookingDTO(bookings);

        if (mapUser) {
            bookingsDTO.setUser((org.apache.catalina.User) Utils.mapUserEntityToUserDTO(bookings.getUser()));
        }

        if (bookings.getRoom() != null) {

            RoomDTO roomDTO = new RoomDTO();

            roomDTO.setId(bookings.getRoom().getId());
            roomDTO.setRoomType(bookings.getRoom().getRoomType());
            roomDTO.setRoomPrice(bookings.getRoom().getRoomPrice());
            roomDTO.setRoomPhotoUrl(bookings.getRoom().getRoomPhotoUrl());
            roomDTO.setDescription(bookings.getRoom().getDescription());

            bookingsDTO.setRoom(roomDTO);
        }

        return bookingsDTO;
    }

    public static UserDTO mapUserEntityToUserDTOPlusUserBookingsAndRoom(User user) {
        UserDTO userDTO = mapUserEntityToUserDTO(user);

        if (!user.getBookings().isEmpty()) {
            userDTO.setBookings(user.getBookings().stream().map(booking -> mapBookingEntityToBookingDTOPlusBookedRooms(booking, false)).collect(Collectors.toList()));
        }
        return userDTO;
    }

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<RoomDTO> mapRoomListEntityToRoomListDTO(List<Rooms> roomList) {
        return roomList.stream().map(Utils::mapRoomEntityToRoomDTO).collect(Collectors.toList());
    }

    public static List<BookingsDTO> mapBookingListEntityToBookingListDTO(List<Bookings> bookingList) {
        return bookingList.stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList());
    }

}
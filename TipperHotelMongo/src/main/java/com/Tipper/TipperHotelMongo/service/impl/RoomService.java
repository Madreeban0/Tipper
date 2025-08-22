package com.Tipper.TipperHotelMongo.service.impl;

import com.Tipper.TipperHotelMongo.dto.ResponseDTO;
import com.Tipper.TipperHotelMongo.dto.RoomDTO;
import com.Tipper.TipperHotelMongo.entity.Bookings;
import com.Tipper.TipperHotelMongo.entity.Rooms;
import com.Tipper.TipperHotelMongo.exception.OurException;
import com.Tipper.TipperHotelMongo.repo.BookingsRepository;
import com.Tipper.TipperHotelMongo.repo.RoomsRepository;
import com.Tipper.TipperHotelMongo.service.interfac.IRoomService;
import com.Tipper.TipperHotelMongo.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
public class RoomService implements IRoomService {
    private final RoomsRepository roomsRepository;
    private final BookingsRepository bookingsRepository;

    public RoomService(BookingsRepository bookingsRepository, RoomsRepository roomsRepository) {
        this.bookingsRepository = bookingsRepository;
        this.roomsRepository = roomsRepository;
    }


    @Override
    public ResponseDTO addNewRoom(String roomType, BigDecimal roomPrice, String description){
        ResponseDTO response = new ResponseDTO();

        try{
            Rooms rooms = new Rooms();
            rooms.setRoomType(roomType);
            rooms.setRoomPrice(roomPrice);
            rooms.setDescription(description);

            Rooms savedRoo = roomsRepository.save(rooms);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoo);

            response.setStatusCode(200);
            response.setMessage("New room sucessfully added");
            response.setRooms(roomDTO);
        } catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while adding new room"+e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllRoomType() {
        return roomsRepository.findDistinctRoomType();
    }

    @Override
    public ResponseDTO getAllRooms() {
        ResponseDTO response = new ResponseDTO();

        try{
            List<Rooms> roomsList = roomsRepository.findAll();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomsList);
            response.setStatusCode(200);
            response.setMessage("Successfully fetched all rooms");
            response.setRoomsList(roomDTOList);
        } catch(Exception e){
            response.setStatusCode(404);
            response.setMessage("Error while fetching all rooms"+e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO deleteRooms(String roomId) {
        ResponseDTO response = new ResponseDTO();
        try{
            roomsRepository.findById(roomId).orElseThrow(()-> new OurException("Room not found"));
            roomsRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("Room deleted sucessfully");

        } catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while deleting room"+e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO updateRooms(String roomId, String roomType, String description, BigDecimal roomPrice, MultipartFile photo) {
        ResponseDTO response = new ResponseDTO();
        try{
            Rooms rooms = roomsRepository.findById(roomId).orElseThrow(()-> new OurException("Room not found"));
            if(roomType != null){
                rooms.setRoomType(roomType);
            } if(roomPrice != null){
                rooms.setRoomPrice(roomPrice);
            } if(description != null){
                rooms.setDescription(description);
            }

            Rooms updatedRoom = roomsRepository.save(rooms);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            response.setStatusCode(200);
            response.setMessage("Successfully updated your room");
            response.setRooms(roomDTO);

        } catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while updating your room");
        }
        return response;
    }

    @Override
    public ResponseDTO getAllAvailableRooms() {

        ResponseDTO response = new ResponseDTO();
        try{
            List<Rooms> roomsList = roomsRepository.findAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomsList);
            response.setStatusCode(200);
            response.setMessage("Successfully fetched all available rooms");
            response.setRooms((RoomDTO) roomDTOList);
        } catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while fetching available rooms");
        }
        return response;
    }

    @Override
    public ResponseDTO getRoomById(String roomId) {
        ResponseDTO response = new ResponseDTO();

        try{
            Rooms rooms = roomsRepository.findById(roomId).orElseThrow(()-> new OurException("Room not found"));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(rooms);

            response.setStatusCode(200);
            response.setMessage("Room found");
            response.setRooms(roomDTO);
        } catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while fetching room"+e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        ResponseDTO response = new ResponseDTO();

        try{
            List<Bookings> bookingsList = bookingsRepository.findBookingsByDataRange(checkInDate,checkOutDate);
            List<String> bookedRoomsId = bookingsList.stream().map(bookings -> bookings.getRoom().getId()).toList();

            List<Rooms> roomsList = roomsRepository.findByRoomIdAndIdNotIn(roomType, bookedRoomsId);
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomsList);

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setRooms((RoomDTO) roomDTOList);
        } catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while getting available rooms by date range");
        }
        return response;
    }

}
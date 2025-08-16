package com.Tipper.TipperHotelMongo.repo;

import com.Tipper.TipperHotelMongo.entity.Rooms;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RoomsRepository extends MongoRepository<String, Rooms> {
    @Aggregation("{$group : {_id : RoomType}}")
    List<String> findDistinctRoomType();
    @Query("{$bookings : {$size : 0}")
    List<String> findAllAvailableRooms();

    List<String> findByRoomIdAndIdNotIn(String rooms, List<String> bookedRoomIds);

}
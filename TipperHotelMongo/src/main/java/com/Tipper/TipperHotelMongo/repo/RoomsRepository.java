package com.Tipper.TipperHotelMongo.repo;

import com.Tipper.TipperHotelMongo.entity.Rooms;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RoomsRepository extends MongoRepository<Rooms, String> {
    @Aggregation("{$group : {_id : RoomType}}")
    List<String> findDistinctRoomType();
    @Query("{$bookings : {$size : 0}")
    List<Rooms> findAllAvailableRooms();

    List<Rooms> findByRoomIdAndIdNotIn(String rooms, List<String> bookedRoomIds);

}
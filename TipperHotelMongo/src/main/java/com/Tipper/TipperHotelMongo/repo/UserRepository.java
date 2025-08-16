package com.Tipper.TipperHotelMongo.repo;

import com.Tipper.TipperHotelMongo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existByEmail(String email);

    Optional<User> findByEmail(String email);
}
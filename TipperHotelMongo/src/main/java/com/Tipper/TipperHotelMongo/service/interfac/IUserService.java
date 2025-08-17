package com.Tipper.TipperHotelMongo.service.interfac;

import com.Tipper.TipperHotelMongo.dto.LoginRequestDTO;
import com.Tipper.TipperHotelMongo.dto.ResponseDTO;
import com.Tipper.TipperHotelMongo.entity.User;
import org.apache.coyote.Response;

public interface IUserService {
    ResponseDTO register(User user);

    ResponseDTO Login(LoginRequestDTO loginRequestDTO);

    ResponseDTO getAllUsers();

    ResponseDTO deleteUser(String userId);

    ResponseDTO getUserById(String userId);

    ResponseDTO getMyInfo(String userId);

    ResponseDTO getUsersBookingsHistory(String userId);
}
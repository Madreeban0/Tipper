package com.Tipper.TipperHotelMongo.service.impl;


import com.Tipper.TipperHotelMongo.dto.LoginRequestDTO;
import com.Tipper.TipperHotelMongo.dto.ResponseDTO;
import com.Tipper.TipperHotelMongo.dto.UserDTO;
import com.Tipper.TipperHotelMongo.entity.User;
import com.Tipper.TipperHotelMongo.exception.OurException;
import com.Tipper.TipperHotelMongo.repo.UserRepository;
import com.Tipper.TipperHotelMongo.service.interfac.IUserService;
import com.Tipper.TipperHotelMongo.utils.JWTUtils;
import com.Tipper.TipperHotelMongo.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public ResponseDTO register(User user){

        ResponseDTO response = new ResponseDTO();

        try{
            if(user.getRole()==null || user.getRole().isBlank()){
                user.setRole("USER");
            }
            if(userRepository.existByEmail(user.getId())){
                throw  new Exception("Email Already exits");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            response.setStatusCode(200);
            response.setMessage("You have successfully registered your email");
            response.setUser(userDTO);
        } catch(OurException e){

            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch(Exception e){

            response.setStatusCode(500);
            response.setMessage("Error while regitering your email"+e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO Login(LoginRequestDTO loginRequestDTO){

      ResponseDTO response = new ResponseDTO();



       try{
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),loginRequestDTO.getPassword()));
           var user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new OurException("User not found"));
           var token = jwtUtils.generateToken(user);



           response.setStatusCode(200);
           response.setMessage("You have successfully logged in");
           response.setToken((String) token);
           response.setRole(user.getRole());
           response.setExpirationTime("7 days");
       } catch(OurException e){
           response.setStatusCode(404);
           response.setMessage(e.getMessage());

       } catch(Exception e){
           response.setStatusCode(500);
           response.setMessage("Error while logging in"+e.getMessage());
       }
        return response;
    }

    @Override
    public ResponseDTO getAllUsers(){
        ResponseDTO response = new ResponseDTO();

        try{
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
            response.setStatusCode(200);
            response.setMessage("Successfully fetched all users");
            response.setUserList(userDTOList);
        } catch(Exception e){
            response.setStatusCode(404);
            response.setMessage("Error while fetching all users"+e.getMessage());

        }
        return response;
    }

    @Override
    public ResponseDTO getUsersBookingsHistory(String userId){

        ResponseDTO response = new ResponseDTO();

        try{
            User user = userRepository.findById(userId).orElseThrow(()-> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
            response.setStatusCode(200);
            response.setMessage("Successfully fetched user bookings history");
            response.setUser(userDTO);
        } catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while fetching user bookings history"+e.getMessage());
        }
        return null;
    }

    @Override
    public ResponseDTO deleteUser(String userId){

        ResponseDTO response = new ResponseDTO();

        try{
            User user =userRepository.findById(userId).orElseThrow(()-> new OurException("User not found"));
            userRepository.deleteById(userId);
            response.setStatusCode(200);
            response.setMessage("User deleted successfully");
        }catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while deleting user"+e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO getUserById(String userId){

        ResponseDTO response = new ResponseDTO();

        try{
            User user = userRepository.findById(userId).orElseThrow(()-> new OurException("User not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("User found");
            response.setUser(userDTO);
        } catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while getting id"+e.getMessage());
        }
        return response;
    }

    @Override
    public ResponseDTO getMyInfo(String email){

        ResponseDTO response = new ResponseDTO();

        try{
            User user = userRepository.findByEmail(email).orElseThrow(()-> new OurException("User Info not found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successfully fetched user info");
            response.setUser(userDTO);
        } catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while fetching user info"+e.getMessage());
        }
        return response;
    }
}
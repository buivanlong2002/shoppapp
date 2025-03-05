package com.example.shopapp.service;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String loginUser(String phoneNumber , String password) throws Exception;
}

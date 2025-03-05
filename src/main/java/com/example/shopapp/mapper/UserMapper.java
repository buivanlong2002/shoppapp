package com.example.shopapp.mapper;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.model.Category;
import com.example.shopapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO userDTO);
    void updateUser(@MappingTarget User user, UserDTO userDTO);
}

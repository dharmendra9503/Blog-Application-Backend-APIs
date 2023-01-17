package com.example.blogapis.service;

import com.example.blogapis.payloads.UserDataTransfer;
import com.example.blogapis.payloads.UserResponse;

import java.util.List;

public interface UserService {

    UserDataTransfer createUser(UserDataTransfer userDTO);

    UserResponse getAllUsers(Integer pageNumber, Integer pageSize);

    UserDataTransfer getUser(Integer userId);

    UserDataTransfer updateUser(UserDataTransfer user, Integer userId);

    void deleteUser(Integer userId);
}

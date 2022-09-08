package com.example.blogapis.service;

import com.example.blogapis.payloads.UserDataTransfer;

import java.util.List;

public interface UserService {

    UserDataTransfer createUser(UserDataTransfer userDTO);

    List<UserDataTransfer> getAllUsers();

    UserDataTransfer getUser(Integer userId);

    UserDataTransfer updateUser(UserDataTransfer user, Integer userId);

    void deleteUser(Integer userId);
}

package com.example.blogapis.controller;

import com.example.blogapis.config.AppConstants;
import com.example.blogapis.payloads.UserDataTransfer;
import com.example.blogapis.payloads.UserResponse;
import com.example.blogapis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String welcome(){
        return "Welcome to Blog Application APIs";
    }

    @PostMapping("/user")
    public UserDataTransfer createUser(@Valid @RequestBody UserDataTransfer userDTO){
        return userService.createUser(userDTO);
    }

    @GetMapping("/users")
    public ResponseEntity<UserResponse> getAllUser(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
            Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
            Integer pageSize
    ){

        return new ResponseEntity<>(userService.getAllUsers(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public UserDataTransfer getAllUser(@PathVariable Integer userId){
        return userService.getUser(userId);
    }

    @PutMapping("/user/{userId}")
    public UserDataTransfer updateUser(@Valid @RequestBody UserDataTransfer userDTO, @PathVariable Integer userId){
        return userService.updateUser(userDTO, userId);
    }

    @DeleteMapping("user/{userId}")
    public String deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);

        return "User with id "+userId+" deleted successfully";
    }

}

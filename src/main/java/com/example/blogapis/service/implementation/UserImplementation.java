package com.example.blogapis.service.implementation;

import com.example.blogapis.exception.ResourceNotFoundException;
import com.example.blogapis.model.User;
import com.example.blogapis.payloads.UserDataTransfer;
import com.example.blogapis.payloads.UserResponse;
import com.example.blogapis.repository.UserRepository;
import com.example.blogapis.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    //UserDataTransfer and User both class have member variable and Getters and Setters
    //Here we can use User class but for security reason we have developed one another class named UserDataTransfer.
    //Now we will use User class for Database and not expose this User class. we will do all work of API management using UserDataTransfer class.

    public UserDataTransfer createUser(UserDataTransfer userData) {
        User user = userDataTransfer_to_user(userData);

        //What is the need to convert UserdataTransfer object to User object?
        //Here we manage database using user class, and that's why all the method of JpaRepository will take User class object as argument. so, we need to convert UserDataTransfer object to User object.

        User saved =  userRepository.save(user);

        return user_to_userDataTransfer(saved);     //Here our return type is userDataTransfer. so, we need to convert User object to UserDataTransfer object.
    }

    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> userPage = userRepository.findAll(pageable);
        List<User> users = userPage.getContent();

//        List<UserDataTransfer> userDTO = users.stream().map(user -> user_to_userDataTransfer(user)).toList();
        List<UserDataTransfer> userDTO = users.stream().map(user -> modelMapper.map(user, UserDataTransfer.class)).toList();
//        users.stream().map(this::user_to_userDataTransfer).toList();       //We can also use this

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(userDTO);
        userResponse.setPageNumber(userPage.getNumber());
        userResponse.setPageSize(userPage.getSize());
        userResponse.setTotalPages(userPage.getTotalPages());
        userResponse.setTotalElements(userPage.getTotalElements());
        userResponse.setLastPage(userPage.isLast());

        return userResponse;
    }

    @Override
    public UserDataTransfer getUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        return user_to_userDataTransfer(user);
    }

    @Override
    public UserDataTransfer updateUser(UserDataTransfer userData, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        user.setName(userData.getName());
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());
        user.setAbout(userData.getAbout());
        
        User updated = userRepository.save(user);
        
        return user_to_userDataTransfer(updated);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
    }



    private User userDataTransfer_to_user(UserDataTransfer userDTO){     //This is conversion method which will take UserDataTransfer object as argument and return User object.
        //Here we have use ModelMapper to convert one model to another model.
        User user = this.modelMapper.map(userDTO, User.class);

        //Here we are manually convert one model to another model.
//        User user = new User();
//        user.setId(userDTO.getId());
//        user.setName(userDTO.getName());
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());
//        user.setAbout(userDTO.getAbout());

        return user;
    }

    private UserDataTransfer user_to_userDataTransfer(User user){      //This is conversion method which will take User object as argument and return UserDataTransfer object.
        //Here we have use ModelMapper to convert one model to another model.
        UserDataTransfer userDTO = this.modelMapper.map(user, UserDataTransfer.class);

        //Here we are manually convert one model to another model.
//        UserDataTransfer userDTO = new UserDataTransfer();
//        userDTO.setId(user.getId());
//        userDTO.setName(user.getName());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setPassword(user.getPassword());
//        userDTO.setAbout(user.getAbout());

        return userDTO;
    }
}

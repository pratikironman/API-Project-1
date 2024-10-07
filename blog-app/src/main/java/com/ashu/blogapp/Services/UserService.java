package com.ashu.blogapp.Services;

import com.ashu.blogapp.Payloads.UserDto;

import java.util.List;

public interface UserService {

    // in interface any method created is by default "public" method
    // we use interfaces for "loose coupling" & unit testing purpose so that whenever we need, we could make changed in any of method we want



    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);


    //  for registering users along with specific role nd encrypted password
    UserDto registerNewUser(UserDto userDto);

}

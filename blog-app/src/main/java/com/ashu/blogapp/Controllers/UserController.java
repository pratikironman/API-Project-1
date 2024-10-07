package com.ashu.blogapp.Controllers;

import com.ashu.blogapp.Payloads.APIResponse;
import com.ashu.blogapp.Payloads.UserDto;
import com.ashu.blogapp.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")

public class UserController {

    @Autowired
    private UserService userService;

    //POST - create User
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto = this.userService.createUser(userDto);

        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //PUT - update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
        UserDto updatedUserDto1 = this.userService.updateUser(userDto, uid);

        return ResponseEntity.ok(updatedUserDto1);
    }

    // this can be only done by only "ADMIN"
    //DELETE - delete User
    @PreAuthorize("hasRole('ADMIN')")       // THIS API IS ONLY AVAILABLE TO ADMIN

    @DeleteMapping("/{userId}")

    //We could also use "public void" if we don't want to return anything from the method, but we are returning here something, so we used "public ResponseEntity nd using APIResponse class we created for various Responses we get"

    public ResponseEntity<APIResponse> deleteUser(@PathVariable("userId") Integer uid){
        this.userService.deleteUser(uid);

        return new ResponseEntity<APIResponse>(new APIResponse("User Deleted Successfully !!!", true), HttpStatus.OK);
    }

    //GET - get User
    // getting all Users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){

        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    //getting particular user based on Id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUsers(@PathVariable Integer userId){

        return ResponseEntity.ok(this.userService.getUserById(userId));
    }



}

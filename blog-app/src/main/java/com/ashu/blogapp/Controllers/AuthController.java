package com.ashu.blogapp.Controllers;

import com.ashu.blogapp.Exceptions.ApiLoginException;
import com.ashu.blogapp.Payloads.JWTAuthRequest;
import com.ashu.blogapp.Payloads.JWTAuthResponse;
import com.ashu.blogapp.Payloads.UserDto;
import com.ashu.blogapp.Security.JWTTokenHelper;
import com.ashu.blogapp.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody JWTAuthRequest request) {

        this.doAuthenticate(request.getUserName(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        String token = this.jwtTokenHelper.generateToken(userDetails);

        JWTAuthResponse response = new JWTAuthResponse();
        response.setToken(token);
        response.setUserName(userDetails.getUsername());


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //---

    private void doAuthenticate(String userName, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName, password);
        try {
            authenticationManager.authenticate(authentication);

        } catch (BadCredentialsException e) {
//            throw new BadCredentialsException(" Invalid Username or Password  !!");
            throw new ApiLoginException("Invalid Username or Password  !!");

        }

    }

//     We are creating custom exception(ApiLoginException) for this stage

//    @ExceptionHandler(BadCredentialsException.class)
//    public String exceptionHandler() {
//        return "Credentials Invalid !!";
//    }



    //  for registering users along with specific role nd encrypted password
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registeringNewUser(@RequestBody UserDto userDto){

        UserDto registerNewUser = this.userService.registerNewUser(userDto);

        return new ResponseEntity<UserDto>(registerNewUser, HttpStatus.CREATED);
    }



}



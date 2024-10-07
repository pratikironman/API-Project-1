package com.ashu.blogapp.Exceptions;


import com.ashu.blogapp.Payloads.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();

        APIResponse apiResponse = new APIResponse(message, false);

        return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    // Handling exception while validating User
    // "MethodArgumentNotValidException" is exception that was occurring so using it for handling exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){

        Map<String, String> response = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();

            response.put(fieldName, message);
        });


        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ApiLoginException.class)
    public ResponseEntity<APIResponse> handleApiLoginException(ApiLoginException ex){
        String message = ex.getMessage();

        APIResponse apiResponse = new APIResponse(message, true);

        return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }




}

package com.ashu.blogapp.Exceptions;

public class ApiLoginException extends RuntimeException{

    // String parameter constrctor
    public ApiLoginException(String message) {
        super(message);


    }

    // default constructor
    public ApiLoginException() {
    }
}

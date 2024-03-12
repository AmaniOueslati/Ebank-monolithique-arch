package com.example.eBANK_backend.exception;

// extends RuntimeException si elle est une exception non surveillé
//extends Exception si elle est exception surveillé
public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(String message ){
        super(message);
    }
}

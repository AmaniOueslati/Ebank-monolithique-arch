package com.example.eBANK_backend.exception;

public class BankAccountNotFoundException extends Exception{
    public BankAccountNotFoundException(String message){
        super(message);
    }
}

package com.example.dreamShops.exceptions;

import org.aspectj.bridge.IMessage;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message){
        super(message);
    }
}

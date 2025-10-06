package com.example.dreamShops.exceptions;

import com.example.dreamShops.repository.CategoryRepository;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String message){
        super(message);
    }
}

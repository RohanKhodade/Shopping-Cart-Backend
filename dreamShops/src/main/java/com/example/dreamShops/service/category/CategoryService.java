package com.example.dreamShops.service.category;

import com.example.dreamShops.models.Category;

public interface CategoryService {
    Category getCategoryByName(String name);
    boolean addCategory(String name);
}

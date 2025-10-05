package com.example.dreamShops.service.category;

import com.example.dreamShops.models.Category;
import com.example.dreamShops.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Override
    public Category getCategoryByName(String name){
        return categoryRepository.findByName(name).orElse(null);
    }
    @Override
    public boolean addCategory(String name){
        Category category=getCategoryByName(name);
        if (category!=null){
            return false;
        }
        Category newCategory=new Category(name);
        categoryRepository.save(newCategory);
        return true;
    }

}
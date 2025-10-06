package com.example.dreamShops.service.category;

import com.example.dreamShops.exceptions.AlreadyExistsException;
import com.example.dreamShops.exceptions.CategoryNotFoundException;
import com.example.dreamShops.models.Category;
import com.example.dreamShops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                ()-> new CategoryNotFoundException("Category not found!")
        );
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())){
            throw new AlreadyExistsException("category Already Exists!");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category , Long id) {
        return Optional.ofNullable(categoryRepository.findByName(category.getName()))
                .map(existingCategory->{
                    existingCategory.setName(category.getName());
                    return categoryRepository.save(existingCategory);
                }).orElseThrow(()-> new CategoryNotFoundException("Category not found !"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(category->categoryRepository.delete(category),
                        ()->{
                    throw new CategoryNotFoundException("Category not found !");
                });
    }
}
package com.example.dreamShops.repository;

import com.example.dreamShops.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findProductsByBrand(String brand);
    List<Product> findProductsByCategoryName(String categoryName);
    List<Product> findProductsByBrandAndCategoryName(String brand, String categoryName);
    List<Product> findProductsByName(String name);
    List<Product> findProductsByBrandAndName(String brand,String name);
    Long countProductsByBrandAndName(String brand, String name);
}

package com.example.dreamShops.service.product;

import com.example.dreamShops.models.Product;
import com.example.dreamShops.requests.AddProductRequest;
import com.example.dreamShops.requests.UpdateProductRequest;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product getProductByID(Long id);
    Product addProduct(AddProductRequest request);
    Product deleteProduct(Long productId);
    Product updateProduct(UpdateProductRequest request,Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryName(String categoryName);
    List<Product> getProductsByBrandAndCategory(String brand, String category);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String name, String brand);
}
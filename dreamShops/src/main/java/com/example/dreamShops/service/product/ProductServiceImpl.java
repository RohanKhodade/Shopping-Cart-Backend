package com.example.dreamShops.service.product;

import com.example.dreamShops.exceptions.ProductNotFoundException;
import com.example.dreamShops.models.Category;
import com.example.dreamShops.models.Product;
import com.example.dreamShops.repository.ProductRepository;
import com.example.dreamShops.requests.AddProductRequest;
import com.example.dreamShops.service.category.CategoryServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryServiceImpl categoryServiceImpl;
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryServiceImpl categoryServiceImpl){
        this.productRepository=productRepository;
        this.categoryServiceImpl=categoryServiceImpl;
    }
    @Override
    public Product getProductByID(Long id) {
        return productRepository.findById(id).orElseThrow(
                ()-> new ProductNotFoundException("product with id " +id +" not found ")
        );
    }

    @Override
    public Void addProduct(AddProductRequest request) {
        // if category not in category then current is new category add in category
        // else create product and add
        Category category=request.getCategory();
        Category isCategoryPresent=categoryServiceImpl.getCategoryByName(category.getName());
        if (isCategoryPresent==null){
            // add the category;
            categoryServiceImpl.addCategory(category.getName());
        }
        Product product=createProduct(request,categoryServiceImpl.getCategoryByName(category.getName()));
        productRepository.save(product);
        return null;
    }
    public Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public boolean deleteProduct(Long productId) {
        Product product=productRepository.findById(productId).orElse(null);
        if (product!=null){
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findProductsByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findProductsByCategoryName(categoryName);
    }

    @Override
    public List<Product> getProductsByBrandAndCategory(String brand, String category) {
        return productRepository.findProductsByBrandAndCategoryName(brand,category);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findProductsByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findProductsByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countProductsByBrandAndName(brand, name);
    }
}
package com.example.dreamShops.service.product;

import com.example.dreamShops.exceptions.ProductNotFoundException;
import com.example.dreamShops.models.Category;
import com.example.dreamShops.models.Product;
import com.example.dreamShops.repository.CategoryRepository;
import com.example.dreamShops.repository.ProductRepository;
import com.example.dreamShops.requests.AddProductRequest;
import com.example.dreamShops.requests.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public Product getProductByID(Long id) {
        return productRepository.findById(id).orElseThrow(
                ()-> new ProductNotFoundException("product with id " +id +" not found ")
        );
    }

    @Override
    public Product addProduct(AddProductRequest request) {
        // if category not in category then current is new category add in category
        // else create product and add
        Category category=Optional.ofNullable(categoryRepository
                .findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory=new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        Product product=createProduct(request,category);
        return productRepository.save(product);
    }
    private Product createProduct(AddProductRequest request, Category category){
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
    public Product updateProduct(UpdateProductRequest request,Long id){
        // find if product is present in db
        return productRepository.findById(id)
                .map(existingProduct->createProductReq(existingProduct,request))
                .map(existingProduct->productRepository.save(existingProduct))
                .orElseThrow(()->new ProductNotFoundException("Product not found exception"));
    }
    private Product createProductReq(Product existingProduct,UpdateProductRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category= Optional.ofNullable(
                categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory=new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        existingProduct.setCategory(category);
        return existingProduct;
    }
    @Override
    public Product deleteProduct(Long productId) {
        Product product=productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("product not found to delete"));
        productRepository.delete(product);
        return product;
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
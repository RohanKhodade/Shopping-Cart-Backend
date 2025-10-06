package com.example.dreamShops.requests;

import com.example.dreamShops.models.Category;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@Getter
public class UpdateProductRequest {
    private String name;
    private BigDecimal price;
    private String brand;
    private int inventory;
    private String description;
    private Category category;
}

package com.example.spring_data.service;

import com.example.spring_data.model.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductList();

    Product getProductById(Long id);

    void deleteById(Long id);
}

package com.example.spring_data.service;

import com.example.spring_data.model.entity.Product;
import com.example.spring_data.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository pr;

    @Autowired
    public ProductServiceImpl(ProductRepository pr) {
        this.pr = pr;
    }

    @Override
    public List<Product> getProductList() {
        return pr.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return pr.findById(id).orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        pr.deleteById(id);
    }
}

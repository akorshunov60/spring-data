package com.example.spring_data.model.repository;

import com.example.spring_data.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findProductByPriceLessThan(BigDecimal maxPrice);

    List<Product> findProductByPriceGreaterThan(BigDecimal minPrice);

    List<Product> findProductByName(String prodName);
}

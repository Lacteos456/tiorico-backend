package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.models.Product;

import java.util.List;

public interface ProductService
{
    List<Product> findAll();
    Product findById(Integer id);
    Product save(Product product);
    public Product getProductById(Integer productId);
}

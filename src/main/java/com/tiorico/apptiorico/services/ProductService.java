package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.User;

import java.util.List;

public interface ProductService
{
    List<Product> findAll();
    Product save(Product product);
    public Product getProductById(Integer productId);
}

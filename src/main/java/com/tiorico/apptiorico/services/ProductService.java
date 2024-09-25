package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.models.Product;

import java.util.List;

public interface ProductService
{
    List<Product> findAll();
    Product save(Product product);
}

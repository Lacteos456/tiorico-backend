package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.services.ProductService;
import com.tiorico.apptiorico.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
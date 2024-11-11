package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.exceptions.UserException;
import com.tiorico.apptiorico.models.Category;
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
    public List<Product> findActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }

    @Override
    public Product findById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Integer productId) throws UserException.UserNotFoundException  {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
}
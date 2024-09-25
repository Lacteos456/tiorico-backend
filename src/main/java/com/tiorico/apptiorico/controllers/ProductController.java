package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.dtos.ProductDTO;
import com.tiorico.apptiorico.mappers.ProductMapper;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@CrossOrigin("*")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO)
                .toList();
        return ResponseEntity.ok(productDTOs);
    }

    @PostMapping("/")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productService.save(product);
        return ResponseEntity.ok(productMapper.toDTO(savedProduct));
    }
}
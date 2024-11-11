package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.dtos.ProductDTO;
import com.tiorico.apptiorico.dtos.ProviderDTO;
import com.tiorico.apptiorico.mappers.ProductMapper;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.Provider;
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

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.findActiveProducts();
        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO)
                .toList();
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProviderById(@PathVariable Integer id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDTO(product));
    }

    @PostMapping("/")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productService.save(product);
        return ResponseEntity.ok(productMapper.toDTO(savedProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductDTO productDTO) {
        Product productToUpdate = productMapper.toEntity(productDTO);
        productToUpdate.setId(id);
        Product updatedProduct = productService.updateProduct(productToUpdate);
        return ResponseEntity.ok(productMapper.toDTO(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        Product product = productService.findById(id);
        if (product != null) {
            product.setIsActive(false); // Marcado como inactivo
            productService.updateProduct(product);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
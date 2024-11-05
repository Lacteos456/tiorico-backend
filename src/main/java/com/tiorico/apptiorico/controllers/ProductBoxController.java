package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.dtos.ProductBoxDTO;
import com.tiorico.apptiorico.services.ProductBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-boxes")
@CrossOrigin("*")
public class ProductBoxController
{
    @Autowired
    private ProductBoxService productBoxService;

    @PostMapping("/")
    public ResponseEntity<ProductBoxDTO> createProductBox(@RequestBody ProductBoxDTO productBoxDTO) {
        ProductBoxDTO createdBox = productBoxService.createProductBox(productBoxDTO);
        return ResponseEntity.ok(createdBox);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductBoxDTO> getProductBoxById(@PathVariable Integer id) {
        ProductBoxDTO productBox = productBoxService.getProductBoxById(id);
        return ResponseEntity.ok(productBox);
    }

    @GetMapping
    public ResponseEntity<List<ProductBoxDTO>> getAllProductBoxes() {
        List<ProductBoxDTO> productBoxes = productBoxService.getAllProductBoxes();
        return ResponseEntity.ok(productBoxes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductBoxDTO> updateProductBox(@PathVariable Integer id, @RequestBody ProductBoxDTO productBoxDTO) {
        ProductBoxDTO updatedBox = productBoxService.updateProductBox(id, productBoxDTO);
        return ResponseEntity.ok(updatedBox);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductBox(@PathVariable Integer id) {
        productBoxService.deleteProductBox(id);
        return ResponseEntity.noContent().build();
    }
}
package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.dtos.ProductBoxDTO;
import com.tiorico.apptiorico.services.ProductBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/{productId}/boxes")
@CrossOrigin("*")
public class ProductBoxController
{
    @Autowired
    private ProductBoxService productBoxService;

    @PostMapping
    public ResponseEntity<ProductBoxDTO> createProductBox(@PathVariable Integer productId, @RequestBody ProductBoxDTO productBoxDTO) {
        productBoxDTO.setProductId(productId);  // Asociar el box al producto
        ProductBoxDTO createdBox = productBoxService.createProductBox(productBoxDTO);
        return ResponseEntity.ok(createdBox);
    }

    @GetMapping("/{boxId}")
    public ResponseEntity<ProductBoxDTO> getProductBoxById(@PathVariable Integer productId, @PathVariable Integer boxId) {
        ProductBoxDTO productBox = productBoxService.getProductBoxById(boxId);
        return ResponseEntity.ok(productBox);
    }

    @GetMapping
    public ResponseEntity<List<ProductBoxDTO>> getAllProductBoxes(@PathVariable Integer productId) {
        List<ProductBoxDTO> productBoxes = productBoxService.getAllProductBoxesByProductId(productId);
        return ResponseEntity.ok(productBoxes);
    }

    @PutMapping("/{boxId}")
    public ResponseEntity<ProductBoxDTO> updateProductBox(@PathVariable Integer productId, @PathVariable Integer boxId, @RequestBody ProductBoxDTO productBoxDTO) {
        productBoxDTO.setProductId(productId);  // Asegurar que el box está asociado al producto correcto
        ProductBoxDTO updatedBox = productBoxService.updateProductBox(boxId, productBoxDTO);
        return ResponseEntity.ok(updatedBox);
    }

    @DeleteMapping("/{boxId}")
    public ResponseEntity<Void> deleteProductBox(@PathVariable Integer productId, @PathVariable Integer boxId) {
        productBoxService.deleteProductBox(boxId);
        return ResponseEntity.noContent().build();
    }
}
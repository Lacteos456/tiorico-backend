package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.dtos.ProductSupplyDTO;
import com.tiorico.apptiorico.mappers.ProductSupplyMapper;
import com.tiorico.apptiorico.models.ProductSupply;
import com.tiorico.apptiorico.services.ProductSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-supplies")
@CrossOrigin("*")
public class ProductSupplyController
{
    @Autowired
    private ProductSupplyService productSupplyService;

    @Autowired
    private ProductSupplyMapper productSupplyMapper;

    @GetMapping("/product/{productId}")
    public List<ProductSupplyDTO> getSuppliesByProductId(@PathVariable Integer productId) {
        return productSupplyService.findByProductId(productId)
                .stream()
                .map(productSupplyMapper::toDTO)
                .toList();
    }

    @GetMapping("/")
    public List<ProductSupplyDTO> getAllProductSupplies() {
        return productSupplyService.findAll()
                .stream()
                .map(productSupplyMapper::toDTO)
                .toList();
    }

    @PostMapping("/")
    public ResponseEntity<ProductSupplyDTO> createProductSupply(@RequestBody ProductSupplyDTO productSupplyDTO) {
        ProductSupply supply = productSupplyMapper.toEntity(productSupplyDTO);
        ProductSupply savedSupply = productSupplyService.saveSupply(supply);
        return ResponseEntity.ok(productSupplyMapper.toDTO(savedSupply));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductSupply(@PathVariable Long id) {
        productSupplyService.saveSupply(null);
        return ResponseEntity.noContent().build();
    }
}
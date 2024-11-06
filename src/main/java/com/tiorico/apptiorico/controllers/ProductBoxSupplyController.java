package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.dtos.ProductBoxSupplyDTO;
import com.tiorico.apptiorico.mappers.ProductBoxSupplyMapper;
import com.tiorico.apptiorico.models.ProductBoxSupply;
import com.tiorico.apptiorico.services.ProductBoxSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-box-supplies")
@CrossOrigin("*")
public class ProductBoxSupplyController
{
    @Autowired
    private ProductBoxSupplyService productBoxSupplyService;

    @Autowired
    private ProductBoxSupplyMapper productBoxSupplyMapper;

    @GetMapping("/box/{productBoxId}")
    public List<ProductBoxSupplyDTO> getBoxSuppliesByBoxId(@PathVariable Integer productBoxId) {
        return productBoxSupplyService.findByProductBoxId(productBoxId)
                .stream()
                .map(productBoxSupplyMapper::toDTO)
                .toList();
    }

    @PostMapping("/")
    public ResponseEntity<ProductBoxSupplyDTO> createProductBoxSupply(@RequestBody ProductBoxSupplyDTO productBoxSupplyDTO) {
        ProductBoxSupply savedSupply = productBoxSupplyService.saveSupply(productBoxSupplyDTO);
        return ResponseEntity.ok(productBoxSupplyMapper.toDTO(savedSupply));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductBoxSupply(@PathVariable Integer id) {
        productBoxSupplyService.deleteSupply(id);
        return ResponseEntity.noContent().build();
    }
}
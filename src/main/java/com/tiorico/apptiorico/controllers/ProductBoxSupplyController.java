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

    @PostMapping
    public ResponseEntity<ProductBoxSupplyDTO> addProductBoxSupply(@RequestBody ProductBoxSupplyDTO dto) {
        ProductBoxSupplyDTO createdSupply = productBoxSupplyService.addProductBoxSupply(dto);
        return ResponseEntity.ok(createdSupply);
    }
}
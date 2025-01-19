package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.dtos.SaleDetailsDTO;
import com.tiorico.apptiorico.services.SaleDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sale-details")
@CrossOrigin("*")
public class SaleDetailController {

    private final SaleDetailsService saleDetailsService;

    public SaleDetailController(SaleDetailsService saleDetailsService) {
        this.saleDetailsService = saleDetailsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDetailsDTO> getSaleDetailsById(@PathVariable Integer id) {
        return ResponseEntity.ok(saleDetailsService.getSaleDetailsById(id));
    }

    @GetMapping
    public ResponseEntity<List<SaleDetailsDTO>> getAllSaleDetails() {
        return ResponseEntity.ok(saleDetailsService.getAllSaleDetails());
    }
}
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

    @PostMapping
    public ResponseEntity<ProductBoxSupplyDTO> addProductBoxSupply(@RequestBody ProductBoxSupplyDTO dto) {
        ProductBoxSupplyDTO createdSupply = productBoxSupplyService.addProductBoxSupply(dto);
        return ResponseEntity.ok(createdSupply);
    }

    @GetMapping
    public ResponseEntity<List<ProductBoxSupplyDTO>> getAllProducts() {
        List<ProductBoxSupply> productBoxSupplies = productBoxSupplyService.findAll();
        List<ProductBoxSupplyDTO> productBoxSupplyDTOs = productBoxSupplies.stream()
                .map(productBoxSupplyMapper::toDTO)
                .toList();
        return ResponseEntity.ok(productBoxSupplyDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductBoxSupplyDTO> getProductBoxSupplyById(@PathVariable Integer id) {
        ProductBoxSupply productBoxSupply = productBoxSupplyService.findById(id);
        if (productBoxSupply != null) {
            ProductBoxSupplyDTO productBoxSupplyDTO = productBoxSupplyMapper.toDTO(productBoxSupply);
            return ResponseEntity.ok(productBoxSupplyDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductBoxSupply(@PathVariable Integer id) {
        // Buscar el suministro por ID
        ProductBoxSupply productBoxSupply = productBoxSupplyService.findById(id);

        if (productBoxSupply != null) {
            // Llamar al servicio para eliminar el suministro y restaurar el stock
            productBoxSupplyService.deleteProductBoxSupply(productBoxSupply);
            return ResponseEntity.noContent().build(); // Responder con éxito (204)
        } else {
            return ResponseEntity.notFound().build(); // Responder con 404 si no se encuentra el suministro
        }
    }
}
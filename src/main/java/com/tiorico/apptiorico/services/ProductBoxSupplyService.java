package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.dtos.ProductBoxSupplyDTO;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.ProductBoxSupply;

import java.util.List;

public interface ProductBoxSupplyService
{
    List<ProductBoxSupply> findAll();
    ProductBoxSupplyDTO addProductBoxSupply(ProductBoxSupplyDTO dto);
    ProductBoxSupply findById(Integer id);
    void updateProductBoxSupply(ProductBoxSupply productBoxSupply);
    void deleteProductBoxSupply(ProductBoxSupply productBoxSupply);
}
package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.dtos.ProductBoxDTO;

import java.util.List;

public interface ProductBoxService
{
    ProductBoxDTO createProductBox(ProductBoxDTO productBoxDTO);
    ProductBoxDTO getProductBoxById(Integer id);
    List<ProductBoxDTO> getAllProductBoxes();
    ProductBoxDTO updateProductBox(Integer id, ProductBoxDTO productBoxDTO);
    void deleteProductBox(Integer id);
}
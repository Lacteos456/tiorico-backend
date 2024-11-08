package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.models.ProductSupply;

import java.util.List;

public interface ProductSupplyService
{
    ProductSupply saveSupply(ProductSupply supply);
    ProductSupply updateSupply(Integer supplyId, ProductSupply updatedSupply);
    List<ProductSupply> findAll();
    void deleteSupply(Integer supplyId);
    List<ProductSupply> findByProductId(Integer productId);
}
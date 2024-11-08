package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.dtos.ProductBoxSupplyDTO;
import com.tiorico.apptiorico.models.ProductBoxSupply;

import java.util.List;

public interface ProductBoxSupplyService
{
    ProductBoxSupply saveSupply(ProductBoxSupplyDTO dto);
    ProductBoxSupply updateSupply(Integer supplyId, ProductBoxSupplyDTO updatedDto);
    List<ProductBoxSupply> findAll();
    void deleteSupply(Integer supplyId);
    List<ProductBoxSupply> findByProductBoxId(Integer productBoxId);
}
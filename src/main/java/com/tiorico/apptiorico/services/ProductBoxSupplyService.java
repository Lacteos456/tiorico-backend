package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.models.ProductBoxSupply;

import java.util.List;

public interface ProductBoxSupplyService
{
    ProductBoxSupply saveSupply(ProductBoxSupply supply);
    List<ProductBoxSupply> findByProductBoxId(Integer productBoxId);
}
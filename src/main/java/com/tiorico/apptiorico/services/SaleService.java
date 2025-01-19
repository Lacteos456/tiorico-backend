package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.dtos.SaleDTO;

import java.util.List;

public interface SaleService {
    SaleDTO createSale(SaleDTO saleDTO);
    SaleDTO getSaleById(Integer id);
    List<SaleDTO> getAllSales();
    List<SaleDTO> getSalesByUserId(Integer userId);
}
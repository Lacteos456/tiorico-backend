package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.dtos.SaleDetailsDTO;

import java.util.List;

public interface SaleDetailsService {
    SaleDetailsDTO getSaleDetailsById(Integer id);
    List<SaleDetailsDTO> getAllSaleDetails();
}
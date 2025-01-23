package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.dtos.SaleDetailsDTO;
import com.tiorico.apptiorico.mappers.SaleDetailsMapper;
import com.tiorico.apptiorico.repositories.SaleDetailRepository;
import com.tiorico.apptiorico.services.SaleDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleDetailsServiceImpl implements SaleDetailsService {

    private final SaleDetailRepository saleDetailsRepository;
    private final SaleDetailsMapper saleDetailsMapper;

    public SaleDetailsServiceImpl(SaleDetailRepository saleDetailsRepository, SaleDetailsMapper saleDetailsMapper) {
        this.saleDetailsRepository = saleDetailsRepository;
        this.saleDetailsMapper = saleDetailsMapper;
    }

    @Override
    public SaleDetailsDTO getSaleDetailsById(Integer id) {
        return saleDetailsMapper.toDTO(saleDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado")));
    }

    @Override
    public List<SaleDetailsDTO> getAllSaleDetails() {
        return saleDetailsRepository.findAll().stream()
                .map(saleDetailsMapper::toDTO)
                .collect(Collectors.toList());
    }
}
package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.SaleDTO;
import com.tiorico.apptiorico.models.DailyAssignment;
import com.tiorico.apptiorico.models.Sale;
import com.tiorico.apptiorico.models.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SaleMapper {

    private final SaleDetailsMapper saleDetailsMapper;

    public SaleMapper(SaleDetailsMapper saleDetailsMapper) {
        this.saleDetailsMapper = saleDetailsMapper;
    }

    public SaleDTO toDTO(Sale sale) {
        return SaleDTO.builder()
                .id(sale.getId())
                .userId(sale.getUser().getId())
                .dailyAssignmentId(sale.getDailyAssignment().getId())
                .saleDate(sale.getSaleDate())
                .totalPrice(sale.getTotalPrice())
                .price(sale.getPrice())
                .isActive(sale.getIsActive())
                .saleDetails(sale.getSaleDetails().stream()
                        .map(saleDetailsMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public Sale toEntity(SaleDTO dto, User user, DailyAssignment dailyAssignment) {
        Sale sale = new Sale();
        sale.setId(dto.getId());
        sale.setUser(user);
        sale.setDailyAssignment(dailyAssignment);
        sale.setSaleDate(dto.getSaleDate());
        sale.setTotalPrice(dto.getTotalPrice());
        sale.setPrice(dto.getPrice());
        sale.setIsActive(dto.getIsActive());
        return sale;
    }
}
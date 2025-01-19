package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.SaleDetailsDTO;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.Sale;
import com.tiorico.apptiorico.models.SaleDetails;
import org.springframework.stereotype.Component;

@Component
public class SaleDetailsMapper {

    public SaleDetailsDTO toDTO(SaleDetails saleDetails) {
        return SaleDetailsDTO.builder()
                .id(saleDetails.getId())
                .saleId(saleDetails.getSale().getId())
                .productId(saleDetails.getProduct().getId())
                .boxQuantitySold(saleDetails.getBoxQuantitySold())
                .unitQuantitySold(saleDetails.getUnitQuantitySold())
                .unitPrice(saleDetails.getUnitPrice())
                .isActive(saleDetails.getIsActive())
                .build();
    }

    public SaleDetails toEntity(SaleDetailsDTO dto, Sale sale, Product product) {
        SaleDetails saleDetails = new SaleDetails();
        saleDetails.setId(dto.getId());
        saleDetails.setSale(sale);
        saleDetails.setProduct(product);
        saleDetails.setBoxQuantitySold(dto.getBoxQuantitySold());
        saleDetails.setUnitQuantitySold(dto.getUnitQuantitySold());
        saleDetails.setUnitPrice(dto.getUnitPrice());
        saleDetails.setIsActive(dto.getIsActive());
        return saleDetails;
    }
}
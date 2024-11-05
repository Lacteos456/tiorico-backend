package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.ProductBoxDTO;
import com.tiorico.apptiorico.models.ProductBox;
import org.springframework.stereotype.Component;

@Component
public class ProductBoxMapper
{
    public ProductBoxDTO toDTO(ProductBox productBox) {
        if (productBox == null) {
            return null;
        }

        ProductBoxDTO dto = new ProductBoxDTO();
        dto.setId(productBox.getId());
        dto.setProductId(productBox.getProduct().getId());
        dto.setUnitsPerBox(productBox.getUnitsPerBox());
        dto.setBoxPrice(productBox.getBoxPrice());

        return dto;
    }

    public ProductBox toEntity(ProductBoxDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductBox productBox = new ProductBox();
        productBox.setId(dto.getId());
        productBox.setUnitsPerBox(dto.getUnitsPerBox());
        productBox.setBoxPrice(dto.getBoxPrice());

        return productBox;
    }
}
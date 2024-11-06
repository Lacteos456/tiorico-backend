package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.ProductBoxSupplyDTO;
import com.tiorico.apptiorico.models.ProductBox;
import com.tiorico.apptiorico.models.ProductBoxSupply;
import com.tiorico.apptiorico.models.Provider;
import org.springframework.stereotype.Component;

@Component
public class ProductBoxSupplyMapper
{
    public ProductBoxSupplyDTO toDTO(ProductBoxSupply supply) {
        return new ProductBoxSupplyDTO(
                supply.getId(),
                supply.getProvider().getId(),
                supply.getProductBox().getId(),
                supply.getBoxQuantity(),
                supply.getBoxPrice(),
                supply.getSupplyDate()
        );
    }

    public ProductBoxSupply toEntity(ProductBoxSupplyDTO dto, Provider provider, ProductBox productBox) {
        ProductBoxSupply supply = new ProductBoxSupply();
        supply.setId(dto.getId());
        supply.setProvider(provider);
        supply.setProductBox(productBox);
        supply.setBoxQuantity(dto.getBoxQuantity());
        supply.setBoxPrice(dto.getBoxPrice());
        supply.setSupplyDate(dto.getSupplyDate());
        return supply;
    }
}
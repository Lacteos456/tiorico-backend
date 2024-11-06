package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.ProductSupplyDTO;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.ProductSupply;
import com.tiorico.apptiorico.models.Provider;
import org.springframework.stereotype.Component;

@Component
public class ProductSupplyMapper
{
    public ProductSupplyDTO toDTO(ProductSupply supply) {
        return new ProductSupplyDTO(supply.getId(), supply.getProvider().getId(), supply.getProduct().getId(), supply.getSupplyQuantity(), supply.getSupplyPrice(), supply.getSupplyDate());
    }

    public ProductSupply toEntity(ProductSupplyDTO dto, Provider provider, Product product) {
        ProductSupply supply = new ProductSupply();
        supply.setId(dto.getId());
        supply.setProvider(provider);
        supply.setProduct(product);
        supply.setSupplyQuantity(dto.getSupplyQuantity());
        supply.setSupplyPrice(dto.getSupplyPrice());
        supply.setSupplyDate(dto.getSupplyDate());
        return supply;
    }
}
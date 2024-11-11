package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.ProductBoxSupplyDTO;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.ProductBoxSupply;
import com.tiorico.apptiorico.models.Provider;
import com.tiorico.apptiorico.repositories.ProductRepository;
import com.tiorico.apptiorico.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductBoxSupplyMapper
{
    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductBoxSupplyDTO toDTO(ProductBoxSupply supply) {
        ProductBoxSupplyDTO dto = new ProductBoxSupplyDTO();
        dto.setId(supply.getId());
        dto.setProviderId(supply.getProvider().getId());
        dto.setProductId(supply.getProduct().getId());
        dto.setBoxQuantity(supply.getBoxQuantity());
        dto.setUnitsPerBox(supply.getUnitsPerBox());
        dto.setIsActive(supply.getIsActive());
        dto.setBoxPrice(supply.getBoxPrice());
        dto.setSupplyDate(supply.getSupplyDate());
        return dto;
    }

    public ProductBoxSupply toEntity(ProductBoxSupplyDTO dto) {
        ProductBoxSupply supply = new ProductBoxSupply();
        supply.setBoxQuantity(dto.getBoxQuantity());
        supply.setUnitsPerBox(dto.getUnitsPerBox());
        supply.setBoxPrice(dto.getBoxPrice());

        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        supply.setProvider(provider);

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        supply.setProduct(product);

        supply.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        return supply;
    }
}
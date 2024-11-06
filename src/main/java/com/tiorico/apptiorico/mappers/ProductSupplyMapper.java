package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.ProductSupplyDTO;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.ProductSupply;
import com.tiorico.apptiorico.models.Provider;
import com.tiorico.apptiorico.repositories.ProductRepository;
import com.tiorico.apptiorico.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductSupplyMapper
{
    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductSupplyDTO toDTO(ProductSupply supply) {
        return new ProductSupplyDTO(supply.getId(), supply.getProvider().getId(), supply.getProduct().getId(), supply.getSupplyQuantity(), supply.getSupplyPrice(), supply.getSupplyDate());
    }

    public ProductSupply toEntity(ProductSupplyDTO dto) {
        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

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
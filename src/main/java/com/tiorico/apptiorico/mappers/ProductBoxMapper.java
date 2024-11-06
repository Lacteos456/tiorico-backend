package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.ProductBoxDTO;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.ProductBox;
import org.springframework.stereotype.Component;
import com.tiorico.apptiorico.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ProductBoxMapper
{
    @Autowired
    private ProductRepository productRepository;

    // Convierte un ProductBox a ProductBoxDTO
    public ProductBoxDTO toDTO(ProductBox productBox) {
        if (productBox == null) {
            return null;
        }

        ProductBoxDTO dto = new ProductBoxDTO();
        dto.setId(productBox.getId());
        dto.setProductId(productBox.getProduct().getId()); // Asigna el ID de Product
        dto.setBoxPrice(productBox.getBoxPrice());
        dto.setUnitsPerBox(productBox.getUnitsPerBox() != null ? productBox.getUnitsPerBox() : 0);

        return dto;
    }

    // Convierte un ProductBoxDTO a ProductBox
    public ProductBox toEntity(ProductBoxDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductBox productBox = new ProductBox();
        productBox.setId(dto.getId());
        productBox.setBoxPrice(dto.getBoxPrice());
        productBox.setUnitsPerBox(dto.getUnitsPerBox() != null ? dto.getUnitsPerBox() : 0);

        // Asignar Product si productId no es null
        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            productBox.setProduct(product); // Asigna el Product obtenido
        }

        return productBox;
    }
}
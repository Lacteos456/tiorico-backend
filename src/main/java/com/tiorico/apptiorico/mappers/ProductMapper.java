package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.ProductDTO;
import com.tiorico.apptiorico.models.Category;
import com.tiorico.apptiorico.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper
{
    // Convierte un Product a ProductDTO
    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setImage(product.getImage());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setIsActive(product.getIsActive());
        dto.setCustomFields(product.getCustomFields());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);

        return dto;
    }

    // Convierte un ProductDTO a Product
    public Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImage(dto.getImage());
        product.setCustomFields(dto.getCustomFields());

        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(dto.getCategoryId());
            product.setCategory(category);
        }

        // Establecer isActive a true por defecto
        product.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        return product;
    }
}
package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.CategoryDTO;
import com.tiorico.apptiorico.models.Category;
import com.tiorico.apptiorico.models.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper
{
    private final ProductMapper productMapper;

    public CategoryMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    // Convierte un Category a CategoryDTO
    public CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        dto.setIsActive(category.getIsActive());
        dto.setCustomFields(category.getCustomFields());

        // Mapea la lista de productos a una lista de productIds
        List<Integer> productIds = category.getProducts() != null
                ? category.getProducts().stream().map(Product::getId).collect(Collectors.toList())
                : null;
        dto.setProductIds(productIds);

        return dto;
    }

    // Convierte un CategoryDTO a Category
    public Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setIsActive(dto.getIsActive());
        category.setCustomFields(dto.getCustomFields());

        return category;
    }
}
package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.dtos.ProductBoxDTO;
import com.tiorico.apptiorico.mappers.ProductBoxMapper;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.ProductBox;
import com.tiorico.apptiorico.repositories.ProductBoxRepository;
import com.tiorico.apptiorico.repositories.ProductRepository;
import com.tiorico.apptiorico.services.ProductBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductBoxServiceImpl implements ProductBoxService
{

    @Autowired
    private ProductBoxRepository productBoxRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductBoxMapper productBoxMapper;

    @Override
    public ProductBoxDTO createProductBox(ProductBoxDTO productBoxDTO) {
        Product product = productRepository.findById(productBoxDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        ProductBox productBox = productBoxMapper.toEntity(productBoxDTO);
        productBox.setProduct(product);

        ProductBox savedProductBox = productBoxRepository.save(productBox);
        return productBoxMapper.toDTO(savedProductBox);
    }

    @Override
    public ProductBoxDTO getProductBoxById(Integer id) {
        ProductBox productBox = productBoxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ProductBox no encontrado"));
        return productBoxMapper.toDTO(productBox);
    }

    @Override
    public List<ProductBoxDTO> getAllProductBoxes() {
        return productBoxRepository.findAll().stream()
                .map(productBoxMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductBoxDTO> getAllProductBoxesByProductId(Integer productId) {
        List<ProductBox> productBoxes = productBoxRepository.findProductBoxByProduct_Id(productId);
        return productBoxes.stream()
                .map(productBoxMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductBoxDTO updateProductBox(Integer id, ProductBoxDTO productBoxDTO) {
        ProductBox productBox = productBoxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ProductBox no encontrado"));

        productBox.setBoxPrice(productBoxDTO.getBoxPrice());

        ProductBox updatedProductBox = productBoxRepository.save(productBox);
        return productBoxMapper.toDTO(updatedProductBox);
    }

    @Override
    public void deleteProductBox(Integer id) {
        productBoxRepository.deleteById(id);
    }
}
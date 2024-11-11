package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.dtos.ProductBoxSupplyDTO;
import com.tiorico.apptiorico.mappers.ProductBoxSupplyMapper;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.ProductBoxSupply;
import com.tiorico.apptiorico.repositories.ProductBoxSupplyRepository;
import com.tiorico.apptiorico.repositories.ProductRepository;
import com.tiorico.apptiorico.services.ProductBoxSupplyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductBoxSupplyServiceImpl implements ProductBoxSupplyService
{
    @Autowired
    private ProductBoxSupplyRepository productBoxSupplyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductBoxSupplyMapper productBoxSupplyMapper;

    @Override
    public List<ProductBoxSupply> findAll() {
        return productBoxSupplyRepository.findAll();
    }

    @Override
    @Transactional
    public ProductBoxSupplyDTO addProductBoxSupply(ProductBoxSupplyDTO dto) {
        ProductBoxSupply supply = productBoxSupplyMapper.toEntity(dto);
        productBoxSupplyRepository.save(supply);

        // Actualizar el stock del producto basado en el suministro de cajas
        Product product = supply.getProduct();
        int additionalUnits = supply.getBoxQuantity() * supply.getUnitsPerBox();
        product.setStock(product.getStock() + additionalUnits);

        productRepository.save(product);

        return productBoxSupplyMapper.toDTO(supply);
    }
}
package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.ProductSupply;
import com.tiorico.apptiorico.repositories.ProductSupplyRepository;
import com.tiorico.apptiorico.services.ProductService;
import com.tiorico.apptiorico.services.ProductSupplyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSupplyServiceImpl implements ProductSupplyService
{
    @Autowired
    private ProductSupplyRepository productSupplyRepository;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public ProductSupply saveSupply(ProductSupply supply) {
        Product product = supply.getProduct();
        product.setStock(product.getStock() + supply.getSupplyQuantity());
        productService.save(product);

        return productSupplyRepository.save(supply);
    }

    @Override
    @Transactional
    public ProductSupply updateSupply(Integer supplyId, ProductSupply updatedSupply) {
        ProductSupply existingSupply = productSupplyRepository.findById(supplyId).orElseThrow();
        Product product = existingSupply.getProduct();

        int stockAdjustment = updatedSupply.getSupplyQuantity() - existingSupply.getSupplyQuantity();
        product.setStock(product.getStock() + stockAdjustment);
        productService.save(product);

        updatedSupply.setId(supplyId);
        return productSupplyRepository.save(updatedSupply);
    }

    @Override
    @Transactional
    public void deleteSupply(Integer supplyId) {
        ProductSupply supply = productSupplyRepository.findById(supplyId).orElseThrow();
        Product product = supply.getProduct();

        product.setStock(product.getStock() - supply.getSupplyQuantity());
        productService.save(product);

        productSupplyRepository.deleteById(supplyId);
    }

    @Override
    public List<ProductSupply> findByProductId(Integer productId) {
        return productSupplyRepository.findByProductId(productId);
    }
}
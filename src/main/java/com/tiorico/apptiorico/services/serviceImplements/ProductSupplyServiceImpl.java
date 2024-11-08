package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.ProductSupply;
import com.tiorico.apptiorico.repositories.ProductRepository;
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
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public ProductSupply saveSupply(ProductSupply supply) {
        // Validar que el producto esté asignado en el suministro
        if (supply.getProduct() == null) {
            throw new RuntimeException("El producto asociado al suministro es nulo.");
        }

        // Verificar que el producto existe en la base de datos y asignarlo
        Product product = productRepository.findById(supply.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
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
    public List<ProductSupply> findAll() {
        return productSupplyRepository.findAll();
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
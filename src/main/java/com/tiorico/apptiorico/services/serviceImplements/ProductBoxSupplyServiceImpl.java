package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.models.ProductBoxSupply;
import com.tiorico.apptiorico.repositories.ProductBoxSupplyRepository;
import com.tiorico.apptiorico.services.ProductBoxSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductBoxSupplyServiceImpl implements ProductBoxSupplyService
{
    @Autowired
    private ProductBoxSupplyRepository productBoxSupplyRepository;

    @Override
    public ProductBoxSupply saveSupply(ProductBoxSupply supply) {
        return productBoxSupplyRepository.save(supply);
    }

    @Override
    public List<ProductBoxSupply> findByProductBoxId(Integer productBoxId) {
        return productBoxSupplyRepository.findByProductBoxId(productBoxId);
    }
}
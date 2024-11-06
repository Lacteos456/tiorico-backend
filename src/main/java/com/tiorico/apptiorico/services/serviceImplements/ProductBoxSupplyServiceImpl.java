package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.dtos.ProductBoxSupplyDTO;
import com.tiorico.apptiorico.mappers.ProductBoxSupplyMapper;
import com.tiorico.apptiorico.models.ProductBox;
import com.tiorico.apptiorico.models.ProductBoxSupply;
import com.tiorico.apptiorico.models.Provider;
import com.tiorico.apptiorico.repositories.ProductBoxRepository;
import com.tiorico.apptiorico.repositories.ProductBoxSupplyRepository;
import com.tiorico.apptiorico.repositories.ProviderRepository;
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
    private ProductBoxRepository productBoxRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProductBoxSupplyMapper productBoxSupplyMapper;

    @Override
    @Transactional
    public ProductBoxSupply saveSupply(ProductBoxSupplyDTO dto) {
        // Obtener Provider y ProductBox desde la base de datos
        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        ProductBox productBox = productBoxRepository.findById(dto.getProductBoxId())
                .orElseThrow(() -> new RuntimeException("ProductBox no encontrado"));

        // Convertir DTO a entidad
        ProductBoxSupply supply = productBoxSupplyMapper.toEntity(dto, provider, productBox);

        // Ajustar unidades del ProductBox
        productBox.setUnitsPerBox(productBox.getUnitsPerBox() + supply.getBoxQuantity());
        productBoxRepository.save(productBox);

        return productBoxSupplyRepository.save(supply);
    }

    @Override
    @Transactional
    public ProductBoxSupply updateSupply(Integer supplyId, ProductBoxSupplyDTO updatedDto) {
        ProductBoxSupply existingSupply = productBoxSupplyRepository.findById(supplyId)
                .orElseThrow(() -> new RuntimeException("Suministro no encontrado"));

        ProductBox productBox = existingSupply.getProductBox();
        if (productBox == null) {
            throw new RuntimeException("El ProductBox asociado al suministro es nulo.");
        }

        // Ajustar unidades basado en la diferencia de cantidades
        int quantityAdjustment = updatedDto.getBoxQuantity() - existingSupply.getBoxQuantity();
        productBox.setUnitsPerBox(productBox.getUnitsPerBox() + quantityAdjustment);
        productBoxRepository.save(productBox);

        // Actualizar los datos del suministro
        existingSupply.setBoxQuantity(updatedDto.getBoxQuantity());
        existingSupply.setBoxPrice(updatedDto.getBoxPrice());
        existingSupply.setSupplyDate(updatedDto.getSupplyDate());

        return productBoxSupplyRepository.save(existingSupply);
    }

    @Override
    @Transactional
    public void deleteSupply(Integer supplyId) {
        ProductBoxSupply supply = productBoxSupplyRepository.findById(supplyId)
                .orElseThrow(() -> new RuntimeException("Suministro no encontrado"));

        ProductBox productBox = supply.getProductBox();
        if (productBox == null) {
            throw new RuntimeException("El ProductBox asociado al suministro es nulo.");
        }

        // Reducir las unidades en ProductBox
        productBox.setUnitsPerBox(productBox.getUnitsPerBox() - supply.getBoxQuantity());
        productBoxRepository.save(productBox);

        productBoxSupplyRepository.deleteById(supplyId);
    }

    @Override
    public List<ProductBoxSupply> findByProductBoxId(Integer productBoxId) {
        return productBoxSupplyRepository.findByProductBoxId(productBoxId);
    }
}
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
        return productBoxSupplyRepository.findAllActive();
    }

    @Override
    @Transactional
    public ProductBoxSupplyDTO addProductBoxSupply(ProductBoxSupplyDTO dto) {
        ProductBoxSupply supply = productBoxSupplyMapper.toEntity(dto);

        // Buscar un suministro existente con el mismo proveedor, producto y unidades por caja
        ProductBoxSupply existingSupply = productBoxSupplyRepository.findByProviderAndProductAndUnitsPerBox(
                supply.getProvider().getId(),
                supply.getProduct().getId(),
                supply.getUnitsPerBox()
        );

        Product product = supply.getProduct();

        if (existingSupply != null) {
            // Sumar las cajas y actualizar el registro existente
            existingSupply.setBoxQuantity(existingSupply.getBoxQuantity() + supply.getBoxQuantity());
            productBoxSupplyRepository.save(existingSupply);

            // Actualizar el stock del producto
            int additionalUnits = supply.getBoxQuantity() * supply.getUnitsPerBox();
            product.setStock(product.getStock() + additionalUnits);
            productRepository.save(product);

            return productBoxSupplyMapper.toDTO(existingSupply);
        } else {
            // Crear un nuevo registro si no existe uno con las mismas unidades por caja
            productBoxSupplyRepository.save(supply);

            // Actualizar el stock del producto
            int additionalUnits = supply.getBoxQuantity() * supply.getUnitsPerBox();
            product.setStock(product.getStock() + additionalUnits);
            productRepository.save(product);

            return productBoxSupplyMapper.toDTO(supply);
        }
    }

    @Override
    public ProductBoxSupply findById(Integer id) {
        return productBoxSupplyRepository.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteProductBoxSupply(ProductBoxSupply productBoxSupply) {
        // Obtener el producto asociado al suministro
        Product product = productBoxSupply.getProduct();

        // Calcular las unidades que se deben restar del stock
        int unitsToRestore = productBoxSupply.getBoxQuantity() * productBoxSupply.getUnitsPerBox();

        // Restaurar el stock del producto
        product.setStock(product.getStock() - unitsToRestore);
        if (product.getStock() < 0) {
            product.setStock(0); // Evitar que el stock sea negativo
        }

        // Guardar el producto con el stock actualizado
        productRepository.save(product);

        // Eliminar el registro del suministro
        productBoxSupplyRepository.delete(productBoxSupply);
    }

    @Override
    public void updateProductBoxSupply(ProductBoxSupply productBoxSupply) {
        productBoxSupply.setIsActive(false);
        productBoxSupplyRepository.save(productBoxSupply);

        Product product = productBoxSupply.getProduct();
        int unitsToSubtract = productBoxSupply.getBoxQuantity() * productBoxSupply.getUnitsPerBox();
        product.setStock(Math.max(product.getStock() - unitsToSubtract, 0));

        productRepository.save(product);
    }
}
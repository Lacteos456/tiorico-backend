package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.dtos.DailyAssignmentDTO;
import com.tiorico.apptiorico.mappers.DailyAssignmentMapper;
import com.tiorico.apptiorico.models.DailyAssignment;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.ProductBoxSupply;
import com.tiorico.apptiorico.models.User;
import com.tiorico.apptiorico.repositories.DailyAssignmentRepository;
import com.tiorico.apptiorico.repositories.ProductBoxSupplyRepository;
import com.tiorico.apptiorico.repositories.ProductRepository;
import com.tiorico.apptiorico.repositories.UserRepository;
import com.tiorico.apptiorico.services.DailyAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyAssignmentServiceImpl implements DailyAssignmentService
{
    @Autowired
    private DailyAssignmentRepository dailyAssignmentRepository;

    @Autowired
    private ProductBoxSupplyRepository productBoxSupplyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DailyAssignmentMapper dailyAssignmentMapper;

    @Override
    public List<DailyAssignmentDTO> getAllAssignments() {
        return dailyAssignmentRepository.findAll().stream()
                .map(dailyAssignmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DailyAssignmentDTO createDailyAssignment(DailyAssignmentDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ProductBoxSupply productBoxSupply = productBoxSupplyRepository.findById(dto.getProductBoxSupplyId())
                .orElseThrow(() -> new RuntimeException("Suministro no encontrado"));

        // Calcular las unidades totales asignadas
        int totalUnitsAssigned = dto.getAssignedBoxes() * productBoxSupply.getUnitsPerBox();

        // Validar que haya suficiente stock en el producto
        if (product.getStock() < totalUnitsAssigned) {
            throw new RuntimeException("El stock disponible (" + product.getStock() + ") es menor que las unidades requeridas (" + totalUnitsAssigned + ").");
        }

        // Validar que haya suficientes cajas en el suministro
        if (productBoxSupply.getBoxQuantity() < dto.getAssignedBoxes()) {
            throw new RuntimeException("El suministro disponible (" + productBoxSupply.getBoxQuantity() + " cajas) es menor que las cajas requeridas (" + dto.getAssignedBoxes() + ").");
        }

        // Actualizar el stock del producto
        product.setStock(product.getStock() - totalUnitsAssigned);
        productRepository.save(product);

        // Actualizar el suministro
        productBoxSupply.setBoxQuantity(productBoxSupply.getBoxQuantity() - dto.getAssignedBoxes());
        productBoxSupplyRepository.save(productBoxSupply);

        // Crear la asignación diaria
        DailyAssignment assignment = dailyAssignmentMapper.toEntity(dto, user, product, productBoxSupply);
        dailyAssignmentRepository.save(assignment);

        return dailyAssignmentMapper.toDTO(assignment);
    }

    @Override
    public DailyAssignmentDTO getDailyAssignmentById(Integer id) {
        DailyAssignment assignment = dailyAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
        return dailyAssignmentMapper.toDTO(assignment);
    }

    @Override
    public List<DailyAssignmentDTO> getAssignmentsByDate(LocalDate date) {
        return dailyAssignmentRepository.findByAssignmentDate(date)
                .stream()
                .map(dailyAssignmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DailyAssignmentDTO updateDailyAssignment(Integer id, DailyAssignmentDTO dto) {
        DailyAssignment assignment = dailyAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        // Obtener datos relacionados
        ProductBoxSupply productBoxSupply = assignment.getProductBoxSupply();
        Product product = productBoxSupply.getProduct();

        // Actualizar `product_box_supplies` si los valores no son 0
        if (dto.getReturnedBoxes() > 0) {
            int updatedBoxQuantity = productBoxSupply.getBoxQuantity() + dto.getReturnedBoxes();
            productBoxSupply.setBoxQuantity(updatedBoxQuantity);
        }

        if (dto.getReturnedUnits() > 0) {
            int updatedUnitsPerBox = productBoxSupply.getUnitsPerBox() + dto.getReturnedUnits();
            productBoxSupply.setUnitsPerBox(updatedUnitsPerBox);
        }

        // Actualizar el stock del producto si las unidades devueltas no son 0
        if (dto.getReturnedUnits() > 0) {
            int updatedStock = product.getStock() + dto.getReturnedUnits();
            product.setStock(updatedStock);
        }

        // Actualizar valores desde el DTO
        assignment.setReturnedUnits(dto.getReturnedUnits());
        assignment.setReturnedBoxes(dto.getReturnedBoxes());

        // Calcular total de unidades asignadas
        int totalAssignedUnits = assignment.getAssignedBoxes() * assignment.getProductBoxSupply().getUnitsPerBox();

        // Calcular unidades vendidas
        int totalSoldUnits = totalAssignedUnits - assignment.getReturnedUnits();
        assignment.setSoldUnits(totalSoldUnits);

        // Calcular cajas vendidas y actualizar soldBoxes
        int soldBoxes = totalSoldUnits / assignment.getProductBoxSupply().getUnitsPerBox();
        assignment.setSoldBoxes(soldBoxes);

        // Calcular ingreso total
        double boxPrice = assignment.getProductBoxSupply().getBoxPrice();
        assignment.setTotalRevenue(soldBoxes * boxPrice);

        // Guardar en base de datos
        dailyAssignmentRepository.save(assignment);
        return dailyAssignmentMapper.toDTO(assignment);
    }

    @Override
    public void deleteDailyAssignment(Integer id) {
        // Obtener la asignación diaria
        DailyAssignment assignment = dailyAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        // Obtener el producto
        Product product = assignment.getProduct();

        // Obtener el suministro del producto
        ProductBoxSupply productBoxSupply = assignment.getProductBoxSupply();

        // Calcular las unidades asignadas
        int totalUnitsAssigned = assignment.getAssignedBoxes() * productBoxSupply.getUnitsPerBox();

        // Restaurar el stock del producto
        product.setStock(product.getStock() + totalUnitsAssigned);

        // Restaurar las cajas en el suministro de producto
        productBoxSupply.setBoxQuantity(productBoxSupply.getBoxQuantity() + assignment.getAssignedBoxes());

        // Guardar las actualizaciones en la base de datos
        productRepository.save(product);
        productBoxSupplyRepository.save(productBoxSupply);

        // Eliminar la asignación diaria
        dailyAssignmentRepository.delete(assignment);
    }
}
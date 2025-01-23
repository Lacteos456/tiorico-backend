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
            throw new RuntimeException("La cantidad disponible (" + product.getStock() + ") es menor que las unidades requeridas (" + totalUnitsAssigned + ").");
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
        // Buscar la asignación diaria
        DailyAssignment assignment = dailyAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        // Obtener datos relacionados
        ProductBoxSupply productBoxSupply = assignment.getProductBoxSupply();
        Product product = productBoxSupply.getProduct();

        // Actualizar las cajas devueltas
        if (dto.getReturnedBoxes() > 0) {
            productBoxSupply.setBoxQuantity(productBoxSupply.getBoxQuantity() + dto.getReturnedBoxes());
        }

        // Actualizar el stock del producto
        if (dto.getReturnedUnits() > 0) {
            product.setStock(product.getStock() + dto.getReturnedUnits());
        }

        // Actualizar valores en la asignación
        assignment.setReturnedUnits(dto.getReturnedUnits());
        assignment.setReturnedBoxes(dto.getReturnedBoxes());

        // Calcular las unidades totales asignadas originalmente
        int totalAssignedUnits = assignment.getAssignedBoxes() * productBoxSupply.getUnitsPerBox();

        // Calcular las unidades vendidas
        int totalSoldUnits = totalAssignedUnits - dto.getReturnedUnits();
        assignment.setSoldUnits(totalSoldUnits);

        // Calcular las cajas vendidas
        int soldBoxes = totalSoldUnits / productBoxSupply.getUnitsPerBox();
        assignment.setSoldBoxes(soldBoxes);

        // Calcular ingreso total
        double boxPrice = productBoxSupply.getBoxPrice();
        assignment.setTotalRevenue(soldBoxes * boxPrice);

        // Guardar actualizaciones
        productRepository.save(product);
        productBoxSupplyRepository.save(productBoxSupply);
        dailyAssignmentRepository.save(assignment);

        return dailyAssignmentMapper.toDTO(assignment);
    }

    @Override
    public void deleteDailyAssignment(Integer id) {
        // Obtener la asignación diaria
        DailyAssignment assignment = dailyAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        // Obtener el producto relacionado
        Product product = assignment.getProduct();

        // Obtener el suministro del producto relacionado
        ProductBoxSupply productBoxSupply = assignment.getProductBoxSupply();

        // Calcular las unidades y cajas devueltas
        int returnedUnits = assignment.getReturnedUnits() != null ? assignment.getReturnedUnits() : 0;
        int returnedBoxes = assignment.getReturnedBoxes() != null ? assignment.getReturnedBoxes() : 0;

        // Calcular las unidades y cajas asignadas originalmente
        int assignedUnits = assignment.getAssignedBoxes() * productBoxSupply.getUnitsPerBox();
        int assignedBoxes = assignment.getAssignedBoxes();

        // Si todo lo retornado coincide con lo asignado, no hacer ajustes en el inventario
        if (returnedUnits == assignedUnits && returnedBoxes == assignedBoxes) {
            // Solo eliminar la asignación diaria
            dailyAssignmentRepository.delete(assignment);
            return;
        }

        // Calcular las unidades y cajas vendidas
        int soldUnits = assignment.getSoldUnits() != null ? assignment.getSoldUnits() : 0; // Usar 0 si `sold_units` es nulo
        int soldBoxes = assignment.getSoldBoxes() != null ? assignment.getSoldBoxes() : 0; // Usar 0 si `sold_boxes` es nulo

        // Si no hay soldUnits ni soldBoxes, usar assignedBoxes como respaldo
        if (soldUnits == 0 && soldBoxes == 0) {
            soldUnits = assignedUnits - returnedUnits;
            soldBoxes = assignedBoxes - returnedBoxes;
        }

        // Restaurar el inventario en ProductBoxSupply
        productBoxSupply.setBoxQuantity(productBoxSupply.getBoxQuantity() + soldBoxes);

        // Restaurar el stock del producto
        product.setStock(product.getStock() + soldUnits);

        // Guardar las actualizaciones en la base de datos
        productRepository.save(product);
        productBoxSupplyRepository.save(productBoxSupply);

        // Eliminar la asignación diaria
        dailyAssignmentRepository.delete(assignment);
    }
}
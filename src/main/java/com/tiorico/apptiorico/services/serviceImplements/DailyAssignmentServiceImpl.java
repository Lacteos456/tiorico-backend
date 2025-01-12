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

        // Actualizar valores desde el DTO
        assignment.setReturnedUnits(dto.getReturnedUnits());

        // Calcular total de unidades asignadas
        int totalAssignedUnits = assignment.getAssignedBoxes() * assignment.getProductBoxSupply().getUnitsPerBox();

        // Calcular unidades vendidas
        int totalSoldUnits = totalAssignedUnits - assignment.getReturnedUnits();
        if(totalSoldUnits == 0){
            totalSoldUnits = totalAssignedUnits;
            int returnedUnitsTotal = 0;
            assignment.setReturnedUnits(returnedUnitsTotal);
        }

        assignment.setSoldUnits(totalSoldUnits);

        // Calcular ingreso total
        int soldBoxes = totalSoldUnits / assignment.getProductBoxSupply().getUnitsPerBox();
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
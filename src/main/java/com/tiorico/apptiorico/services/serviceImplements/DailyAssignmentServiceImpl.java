package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.dtos.DailyAssignmentDTO;
import com.tiorico.apptiorico.mappers.DailyAssignmentMapper;
import com.tiorico.apptiorico.models.DailyAssignment;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.User;
import com.tiorico.apptiorico.repositories.DailyAssignmentRepository;
import com.tiorico.apptiorico.repositories.ProductBoxSupplyRepository;
import com.tiorico.apptiorico.repositories.ProductRepository;
import com.tiorico.apptiorico.repositories.UserRepository;
import com.tiorico.apptiorico.services.DailyAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DailyAssignmentServiceImpl implements DailyAssignmentService
{
    @Autowired
    private DailyAssignmentRepository dailyAssignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductBoxSupplyRepository productBoxSupplyRepository;

    @Autowired
    private DailyAssignmentMapper dailyAssignmentMapper;

    @Override
    public DailyAssignmentDTO createDailyAssignment(DailyAssignmentDTO dto) {
        DailyAssignment assignment = dailyAssignmentMapper.toEntity(dto);

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        assignment.setUser(user);

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        assignment.setProduct(product);

        // Inicializar campos relacionados con ventas y devoluciones a 0
        assignment.setTotalSoldUnits(0);
        assignment.setTotalRevenue(0.0);
        assignment.setReturnedBoxes(0);
        assignment.setReturnedUnits(0);

        dailyAssignmentRepository.save(assignment);
        return dailyAssignmentMapper.toDTO(assignment);
    }

    @Override
    public DailyAssignmentDTO getDailyAssignmentById(Integer id) {
        DailyAssignment assignment = dailyAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Daily Assignment not found"));
        return dailyAssignmentMapper.toDTO(assignment);
    }

    @Override
    public Map<String, Integer> getProductStockAndUnitsPerBox(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Recupera los valores como listas
        List<Integer> unitsPerBoxList = productBoxSupplyRepository.findUnitsPerBoxByProductId(productId);
        List<Integer> boxQuantityList = productBoxSupplyRepository.findUnitsBoxQuantityByProductId(productId);
        int countRegisters = productBoxSupplyRepository.findCountsByProductId(productId);

        // Procesa las listas (por ejemplo, toma el primer valor o calcula un total si hay más de uno)
        int unitsPerBox = unitsPerBoxList.isEmpty() ? 0 : unitsPerBoxList.get(0); // O suma si prefieres
        int boxQuantity = boxQuantityList.isEmpty() ? 0 : boxQuantityList.get(0); // O suma si prefieres

        Map<String, Integer> result = new HashMap<>();
        result.put("stock", product.getStock());
        result.put("unitsPerBox", unitsPerBox);
        result.put("boxQuantity", boxQuantity);
        result.put("counts", countRegisters);

        return result;
    }

    @Override
    public List<DailyAssignmentDTO> getAllDailyAssignments() {
        return dailyAssignmentRepository.findAll().stream()
                .map(dailyAssignmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DailyAssignmentDTO updateDailyAssignment(Integer id, DailyAssignmentDTO dto) {
        DailyAssignment assignment = dailyAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Daily Assignment not found"));

        // Actualiza los valores de devolución y calculados
        assignment.setReturnedBoxes(dto.getReturnedBoxes());
        assignment.setReturnedUnits(dto.getReturnedUnits());

        // Recupera los valores de unidades por caja como una lista
        List<Integer> unitsPerBoxList = productBoxSupplyRepository.findUnitsPerBoxByProductId(dto.getProductId());

        // Verifica que haya resultados y selecciona un valor adecuado
        int unitsPerBox = unitsPerBoxList.isEmpty() ? 0 : unitsPerBoxList.get(0); // Puedes cambiar la lógica si es necesario

        // Calcula las unidades vendidas y el total recaudado
        int totalAssignedUnits = dto.getAssignedQuantity() * unitsPerBox;
        int returnedUnits = (dto.getReturnedBoxes() * unitsPerBox) + dto.getReturnedUnits();
        assignment.setTotalSoldUnits(totalAssignedUnits - returnedUnits);
        assignment.setTotalRevenue(assignment.getTotalSoldUnits() * assignment.getProduct().getPrice());

        dailyAssignmentRepository.save(assignment);
        return dailyAssignmentMapper.toDTO(assignment);
    }

    @Override
    public void deleteDailyAssignment(Integer id) {
        DailyAssignment assignment = dailyAssignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Daily Assignment not found"));
        dailyAssignmentRepository.delete(assignment);
    }
}
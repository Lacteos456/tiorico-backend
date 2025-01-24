package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.dtos.SaleDTO;
import com.tiorico.apptiorico.mappers.SaleDetailsMapper;
import com.tiorico.apptiorico.mappers.SaleMapper;
import com.tiorico.apptiorico.models.*;
import com.tiorico.apptiorico.repositories.DailyAssignmentRepository;
import com.tiorico.apptiorico.repositories.ProductRepository;
import com.tiorico.apptiorico.repositories.SaleRepository;
import com.tiorico.apptiorico.repositories.UserRepository;
import com.tiorico.apptiorico.services.SaleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final UserRepository userRepository;
    private final DailyAssignmentRepository dailyAssignmentRepository;
    private final SaleDetailsMapper saleDetailsMapper;
    private final ProductRepository productRepository;

    public SaleServiceImpl(SaleRepository saleRepository, SaleMapper saleMapper, UserRepository userRepository, DailyAssignmentRepository dailyAssignmentRepository, SaleDetailsMapper saleDetailsMapper, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
        this.userRepository = userRepository;
        this.dailyAssignmentRepository = dailyAssignmentRepository;
        this.saleDetailsMapper = saleDetailsMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<SaleDTO> getSalesByUserId(Integer userId) {
        return saleRepository.findByUserId(userId).stream()
                .map(saleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SaleDTO createSale(SaleDTO saleDTO) {
        // Obtener el usuario desde el repositorio
        User user = userRepository.findById(saleDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener la asignación diaria desde el repositorio
        DailyAssignment dailyAssignment = dailyAssignmentRepository.findById(saleDTO.getDailyAssignmentId())
                .orElseThrow(() -> new RuntimeException("Asignación diaria no encontrada"));

        // Convertir el DTO de venta a entidad Sale
        Sale sale = saleMapper.toEntity(saleDTO, user, dailyAssignment);

        // Convertir y asignar los detalles de venta al objeto Sale
        List<SaleDetails> saleDetails = saleDTO.getSaleDetails().stream()
                .map(detailDTO -> {
                    // Obtener el producto correspondiente
                    Product product = productRepository.findById(detailDTO.getProductId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                    // Convertir el DTO del detalle de venta a la entidad SaleDetails
                    SaleDetails saleDetail = saleDetailsMapper.toEntity(detailDTO, sale, product);
                    saleDetail.setSale(sale); // Establecer la relación con la venta
                    return saleDetail;
                }).collect(Collectors.toList());

        // Asignar los detalles a la venta
        sale.setSaleDetails(saleDetails);

        // Calcular el total sin IVA y el total con IVA
        double totalPriceWithoutIva = saleDetails.stream()
                .mapToDouble(detail -> detail.getBoxQuantitySold() * detail.getUnitPrice())
                .sum();

        double totalPriceWithIva = totalPriceWithoutIva * 1.19; // Aplicar IVA del 19%

        // Asignar precios a la venta
        sale.setPrice(totalPriceWithoutIva); // Total sin IVA
        sale.setTotalPrice(totalPriceWithIva); // Total con IVA

        // Guardar la venta y devolver el DTO
        return saleMapper.toDTO(saleRepository.save(sale));
    }

    @Override
    public SaleDTO getSaleById(Integer id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        return saleMapper.toDTO(sale);
    }

    @Override
    public List<SaleDTO> getAllSales() {
        return saleRepository.findAll().stream()
                .map(saleMapper::toDTO)
                .collect(Collectors.toList());
    }
}
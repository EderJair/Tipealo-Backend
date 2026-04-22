package com.tipealo.api.service;

import com.tipealo.api.dto.venta.*;
import com.tipealo.api.entity.*;
import com.tipealo.api.entity.enums.TipoVenta;
import com.tipealo.api.repository.ClienteRepository;
import com.tipealo.api.repository.ProductoRepository;
import com.tipealo.api.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final UserService userService;

    public List<VentaResponse> getAll() {
        User user = userService.getCurrentUser();
        return ventaRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<VentaResponse> getHoy() {
        User user = userService.getCurrentUser();
        LocalDateTime inicio = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return ventaRepository.findByUserAndFechaBetween(user, inicio, fin).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public VentaResponse create(VentaRequest request) {
        User user = userService.getCurrentUser();

        Venta venta = new Venta();
        venta.setUser(user);
        venta.setTipo(request.getTipo());
        venta.setNotas(request.getNotas());

        if (request.getClienteId() != null) {
            Cliente cliente = clienteRepository.findByIdAndUser(request.getClienteId(), user)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            venta.setCliente(cliente);
        }

        List<VentaDetalle> detalles = request.getDetalles().stream().map(d -> {
            Producto producto = productoRepository.findByIdAndUser(d.getProductoId(), user)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + d.getProductoId()));

            if (producto.getStock() < d.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - d.getCantidad());
            productoRepository.save(producto);

            VentaDetalle detalle = new VentaDetalle();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(d.getPrecioUnitario());
            detalle.setSubtotal(d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())));
            return detalle;
        }).collect(Collectors.toList());

        BigDecimal total = detalles.stream()
                .map(VentaDetalle::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        venta.setTotal(total);
        venta.setDetalles(detalles);

        return toResponse(ventaRepository.save(venta));
    }

    public void delete(Long id) {
        User user = userService.getCurrentUser();
        Venta venta = ventaRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        ventaRepository.delete(venta);
    }

    private VentaResponse toResponse(Venta venta) {
        VentaResponse response = new VentaResponse();
        response.setId(venta.getId());
        response.setTotal(venta.getTotal());
        response.setTipo(venta.getTipo());
        response.setNotas(venta.getNotas());
        response.setFecha(venta.getFecha());

        if (venta.getCliente() != null) {
            response.setClienteId(venta.getCliente().getId());
            response.setClienteNombre(venta.getCliente().getNombre());
        }

        if (venta.getDetalles() != null) {
            response.setDetalles(venta.getDetalles().stream().map(d -> {
                VentaDetalleResponse dr = new VentaDetalleResponse();
                dr.setProductoId(d.getProducto().getId());
                dr.setProductoNombre(d.getProducto().getNombre());
                dr.setCantidad(d.getCantidad());
                dr.setPrecioUnitario(d.getPrecioUnitario());
                dr.setSubtotal(d.getSubtotal());
                return dr;
            }).collect(Collectors.toList()));
        }

        return response;
    }
}

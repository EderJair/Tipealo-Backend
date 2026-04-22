package com.tipealo.api.service;

import com.tipealo.api.dto.producto.ProductoRequest;
import com.tipealo.api.dto.producto.ProductoResponse;
import com.tipealo.api.entity.Producto;
import com.tipealo.api.entity.User;
import com.tipealo.api.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final UserService userService;

    public List<ProductoResponse> getAll() {
        User user = userService.getCurrentUser();
        return productoRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductoResponse getById(Long id) {
        User user = userService.getCurrentUser();
        Producto producto = productoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toResponse(producto);
    }

    public List<ProductoResponse> getStockBajo() {
        User user = userService.getCurrentUser();
        return productoRepository.findStockBajoByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductoResponse create(ProductoRequest request) {
        User user = userService.getCurrentUser();
        Producto producto = new Producto();
        mapToEntity(request, producto);
        producto.setUser(user);
        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse update(Long id, ProductoRequest request) {
        User user = userService.getCurrentUser();
        Producto producto = productoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        mapToEntity(request, producto);
        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse actualizarStock(Long id, Double cantidad) {
        User user = userService.getCurrentUser();
        Producto producto = productoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setStock(producto.getStock() + cantidad);
        return toResponse(productoRepository.save(producto));
    }

    public void delete(Long id) {
        User user = userService.getCurrentUser();
        Producto producto = productoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoRepository.delete(producto);
    }

    private void mapToEntity(ProductoRequest request, Producto producto) {
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock() != null ? request.getStock() : 0.0);
        producto.setStockMinimo(request.getStockMinimo() != null ? request.getStockMinimo() : 0.0);
        producto.setUnidad(request.getUnidad() != null ? request.getUnidad() : "unidad");
    }

    private ProductoResponse toResponse(Producto producto) {
        ProductoResponse response = new ProductoResponse();
        response.setId(producto.getId());
        response.setNombre(producto.getNombre());
        response.setDescripcion(producto.getDescripcion());
        response.setPrecio(producto.getPrecio());
        response.setStock(producto.getStock());
        response.setStockMinimo(producto.getStockMinimo());
        response.setUnidad(producto.getUnidad());
        response.setStockBajo(producto.getStock() <= producto.getStockMinimo());
        response.setCreatedAt(producto.getCreatedAt());
        return response;
    }
}

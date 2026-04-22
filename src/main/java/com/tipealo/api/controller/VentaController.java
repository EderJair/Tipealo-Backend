package com.tipealo.api.controller;

import com.tipealo.api.dto.venta.VentaRequest;
import com.tipealo.api.dto.venta.VentaResponse;
import com.tipealo.api.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaResponse>> getAll() {
        return ResponseEntity.ok(ventaService.getAll());
    }

    @GetMapping("/hoy")
    public ResponseEntity<List<VentaResponse>> getHoy() {
        return ResponseEntity.ok(ventaService.getHoy());
    }

    @PostMapping
    public ResponseEntity<VentaResponse> create(@RequestBody VentaRequest request) {
        return ResponseEntity.ok(ventaService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ventaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

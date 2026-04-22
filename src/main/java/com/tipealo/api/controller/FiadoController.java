package com.tipealo.api.controller;

import com.tipealo.api.dto.fiado.FiadoRequest;
import com.tipealo.api.dto.fiado.FiadoResponse;
import com.tipealo.api.dto.fiado.PagoRequest;
import com.tipealo.api.service.FiadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fiados")
@RequiredArgsConstructor
public class FiadoController {

    private final FiadoService fiadoService;

    @GetMapping
    public ResponseEntity<List<FiadoResponse>> getAll() {
        return ResponseEntity.ok(fiadoService.getAll());
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<FiadoResponse>> getPendientes() {
        return ResponseEntity.ok(fiadoService.getPendientes());
    }

    @GetMapping("/vencidos")
    public ResponseEntity<List<FiadoResponse>> getVencidos() {
        return ResponseEntity.ok(fiadoService.getVencidos());
    }

    @PostMapping
    public ResponseEntity<FiadoResponse> create(@RequestBody FiadoRequest request) {
        return ResponseEntity.ok(fiadoService.create(request));
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<FiadoResponse> registrarPago(@PathVariable Long id, @RequestBody PagoRequest request) {
        return ResponseEntity.ok(fiadoService.registrarPago(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fiadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

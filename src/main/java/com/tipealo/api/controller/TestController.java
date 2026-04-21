package com.tipealo.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public ResponseEntity<Map<String, String>> publicEndpoint() {
        return ResponseEntity.ok(Map.of("message", "API funcionando correctamente"));
    }

    @GetMapping("/private")
    public ResponseEntity<Map<String, String>> privateEndpoint() {
        return ResponseEntity.ok(Map.of("message", "Acceso autorizado con JWT"));
    }
}

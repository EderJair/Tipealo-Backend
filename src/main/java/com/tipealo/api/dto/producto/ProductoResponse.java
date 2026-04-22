package com.tipealo.api.dto.producto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Double stock;
    private Double stockMinimo;
    private String unidad;
    private boolean stockBajo;
    private LocalDateTime createdAt;
}

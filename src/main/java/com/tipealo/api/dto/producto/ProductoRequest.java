package com.tipealo.api.dto.producto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoRequest {
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Double stock;
    private Double stockMinimo;
    private String unidad;
}

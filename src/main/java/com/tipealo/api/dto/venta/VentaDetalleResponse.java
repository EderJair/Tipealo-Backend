package com.tipealo.api.dto.venta;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class VentaDetalleResponse {
    private Long productoId;
    private String productoNombre;
    private Double cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}

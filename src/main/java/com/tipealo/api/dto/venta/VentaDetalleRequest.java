package com.tipealo.api.dto.venta;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class VentaDetalleRequest {
    private Long productoId;
    private Double cantidad;
    private BigDecimal precioUnitario;
}

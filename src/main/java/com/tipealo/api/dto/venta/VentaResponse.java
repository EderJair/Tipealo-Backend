package com.tipealo.api.dto.venta;

import com.tipealo.api.entity.enums.TipoVenta;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaResponse {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private BigDecimal total;
    private TipoVenta tipo;
    private String notas;
    private LocalDateTime fecha;
    private List<VentaDetalleResponse> detalles;
}

package com.tipealo.api.dto.fiado;

import com.tipealo.api.entity.enums.EstadoFiado;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FiadoResponse {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private BigDecimal monto;
    private BigDecimal montoPagado;
    private BigDecimal montoRestante;
    private LocalDate fechaVencimiento;
    private EstadoFiado estado;
    private String notas;
    private LocalDateTime createdAt;
}

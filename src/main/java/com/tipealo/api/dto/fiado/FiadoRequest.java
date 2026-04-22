package com.tipealo.api.dto.fiado;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FiadoRequest {
    private Long clienteId;
    private BigDecimal monto;
    private LocalDate fechaVencimiento;
    private String notas;
}

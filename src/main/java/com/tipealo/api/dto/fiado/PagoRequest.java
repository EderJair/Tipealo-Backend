package com.tipealo.api.dto.fiado;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PagoRequest {
    private BigDecimal monto;
    private String notas;
}

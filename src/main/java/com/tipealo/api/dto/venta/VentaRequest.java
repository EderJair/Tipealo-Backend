package com.tipealo.api.dto.venta;

import com.tipealo.api.entity.enums.TipoVenta;
import lombok.Data;
import java.util.List;

@Data
public class VentaRequest {
    private Long clienteId;
    private TipoVenta tipo;
    private String notas;
    private List<VentaDetalleRequest> detalles;
}

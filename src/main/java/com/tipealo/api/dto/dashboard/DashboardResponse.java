package com.tipealo.api.dto.dashboard;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class DashboardResponse {
    private BigDecimal ventasHoy;
    private BigDecimal ventasMes;
    private BigDecimal totalFiadosPendientes;
    private Long clientesTotal;
    private Long productosStockBajo;
    private List<String> alertas;
}

package com.tipealo.api.service;

import com.tipealo.api.dto.dashboard.DashboardResponse;
import com.tipealo.api.entity.User;
import com.tipealo.api.entity.enums.EstadoFiado;
import com.tipealo.api.repository.ClienteRepository;
import com.tipealo.api.repository.FiadoRepository;
import com.tipealo.api.repository.ProductoRepository;
import com.tipealo.api.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VentaRepository ventaRepository;
    private final FiadoRepository fiadoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final UserService userService;

    public DashboardResponse getResumen() {
        User user = userService.getCurrentUser();

        LocalDateTime inicioHoy = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime finHoy = inicioHoy.plusDays(1);
        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();

        BigDecimal ventasHoy = ventaRepository.totalVentasByUserAndFecha(user, inicioHoy, finHoy);
        BigDecimal ventasMes = ventaRepository.totalVentasByUserAndFecha(user, inicioMes, finHoy);
        BigDecimal totalFiados = fiadoRepository.totalPendienteByUser(user);
        long totalClientes = clienteRepository.findByUser(user).size();
        long stockBajo = productoRepository.findStockBajoByUser(user).size();

        List<String> alertas = new ArrayList<>();

        if (stockBajo > 0) {
            alertas.add("Tienes " + stockBajo + " producto(s) con stock bajo");
        }

        long fiadosVencidos = fiadoRepository.findByUserAndEstado(user, EstadoFiado.VENCIDO).size();
        if (fiadosVencidos > 0) {
            alertas.add("Tienes " + fiadosVencidos + " fiado(s) vencido(s) sin cobrar");
        }

        DashboardResponse response = new DashboardResponse();
        response.setVentasHoy(ventasHoy != null ? ventasHoy : BigDecimal.ZERO);
        response.setVentasMes(ventasMes != null ? ventasMes : BigDecimal.ZERO);
        response.setTotalFiadosPendientes(totalFiados != null ? totalFiados : BigDecimal.ZERO);
        response.setClientesTotal(totalClientes);
        response.setProductosStockBajo(stockBajo);
        response.setAlertas(alertas);

        return response;
    }
}

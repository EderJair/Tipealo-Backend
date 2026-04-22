package com.tipealo.api.service;

import com.tipealo.api.dto.fiado.FiadoRequest;
import com.tipealo.api.dto.fiado.FiadoResponse;
import com.tipealo.api.dto.fiado.PagoRequest;
import com.tipealo.api.entity.Cliente;
import com.tipealo.api.entity.Fiado;
import com.tipealo.api.entity.PagoFiado;
import com.tipealo.api.entity.User;
import com.tipealo.api.entity.enums.EstadoFiado;
import com.tipealo.api.repository.ClienteRepository;
import com.tipealo.api.repository.FiadoRepository;
import com.tipealo.api.repository.PagoFiadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FiadoService {

    private final FiadoRepository fiadoRepository;
    private final ClienteRepository clienteRepository;
    private final PagoFiadoRepository pagoFiadoRepository;
    private final UserService userService;

    public List<FiadoResponse> getAll() {
        User user = userService.getCurrentUser();
        actualizarVencidos(user);
        return fiadoRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<FiadoResponse> getPendientes() {
        User user = userService.getCurrentUser();
        actualizarVencidos(user);
        return fiadoRepository.findByUserAndEstado(user, EstadoFiado.PENDIENTE).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<FiadoResponse> getVencidos() {
        User user = userService.getCurrentUser();
        actualizarVencidos(user);
        return fiadoRepository.findByUserAndEstado(user, EstadoFiado.VENCIDO).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public FiadoResponse create(FiadoRequest request) {
        User user = userService.getCurrentUser();
        Cliente cliente = clienteRepository.findByIdAndUser(request.getClienteId(), user)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Fiado fiado = new Fiado();
        fiado.setCliente(cliente);
        fiado.setUser(user);
        fiado.setMonto(request.getMonto());
        fiado.setMontoPagado(BigDecimal.ZERO);
        fiado.setFechaVencimiento(request.getFechaVencimiento());
        fiado.setNotas(request.getNotas());
        fiado.setEstado(EstadoFiado.PENDIENTE);

        return toResponse(fiadoRepository.save(fiado));
    }

    public FiadoResponse registrarPago(Long fiadoId, PagoRequest request) {
        User user = userService.getCurrentUser();
        Fiado fiado = fiadoRepository.findByIdAndUser(fiadoId, user)
                .orElseThrow(() -> new RuntimeException("Fiado no encontrado"));

        BigDecimal montoRestante = fiado.getMonto().subtract(fiado.getMontoPagado());
        if (request.getMonto().compareTo(montoRestante) > 0) {
            throw new RuntimeException("El pago excede la deuda restante de S/ " + montoRestante);
        }

        PagoFiado pago = new PagoFiado();
        pago.setFiado(fiado);
        pago.setMonto(request.getMonto());
        pago.setNotas(request.getNotas());
        pagoFiadoRepository.save(pago);

        fiado.setMontoPagado(fiado.getMontoPagado().add(request.getMonto()));
        if (fiado.getMontoPagado().compareTo(fiado.getMonto()) >= 0) {
            fiado.setEstado(EstadoFiado.PAGADO);
        }

        return toResponse(fiadoRepository.save(fiado));
    }

    public void delete(Long id) {
        User user = userService.getCurrentUser();
        Fiado fiado = fiadoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Fiado no encontrado"));
        fiadoRepository.delete(fiado);
    }

    private void actualizarVencidos(User user) {
        List<Fiado> vencidos = fiadoRepository.findVencidosByUser(user, LocalDate.now());
        vencidos.forEach(f -> f.setEstado(EstadoFiado.VENCIDO));
        fiadoRepository.saveAll(vencidos);
    }

    private FiadoResponse toResponse(Fiado fiado) {
        FiadoResponse response = new FiadoResponse();
        response.setId(fiado.getId());
        response.setClienteId(fiado.getCliente().getId());
        response.setClienteNombre(fiado.getCliente().getNombre());
        response.setMonto(fiado.getMonto());
        response.setMontoPagado(fiado.getMontoPagado());
        response.setMontoRestante(fiado.getMonto().subtract(fiado.getMontoPagado()));
        response.setFechaVencimiento(fiado.getFechaVencimiento());
        response.setEstado(fiado.getEstado());
        response.setNotas(fiado.getNotas());
        response.setCreatedAt(fiado.getCreatedAt());
        return response;
    }
}

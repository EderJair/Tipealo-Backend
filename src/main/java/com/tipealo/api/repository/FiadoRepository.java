package com.tipealo.api.repository;

import com.tipealo.api.entity.Cliente;
import com.tipealo.api.entity.Fiado;
import com.tipealo.api.entity.User;
import com.tipealo.api.entity.enums.EstadoFiado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FiadoRepository extends JpaRepository<Fiado, Long> {
    List<Fiado> findByUser(User user);
    List<Fiado> findByUserAndEstado(User user, EstadoFiado estado);
    List<Fiado> findByCliente(Cliente cliente);
    Optional<Fiado> findByIdAndUser(Long id, User user);

    @Query("SELECT SUM(f.monto - f.montoPagado) FROM Fiado f WHERE f.user = :user AND f.estado = 'PENDIENTE'")
    BigDecimal totalPendienteByUser(User user);

    @Query("SELECT f FROM Fiado f WHERE f.user = :user AND f.fechaVencimiento <= :fecha AND f.estado = 'PENDIENTE'")
    List<Fiado> findVencidosByUser(User user, LocalDate fecha);
}

package com.tipealo.api.repository;

import com.tipealo.api.entity.User;
import com.tipealo.api.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByUser(User user);
    Optional<Venta> findByIdAndUser(Long id, User user);

    @Query("SELECT v FROM Venta v WHERE v.user = :user AND v.fecha BETWEEN :inicio AND :fin")
    List<Venta> findByUserAndFechaBetween(User user, LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.user = :user AND v.fecha BETWEEN :inicio AND :fin")
    BigDecimal totalVentasByUserAndFecha(User user, LocalDateTime inicio, LocalDateTime fin);
}

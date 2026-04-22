package com.tipealo.api.repository;

import com.tipealo.api.entity.Producto;
import com.tipealo.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByUser(User user);
    Optional<Producto> findByIdAndUser(Long id, User user);

    @Query("SELECT p FROM Producto p WHERE p.user = :user AND p.stock <= p.stockMinimo")
    List<Producto> findStockBajoByUser(User user);
}

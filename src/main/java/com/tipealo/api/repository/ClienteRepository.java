package com.tipealo.api.repository;

import com.tipealo.api.entity.Cliente;
import com.tipealo.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByUser(User user);
    Optional<Cliente> findByIdAndUser(Long id, User user);
    boolean existsByNombreAndUser(String nombre, User user);
}

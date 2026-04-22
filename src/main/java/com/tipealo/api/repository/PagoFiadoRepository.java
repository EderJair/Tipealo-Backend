package com.tipealo.api.repository;

import com.tipealo.api.entity.Fiado;
import com.tipealo.api.entity.PagoFiado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagoFiadoRepository extends JpaRepository<PagoFiado, Long> {
    List<PagoFiado> findByFiado(Fiado fiado);
}

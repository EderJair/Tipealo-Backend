package com.tipealo.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pagos_fiado")
public class PagoFiado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fiado_id", nullable = false)
    private Fiado fiado;

    @Column(nullable = false)
    private BigDecimal monto;

    private String notas;

    @Column(name = "fecha")
    private LocalDateTime fecha = LocalDateTime.now();
}

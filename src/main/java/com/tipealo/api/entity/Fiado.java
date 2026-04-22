package com.tipealo.api.entity;

import com.tipealo.api.entity.enums.EstadoFiado;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "fiados")
public class Fiado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(name = "monto_pagado")
    private BigDecimal montoPagado = BigDecimal.ZERO;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Enumerated(EnumType.STRING)
    private EstadoFiado estado = EstadoFiado.PENDIENTE;

    private String notas;

    @OneToMany(mappedBy = "fiado", cascade = CascadeType.ALL)
    private List<PagoFiado> pagos;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}

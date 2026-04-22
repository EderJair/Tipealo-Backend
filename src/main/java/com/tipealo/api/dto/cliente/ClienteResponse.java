package com.tipealo.api.dto.cliente;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClienteResponse {
    private Long id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String notas;
    private LocalDateTime createdAt;
}

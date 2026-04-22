package com.tipealo.api.dto.cliente;

import lombok.Data;

@Data
public class ClienteRequest {
    private String nombre;
    private String telefono;
    private String direccion;
    private String notas;
}

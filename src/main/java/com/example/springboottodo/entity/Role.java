package com.example.springboottodo.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Roles disponíveis no sistema")
public enum Role {
    @Schema(description = "Usuário comum")
    USER,

    @Schema(description = "Administrador do sistema")
    ADMIN
}
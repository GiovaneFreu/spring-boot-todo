package com.example.springboottodo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Resposta de autenticação com token JWT")
public class AuthResponseDto {

    @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Tipo do token", example = "Bearer")
    private String type = "Bearer";

    @Schema(description = "Dados do usuário autenticado")
    private UserResponseDto user;

    public AuthResponseDto(String token, UserResponseDto user) {
        this.token = token;
        this.user = user;
    }
}
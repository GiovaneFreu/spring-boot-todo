package com.example.springboottodo.dto;

import com.example.springboottodo.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Dados de resposta do usuário")
public class UserResponseDto {

    @Schema(description = "ID único do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome de usuário", example = "joao123")
    private String username;

    @Schema(description = "Email do usuário", example = "joao@email.com")
    private String email;

    @Schema(description = "Role do usuário")
    private Role role;

    @Schema(description = "Data de criação")
    private LocalDateTime createdAt;

    @Schema(description = "Data de última atualização")
    private LocalDateTime updatedAt;
}
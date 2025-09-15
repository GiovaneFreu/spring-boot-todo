package com.example.springboottodo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Dados para realizar login")
public class LoginRequestDto {

    @Schema(description = "Nome de usuário", example = "admin")
    @NotBlank(message = "Username é obrigatório")
    private String username;

    @Schema(description = "Senha do usuário", example = "123456")
    @NotBlank(message = "Password é obrigatório")
    private String password;
}
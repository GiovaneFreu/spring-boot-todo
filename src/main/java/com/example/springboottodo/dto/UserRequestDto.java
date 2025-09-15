package com.example.springboottodo.dto;

import com.example.springboottodo.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Dados para criar ou atualizar usuário")
public class UserRequestDto {

    @Schema(description = "Nome de usuário único", example = "joao123")
    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
    private String username;

    @Schema(description = "Email do usuário", example = "joao@email.com")
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    private String email;

    @Schema(description = "Senha do usuário", example = "minhasenha123")
    @NotBlank(message = "Password é obrigatório")
    @Size(min = 6, message = "Password deve ter pelo menos 6 caracteres")
    private String password;

    @Schema(description = "Role do usuário", example = "USER")
    @NotNull(message = "Role é obrigatório")
    private Role role;
}
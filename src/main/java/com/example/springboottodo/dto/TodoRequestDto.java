package com.example.springboottodo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Dados para criar ou atualizar um TODO")
public class TodoRequestDto {

    @Schema(description = "Título da tarefa", example = "Estudar Spring Boot")
    @NotBlank(message = "Título é obrigatório")
    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
    private String title;

    @Schema(description = "Descrição detalhada da tarefa", example = "Revisar conceitos de Spring Security e JWT")
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String description;

    @Schema(description = "Status de conclusão da tarefa", example = "false")
    private Boolean completed = false;
}
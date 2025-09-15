package com.example.springboottodo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Dados de resposta de um TODO")
public class TodoResponseDto {

    @Schema(description = "ID único do TODO", example = "1")
    private Long id;

    @Schema(description = "Título da tarefa", example = "Estudar Spring Boot")
    private String title;

    @Schema(description = "Descrição detalhada da tarefa", example = "Revisar conceitos de Spring Security e JWT")
    private String description;

    @Schema(description = "Status de conclusão da tarefa", example = "false")
    private Boolean completed;

    @Schema(description = "Data de criação")
    private LocalDateTime createdAt;

    @Schema(description = "Data de última atualização")
    private LocalDateTime updatedAt;
}
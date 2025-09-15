package com.example.springboottodo.controller;

import com.example.springboottodo.dto.TodoRequestDto;
import com.example.springboottodo.dto.TodoResponseDto;
import com.example.springboottodo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@Tag(name = "TODOs", description = "Endpoints para gerenciamento de tarefas do usuário autenticado")
@SecurityRequirement(name = "Bearer Authentication")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    @Operation(summary = "Listar todos os TODOs", description = "Retorna todas as tarefas do usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Lista de TODOs retornada com sucesso")
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        List<TodoResponseDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar TODO por ID", description = "Retorna um TODO específico do usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TODO encontrado"),
            @ApiResponse(responseCode = "404", description = "TODO não encontrado ou não pertence ao usuário")
    })
    public ResponseEntity<TodoResponseDto> getTodoById(
            @Parameter(description = "ID do TODO") @PathVariable Long id) {
        TodoResponseDto todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    @Operation(summary = "Criar novo TODO", description = "Cria uma nova tarefa para o usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TODO criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<TodoResponseDto> createTodo(@Valid @RequestBody TodoRequestDto todoRequest) {
        TodoResponseDto todo = todoService.createTodo(todoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar TODO", description = "Atualiza uma tarefa existente do usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TODO atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "TODO não encontrado ou não pertence ao usuário"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<TodoResponseDto> updateTodo(
            @Parameter(description = "ID do TODO") @PathVariable Long id,
            @Valid @RequestBody TodoRequestDto todoRequest) {
        TodoResponseDto todo = todoService.updateTodo(id, todoRequest);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar TODO", description = "Remove uma tarefa do usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "TODO deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "TODO não encontrado ou não pertence ao usuário")
    })
    public ResponseEntity<Void> deleteTodo(
            @Parameter(description = "ID do TODO") @PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
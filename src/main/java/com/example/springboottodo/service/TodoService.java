package com.example.springboottodo.service;

import com.example.springboottodo.dto.TodoRequestDto;
import com.example.springboottodo.dto.TodoResponseDto;
import com.example.springboottodo.entity.Todo;
import com.example.springboottodo.entity.User;
import com.example.springboottodo.exception.ResourceNotFoundException;
import com.example.springboottodo.repository.TodoRepository;
import com.example.springboottodo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<TodoResponseDto> getAllTodos() {
        User currentUser = getCurrentUser();
        return todoRepository.findByUserIdOrderByCreatedAtDesc(currentUser.getId())
                .stream()
                .map(todo -> modelMapper.map(todo, TodoResponseDto.class))
                .collect(Collectors.toList());
    }

    public TodoResponseDto getTodoById(Long id) {
        User currentUser = getCurrentUser();
        Todo todo = todoRepository.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Todo não encontrado"));
        return modelMapper.map(todo, TodoResponseDto.class);
    }

    public TodoResponseDto createTodo(TodoRequestDto todoRequest) {
        User currentUser = getCurrentUser();

        Todo todo = modelMapper.map(todoRequest, Todo.class);
        todo.setUser(currentUser);

        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo, TodoResponseDto.class);
    }

    public TodoResponseDto updateTodo(Long id, TodoRequestDto todoRequest) {
        User currentUser = getCurrentUser();
        Todo todo = todoRepository.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Todo não encontrado"));

        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setCompleted(todoRequest.getCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoResponseDto.class);
    }

    public void deleteTodo(Long id) {
        User currentUser = getCurrentUser();
        if (!todoRepository.findByIdAndUserId(id, currentUser.getId()).isPresent()) {
            throw new ResourceNotFoundException("Todo não encontrado");
        }
        todoRepository.deleteByIdAndUserId(id, currentUser.getId());
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
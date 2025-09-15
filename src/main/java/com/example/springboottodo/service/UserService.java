package com.example.springboottodo.service;

import com.example.springboottodo.dto.UserRequestDto;
import com.example.springboottodo.dto.UserResponseDto;
import com.example.springboottodo.entity.User;
import com.example.springboottodo.exception.DuplicateResourceException;
import com.example.springboottodo.exception.ResourceNotFoundException;
import com.example.springboottodo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return modelMapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto createUser(UserRequestDto userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicateResourceException("Username já existe");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateResourceException("Email já existe");
        }

        User user = modelMapper.map(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!user.getUsername().equals(userRequest.getUsername()) &&
            userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicateResourceException("Username já existe");
        }

        if (!user.getEmail().equals(userRequest.getEmail()) &&
            userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateResourceException("Email já existe");
        }

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());

        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponseDto.class);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }
}
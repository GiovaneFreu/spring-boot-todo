package com.example.springboottodo.service;

import com.example.springboottodo.dto.AuthResponseDto;
import com.example.springboottodo.dto.LoginRequestDto;
import com.example.springboottodo.dto.UserRequestDto;
import com.example.springboottodo.dto.UserResponseDto;
import com.example.springboottodo.entity.User;
import com.example.springboottodo.exception.DuplicateResourceException;
import com.example.springboottodo.exception.ResourceNotFoundException;
import com.example.springboottodo.repository.UserRepository;
import com.example.springboottodo.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;

    public AuthResponseDto login(LoginRequestDto loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        UserResponseDto userResponse = modelMapper.map(user, UserResponseDto.class);

        return new AuthResponseDto(token, userResponse);
    }

    public AuthResponseDto register(UserRequestDto userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicateResourceException("Username já existe");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateResourceException("Email já existe");
        }

        User user = modelMapper.map(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User savedUser = userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        UserResponseDto userResponse = modelMapper.map(savedUser, UserResponseDto.class);

        return new AuthResponseDto(token, userResponse);
    }
}
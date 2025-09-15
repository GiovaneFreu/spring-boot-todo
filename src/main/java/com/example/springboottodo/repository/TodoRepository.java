package com.example.springboottodo.repository;

import com.example.springboottodo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Todo> findByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long id, Long userId);

    long countByUserIdAndCompleted(Long userId, Boolean completed);
}
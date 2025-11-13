package br.com.service.tasks.dtos;

import java.time.LocalDateTime;

public record CreateTaskDto (String title, String email, LocalDateTime dueDate) {}

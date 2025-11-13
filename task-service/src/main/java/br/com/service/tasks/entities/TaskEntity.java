package br.com.service.tasks.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "tasks")
@Builder
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String email;
    private LocalDateTime dueDate;
    private boolean isNotified;
}

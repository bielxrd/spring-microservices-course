package br.com.service.tasks.controllers;

import br.com.service.tasks.dtos.CreateTaskDto;
import br.com.service.tasks.dtos.TaskResponseDto;
import br.com.service.tasks.entities.TaskEntity;
import br.com.service.tasks.repositories.TaskRepository;
import br.com.service.tasks.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTask(@RequestBody CreateTaskDto createTaskDto) {
        TaskResponseDto taskResponseDto = taskService.saveTask(createTaskDto);
        return ResponseEntity.ok(taskResponseDto);
    }
}

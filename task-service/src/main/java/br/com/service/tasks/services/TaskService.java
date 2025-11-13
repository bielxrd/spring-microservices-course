package br.com.service.tasks.services;

import br.com.service.tasks.clients.NotificationClient;
import br.com.service.tasks.dtos.CreateTaskDto;
import br.com.service.tasks.dtos.NotificationRequestDTO;
import br.com.service.tasks.dtos.TaskResponseDto;
import br.com.service.tasks.entities.TaskEntity;
import br.com.service.tasks.repositories.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;

@Log4j2
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final NotificationClient notificationClient;
    private final TaskScheduler taskScheduler;

    public TaskService(TaskRepository taskRepository, NotificationClient notificationClient, TaskScheduler taskScheduler) {
        this.taskRepository = taskRepository;
        this.notificationClient = notificationClient;
        this.taskScheduler = taskScheduler;
    }

    public TaskResponseDto saveTask(CreateTaskDto taskDto) {
        TaskEntity taskEntity = TaskEntity.builder()
                .title(taskDto.title())
                .dueDate(taskDto.dueDate())
                .email(taskDto.email())
                .isNotified(false)
                .build();

        var taskResponse = taskRepository.save(taskEntity);

        taskScheduler.schedule(() -> {
            NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO("Sua tarefa " + taskDto.title() + " vence em 1 hora!", taskDto.email());
            notificationClient.sendNotification(notificationRequestDTO);
            taskEntity.setNotified(true);
            taskRepository.save(taskEntity);
        }, taskDto.dueDate().minusHours(1).atZone(java.time.ZoneId.systemDefault()).toInstant());

        return new TaskResponseDto(taskResponse.getId());
    }

    public void sendNotificationForDueTasks() {
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        List<TaskEntity> tasks = taskRepository.findTasksDueWithinDeadline(deadline);

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (TaskEntity task : tasks) {
                executor.submit(() -> {
                    NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO("Sua tarefa" + task.getTitle() + "está prestes a vencer!", task.getEmail());
                    notificationClient.sendNotification(notificationRequestDTO);
                    task.setNotified(true);
                    taskRepository.save(task);
                });
            }
        }
    }

    public void sendNotificationForDueTasksToday() {
        LocalDateTime startOfDay = LocalDateTime.now();
        LocalDateTime endOfDay = startOfDay.toLocalDate().atTime(23, 59, 59);

        List<TaskEntity> tasks = taskRepository.findTasksWithDueDateToday(startOfDay, endOfDay);

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (TaskEntity task : tasks) {
                executor.submit(() -> {
                    NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO("Sua tarefa " + task.getTitle() + " vence hoje!", task.getEmail());
                    notificationClient.sendNotification(notificationRequestDTO);
                    task.setNotified(true);
                    taskRepository.save(task);
                });
            }
        }
    }
}

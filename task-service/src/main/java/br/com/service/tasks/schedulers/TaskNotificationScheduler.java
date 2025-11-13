package br.com.service.tasks.schedulers;

import br.com.service.tasks.services.TaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskNotificationScheduler {

    private final TaskService taskService;

    public TaskNotificationScheduler(TaskService taskService) {
        this.taskService = taskService;
    }

//    @Scheduled(cron = "0 0 10 * * ?") // Every day at 10 AM
//    public void executeDailyTask() {
//        taskService.sendNotificationForDueTasksToday();
//    }

    @Scheduled(fixedRate = 360000 * 24) // Every 24 hours
    public void checkAndNotifyTasks() {
        this.taskService.sendNotificationForDueTasks();
    }

}

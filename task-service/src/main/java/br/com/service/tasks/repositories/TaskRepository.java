package br.com.service.tasks.repositories;

import br.com.service.tasks.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("SELECT t from tasks t WHERE t.dueDate <= :deadline AND t.isNotified = false")
    List<TaskEntity> findTasksDueWithinDeadline(LocalDateTime deadline);

    @Query("SELECT t from tasks t WHERE t.dueDate >= :startOfDay AND t.dueDate <= :endOfDay AND t.isNotified = false")
    List<TaskEntity> findTasksWithDueDateToday(LocalDateTime startOfDay, LocalDateTime endOfDay);
}

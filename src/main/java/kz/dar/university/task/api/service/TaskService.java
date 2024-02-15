package kz.dar.university.task.api.service;

import kz.dar.university.task.api.domain.task.TaskDetailed;
import kz.dar.university.task.api.domain.task.TaskRequest;
import kz.dar.university.task.api.domain.task.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    TaskResponse createTask(TaskRequest request);

    TaskResponse getTaskById(String taskId);

    Page<TaskResponse> getAllTasks(Pageable pageable);

    Page<TaskDetailed> getAllTasksWithDetails(Pageable pageable);

    Page<TaskResponse> getTasksByEmployeeId(
            String employeeId,
            Pageable pageable
    );

    TaskResponse updateTask(TaskRequest request);

    void deleteTaskById(String taskId);

    void deleteTasksByEmployeeId(String employeeId);

}

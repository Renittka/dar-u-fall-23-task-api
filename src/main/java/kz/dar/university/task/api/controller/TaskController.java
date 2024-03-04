package kz.dar.university.task.api.controller;

import jakarta.validation.Valid;
import kz.dar.university.task.api.domain.task.TaskDetailed;
import kz.dar.university.task.api.domain.task.TaskRequest;
import kz.dar.university.task.api.domain.task.TaskResponse;
import kz.dar.university.task.api.service.TaskService;
import kz.dar.university.task.api.util.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskDetailed createTask(@Valid @RequestBody TaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping("/all")
    public Page<TaskResponse> getAllTasks(
            Pageable pageable
    ) {
        return taskService.getAllTasks(pageable);
    }

    @GetMapping("/all/detailed")
    public Page<TaskDetailed> getAllTasksWithDetailedInfo(
            Pageable pageable
    ) {
        return taskService.getAllTasksWithDetails(pageable);
    }

    // task/abc
    // task/employee/abc
    @GetMapping("/{taskId}")
    public TaskResponse getTaskById(@PathVariable String taskId) {

        String doneStr = Status.DONE.toString(); // DONE
        doneStr = Status.DONE.getTitle(); // Done

        return taskService.getTaskById(taskId);
    }

    @GetMapping("/employee/{employeeId}")
    public Page<TaskResponse> getTasksByEmployeeId(
            @PathVariable String employeeId,
            Pageable pageable
    ) {
        return taskService.getTasksByEmployeeId(employeeId, pageable);
    }

    @PutMapping
    public TaskResponse updateTask(@RequestBody TaskRequest request) {
        return taskService.updateTask(request);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable String taskId) {
        taskService.deleteTaskById(taskId);
    }

    @DeleteMapping("/employee/{employeeId}")
    public void deleteTasksByEmployeeId(@PathVariable String employeeId) {
        taskService.deleteTasksByEmployeeId(employeeId);
    }

}

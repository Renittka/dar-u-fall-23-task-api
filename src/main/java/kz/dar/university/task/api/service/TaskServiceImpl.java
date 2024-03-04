package kz.dar.university.task.api.service;

import jakarta.ws.rs.NotFoundException;
import kz.dar.university.task.api.domain.employee.EmployeeDTO;
import kz.dar.university.task.api.domain.task.TaskDetailed;
import kz.dar.university.task.api.domain.task.TaskRequest;
import kz.dar.university.task.api.domain.task.TaskResponse;
import kz.dar.university.task.api.domain.model.Task;
import kz.dar.university.task.api.feign.EmployeeClient;
import kz.dar.university.task.api.repository.TaskRepository;
import kz.dar.university.task.api.util.Status;
import kz.dar.university.task.api.util.TaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeClient employeeClient;
    private final SendService sendService;
    private final TaskMapper mapper;

    @Override
    public TaskDetailed createTask(TaskRequest request) {
        log.info("Request: " + request);
        Task taskToSave = mapper.map(request);
        taskToSave.setStatus(
                request.getStatus() == null ? Status.TO_DO : request.getStatus()
        );
        taskRepository.save(taskToSave);
        log.info("Task: " + taskToSave);

        TaskDetailed taskDetailed = mapper.mapToDetailed(taskToSave);
        String employeeId = taskToSave.getEmployeeId();
        EmployeeDTO employee = employeeClient.getEmployeeById(employeeId);
        taskDetailed.setEmployee(employee);

        log.info("Task detailed: " + taskDetailed);
        sendService.sendMessage(taskDetailed);

        return taskDetailed;
    }

    @Override
    public TaskResponse getTaskById(String taskId) {
        return mapper.map(
                taskRepository.getTaskByTaskId(taskId)
        );
    }

    @Override
    public Page<TaskResponse> getAllTasks(Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 5);
        }
        log.info("Pageable: " + pageable);
        return taskRepository.getTasksBy(
                        pageable
                )
                .map(mapper::map);
    }

    @Override
    public Page<TaskDetailed> getAllTasksWithDetails(Pageable pageable) {
        Page<TaskResponse> allTasks = getAllTasks(pageable);

        /*
        Alternative Option
        return allTasks.map(
                task -> {
                    TaskDetailed taskDetailed = mapper.map(task);
                    String employeeId = task.getEmployeeId();
                    EmployeeDTO employee = employeeClient.getEmployeeById(employeeId);
                    taskDetailed.setEmployee(employee);

                    return taskDetailed;
                }
        );
         */

        // Task
        // allTasks -> distinct employeeIds -> request to employee-core-api
        List<String> uniqueEmployeeIds = allTasks.map(
                        TaskResponse::getEmployeeId
                )
                .stream()
                .distinct()
                .toList();

        Map<String, EmployeeDTO> employees = employeeClient
                .getEmployeesByList(uniqueEmployeeIds);

        return allTasks.map(
                task -> {
                    EmployeeDTO employee = employees.get(task.getEmployeeId());
                    TaskDetailed taskDetailed = mapper.map(task);
                    taskDetailed.setEmployee(employee);

                    return taskDetailed;
                }
        );
    }

    @Override
    public Page<TaskResponse> getTasksByEmployeeId(
            String employeeId,
            Pageable pageable
    ) {
        // Stream -> List -> Page - new PageImpl(list, pageable, list.size())

        if (pageable == null) {
            pageable = PageRequest.of(0, 5);
        }

        return taskRepository.getTasksByEmployeeId(
                        employeeId,
                        pageable
                )
                // .map(task -> mapper.map(task))
                .map(mapper::map);
    }

    @Override
    public TaskResponse updateTask(TaskRequest request) {
        if (request.getStatus() == null) {
            request.setStatus(Status.IN_PROGRESS);
        }

        String taskId = request.getTaskId();
        /*
        Task oldTask = taskRepository.getTaskByTaskId(taskId);

        if (oldTask != null) {
            request.setTaskId(taskId);
            Task taskToUpdate = mapper.map(request);
            return mapper.map(
                    taskRepository.save(taskToUpdate)
            );
        }
        else {
            throw new NotFoundException();
        }

         */

        if (taskRepository.existsTaskByTaskId(taskId)) {
            request.setTaskId(taskId);
            Task taskToUpdate = mapper.map(request);
            taskToUpdate.setStatus(
                    request.getStatus() == null ? Status.IN_PROGRESS : request.getStatus()
            );
            return mapper.map(
                    taskRepository.save(taskToUpdate)
            );
        } else {
            throw new NotFoundException();
        }

    }

    @Override
    public void deleteTaskById(String taskId) {
        taskRepository.deleteTaskByTaskId(taskId);
    }

    @Override
    public void deleteTasksByEmployeeId(String employeeId) {
        taskRepository.deleteTasksByEmployeeId(employeeId);
    }

}

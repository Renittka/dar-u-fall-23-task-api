package kz.dar.university.task.api.repository;

import kz.dar.university.task.api.domain.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends ElasticsearchRepository<Task, String> {

    Task getTaskByTaskId(String taskId);

    Boolean existsTaskByTaskId(String taskId);

    Page<Task> getTasksByEmployeeId(String employeeId, Pageable pageable);

    Page<Task> getTasksBy(Pageable pageable);

    void deleteTaskByTaskId(String taskId);

    void deleteTasksByEmployeeId(String employeeId);

}

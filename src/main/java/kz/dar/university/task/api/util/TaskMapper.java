package kz.dar.university.task.api.util;

import kz.dar.university.task.api.domain.task.TaskDetailed;
import kz.dar.university.task.api.domain.task.TaskRequest;
import kz.dar.university.task.api.domain.task.TaskResponse;
import kz.dar.university.task.api.domain.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TaskMapper {

    @Mapping(
            target = "taskId",
            expression = "java(request.getTaskId() == null?java.util.UUID.randomUUID().toString():request.getTaskId())"
    )
    Task map(TaskRequest request);

    TaskResponse map(Task task);

    TaskDetailed map(TaskResponse response);

}

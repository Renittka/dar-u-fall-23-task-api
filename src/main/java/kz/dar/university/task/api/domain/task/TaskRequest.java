package kz.dar.university.task.api.domain.task;

import jakarta.validation.constraints.NotNull;
import kz.dar.university.task.api.util.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskRequest {
    private String taskId;
    @NotNull
    private String employeeId;
    @NotNull
    private String title;
    private Status status;
}

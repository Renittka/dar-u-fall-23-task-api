package kz.dar.university.task.api.domain.task;

import kz.dar.university.task.api.domain.employee.EmployeeDTO;
import kz.dar.university.task.api.util.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskDetailed {

    private String taskId;
    private EmployeeDTO employee;
    private String title;
    private Status status;
    private Map<String, String> files;

}

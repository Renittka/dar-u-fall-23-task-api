package kz.dar.university.task.api.domain.model;

import kz.dar.university.task.api.util.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(indexName = "tasks")
public class Task {
    @Id
    private String taskId;
    @Field(type = FieldType.Keyword)
    private String employeeId;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Auto)
    private Status status;
}

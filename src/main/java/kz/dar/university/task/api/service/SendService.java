package kz.dar.university.task.api.service;

import kz.dar.university.task.api.domain.task.TaskDetailed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendService {

    private final KafkaTemplate<String, TaskDetailed> kafkaTemplate;

    @Value("${spring.kafka.task.topic}")
    private String topicName;

    public void sendMessage(TaskDetailed msg) {
        kafkaTemplate.send(topicName, msg);
    }

}
